package com.engthesis.demo.manager;

import com.engthesis.demo.dao.AnnoData;
import com.engthesis.demo.dao.DeleteAnnoData;
import com.engthesis.demo.dao.entity.Announcement;
import com.engthesis.demo.dao.entity.User;
import com.engthesis.demo.exception.ObjectNotFoundException;
import com.engthesis.demo.repository.AnnoucementRepository;
import com.engthesis.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AnnoucementManager {


    private final UserManager userManager;
    private final UserRepository userRepository;
    private final AnnoucementRepository annoucementRepository;

    @Autowired

    public AnnoucementManager(UserManager userManager, UserRepository userRepository, AnnoucementRepository annoucementRepository) {
        this.userManager = userManager;
        this.userRepository = userRepository;
        this.annoucementRepository = annoucementRepository;
    }

    public void addAnno(AnnoData inputData){
        User user= userManager.getLoggedUser();

        Announcement anno = new Announcement();
        anno.setUser(user);
        anno.setText(inputData.getText());
        Timestamp date = new Timestamp(new Date().getTime());
        anno.setDate(date);
        annoucementRepository.save(anno);

    }

    public List<AnnoData> userAnno(){
        User user= userManager.getLoggedUser();
        Long id=user.getId();
        return annoucementRepository.findUserAnno(id);
    }

    public void deletebyId(DeleteAnnoData inputData){
        Announcement anno= annoucementRepository.findById(inputData.getId()).orElseThrow(ObjectNotFoundException::new);
        annoucementRepository.delete(anno);
    }

    public List<AnnoData> getUserAnno(Long id){
        if(userRepository.findById(id).isEmpty())
            throw new ObjectNotFoundException();
        return annoucementRepository.findByUserId(id);
    }

    public List<AnnoData> getall(){
        return annoucementRepository.getAll();
    }
}
