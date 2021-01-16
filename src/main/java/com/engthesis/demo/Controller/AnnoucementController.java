package com.engthesis.demo.Controller;

import com.engthesis.demo.dao.*;
import com.engthesis.demo.dao.entity.Announcement;
import com.engthesis.demo.exception.EmailExistException;
import com.engthesis.demo.exception.InvalidInputException;
import com.engthesis.demo.exception.ObjectNotFoundException;
import com.engthesis.demo.manager.AnnoucementManager;
import com.engthesis.demo.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/add")
    public ResponseTransfer add(
            @RequestBody AnnoData inputData){
            annoucementManager.addAnno(inputData);
        return new ResponseTransfer("Successfully created!");

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value ="/useranno")
    public List<AnnoData> getAnno() throws ObjectNotFoundException {
            return annoucementManager.userAnno();

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping(value ="/deleteanno")
    public ResponseTransfer delete(@RequestBody DeleteAnnoData inputData) throws ObjectNotFoundException {
            annoucementManager.deletebyId(inputData);
        return new ResponseTransfer("Successfully deleted!");

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value ="/userannos")
    public List<AnnoData> getUserAnno(@RequestParam Long id) throws ObjectNotFoundException {

            return annoucementManager.getUserAnno(id);

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value ="/allanno")
    public List<AnnoData> getAll() throws ObjectNotFoundException {
            return annoucementManager.getall();

    }
}
