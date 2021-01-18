package com.engthesis.demo.Controller;

import com.engthesis.demo.dao.*;
import com.engthesis.demo.dao.entity.Announcement;
import com.engthesis.demo.exception.EmailExistException;
import com.engthesis.demo.exception.InvalidInputException;
import com.engthesis.demo.exception.ObjectNotFoundException;
import com.engthesis.demo.manager.AnnoucementManager;
import com.engthesis.demo.manager.UserManager;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/anno")
public class AnnoucementController {

    private AnnoucementManager annoucementManager;
    private UserManager userManager;


    @Autowired
    public AnnoucementController(AnnoucementManager annoucementManager, UserManager userManager) {
        this.annoucementManager = annoucementManager;
        this.userManager = userManager;
    }

    @ApiOperation(value = "Add new annoucment", authorizations = {@Authorization(value = "authkey")})
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Annoucement added Succesfully"),
            @ApiResponse( code = 404, message = "Anno Does Not Exist"),
            @ApiResponse(code = 401, message = "Permission Denied")})
    @PostMapping(value = "/add")
    public ResponseTransfer add(
            @ApiParam(value = "Required annoucement content", required = true)
            @RequestBody AnnoData inputData){
            annoucementManager.addAnno(inputData);
        return new ResponseTransfer("Successfully created!");

    }

    @ApiOperation(value = "Get logged user Announcements", authorizations = {@Authorization(value = "authkey")})
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Data downloaded Succesfully"),
            @ApiResponse( code = 404, message = "Anno Does Not Exist"),
            @ApiResponse(code = 401, message = "Permission Denied")})
    @GetMapping(value ="/useranno")
    public List<AnnoData> getAnno() throws ObjectNotFoundException, PermissionDeniedDataAccessException {
            return annoucementManager.userAnno();

    }

    @ApiOperation(value = "Delete Anno Room", authorizations = {@Authorization(value = "authkey")})
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Room Removed Succesfully"),
            @ApiResponse( code = 404, message = "Anno Does Not Exist"),
            @ApiResponse(code = 401, message = "Permission Denied")})
    @DeleteMapping(value ="/deleteanno")
    public ResponseTransfer delete(
            @ApiParam(value = "Required announcementId", required = true)
            @RequestBody DeleteAnnoData inputData) throws ObjectNotFoundException, PermissionDeniedDataAccessException {
            annoucementManager.deletebyId(inputData);
        return new ResponseTransfer("Successfully deleted!");

    }

    @ApiOperation(value = "Get All announcements")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Object Not Found"),
            @ApiResponse(code = 500, message = "Server Error!")})
    @GetMapping(value ="/userannos")
    public List<AnnoData> getUserAnno(
            @ApiParam(value = "Required userId", required = true)
            @RequestParam Long id) throws ObjectNotFoundException {

            return annoucementManager.getUserAnno(id);

    }

    @ApiOperation(value = "Get All announcements")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Object Not Found"),
            @ApiResponse(code = 500, message = "Server Error!")})
    @GetMapping(value ="/allanno")
    public List<AnnoData> getAll() throws ObjectNotFoundException {
            return annoucementManager.getall();

    }

    @GetMapping(value = "/groupanno/{id}")
    public Set<AnnoData> grupAnno(@PathVariable Long id
    ) throws InvalidInputException, PermissionDeniedDataAccessException {

        return  annoucementManager.groupAnnoucments(id);
    }
}
