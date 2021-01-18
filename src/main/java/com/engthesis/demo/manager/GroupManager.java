package com.engthesis.demo.manager;

import com.engthesis.demo.dao.GroupCreatorData;
import com.engthesis.demo.dao.GroupData;
import com.engthesis.demo.dao.UserData;
import com.engthesis.demo.dao.entity.Group;
import com.engthesis.demo.dao.entity.User;
import com.engthesis.demo.exception.EmailExistException;
import com.engthesis.demo.exception.ObjectNotFoundException;
import com.engthesis.demo.exception.PermissionDenied;
import com.engthesis.demo.exception.UserAlreadyBelongtoGroup;
import com.engthesis.demo.repository.GroupRepository;
import com.engthesis.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.PreRemove;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GroupManager {



    private final UserManager userManager;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public GroupManager(UserManager userManager, UserRepository userRepository, GroupRepository groupRepository) {
        this.userManager = userManager;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    public void createGroup(GroupCreatorData inputData){
        User user = userManager.getLoggedUser();
        Group group = new Group();
        group.setCreatorId(user.getId());
        group.setName(inputData.getName());
        group.setDescription(inputData.getDescription());
        Set<Group> groups=user.getUserGroups();
        groups.add(group);
        groupRepository.save(group);

    }

    public void joinToGroup(Long id, String email){
      //  User user = userManager.getLoggedUser();
        User user= userRepository.findByEmail(email).orElseThrow();
        String dupka = userManager.getLoggedUser().getEmail();
        Group group= groupRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
        Set<Group> groups=user.getUserGroups();
        if(groups.contains(group))
            throw new UserAlreadyBelongtoGroup();
        groups.add(group);
        userRepository.save(user);

    }

    public Set<UserData> getGroupUsers(Long id){
        Group group= groupRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
        Set<UserData> users= group.getMembers().stream().map(temp->{
           UserData usr= new UserData();
           usr.setName(temp.getName());
           usr.setEmail(temp.getEmail());
           usr.setSurname(temp.getSurname());
           usr.setNumber(temp.getNumber());
           return usr;
        }).collect(Collectors.toSet());
        return  users;
    }

    public Group groupData(Long id){
        return groupRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
    }

    public Set<Group> loggedUserGroups(){
        User user=userManager.getLoggedUser();
        return user.getUserGroups();
    }

    public List<Group> allGroups(){
        return groupRepository.findAll();
    }

    public void deleteGroup(Long id){
        User user = userManager.getLoggedUser();
        Group group = groupRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
        if(!group.getCreatorId().equals(user.getId()))
            throw new PermissionDenied();
        for (User x : group.getMembers()) {
            x.getUserGroups().remove(group);
        }
        groupRepository.delete(group);
    }

    public void leaveGroup(Long id){
        User user=userManager.getLoggedUser();
        Group group= groupRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
        Set<Group> groups=user.getUserGroups();
        if(!groups.contains(group))
            throw new ObjectNotFoundException();

        groups.remove(group);
        userRepository.save(user);
    }


}
