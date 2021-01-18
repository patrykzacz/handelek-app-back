package com.engthesis.demo.Controller;

import com.engthesis.demo.dao.GroupCreatorData;
import com.engthesis.demo.dao.ResponseTransfer;
import com.engthesis.demo.dao.UserData;
import com.engthesis.demo.dao.UserEmail;
import com.engthesis.demo.dao.entity.Group;
import com.engthesis.demo.dao.entity.User;
import com.engthesis.demo.exception.EmailExistException;
import com.engthesis.demo.exception.InvalidInputException;
import com.engthesis.demo.manager.GroupManager;
import com.engthesis.demo.manager.UserManager;
import com.engthesis.demo.repository.UserRepository;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/group")
public class GroupController {


    private final UserManager userManager;
    private final GroupManager groupManager;
    private final UserRepository userRepository;

    @PostMapping(value = "/newgroup")
    public ResponseTransfer register(
            @ApiParam( value = "Required Group name, description", required = true)
            @RequestBody GroupCreatorData inputData) throws InvalidInputException, PermissionDeniedDataAccessException {
        groupManager.createGroup(inputData);
        return new ResponseTransfer("Successfully created!");

    }

    @PostMapping(value = "/joining/{id}")
    public ResponseTransfer join(@PathVariable Long id,@RequestBody UserEmail input
            ) throws InvalidInputException, PermissionDeniedDataAccessException {
        UserEmail test= input;
        groupManager.joinToGroup(id, input.getEmail());
        return new ResponseTransfer("Successfully joined!");

    }

    @GetMapping(value = "/members/{id}")
    public Set<UserData> members(@PathVariable Long id
    ) throws InvalidInputException, PermissionDeniedDataAccessException {
        return  groupManager.getGroupUsers(id);

    }


    @GetMapping(value = "/group/{id}")
    public Group groupdata(@PathVariable Long id
    ) throws InvalidInputException, PermissionDeniedDataAccessException {
        return  groupManager.groupData(id);
    }

    @ApiOperation(value = "Change user data", authorizations = {@Authorization(value = "authkey")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated!"),
            @ApiResponse(code = 401, message = "Unauthoraized"),
            @ApiResponse(code = 500, message = "Server Error!")})
    @GetMapping(value = "/usergroups")
    public Set<Group> userGroups(
    ) throws InvalidInputException, PermissionDeniedDataAccessException {
        return  groupManager.loggedUserGroups();
    }

    @GetMapping(value = "/allGroups")
    public List<Group> allGroups(
    ) throws InvalidInputException, PermissionDeniedDataAccessException {
        return  groupManager.allGroups();
    }

    @DeleteMapping(value = "/deleteGroup/{id}")
    public ResponseTransfer deleteGroup(@PathVariable Long id
    ) throws InvalidInputException, PermissionDeniedDataAccessException {
            groupManager.deleteGroup(id);
            return new ResponseTransfer("Successfully deleted");
    }

    @DeleteMapping(value = "/leaveGroup/{id}")
    public ResponseTransfer leaveGroup(@PathVariable Long id
    ) throws InvalidInputException, PermissionDeniedDataAccessException {
        groupManager.leaveGroup(id);
        return new ResponseTransfer("Successfully leaved");
    }

}
