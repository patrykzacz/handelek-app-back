package com.engthesis.demo.Controller;

import com.engthesis.demo.dao.*;
import com.engthesis.demo.dao.entity.Announcement;
import com.engthesis.demo.exception.EmailExistException;
import com.engthesis.demo.exception.InvalidInputException;
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
    public ResponseTransfer register(
            @RequestBody AnnoData inputData) {
        try {
            annoucementManager.addAnno(inputData);
        } catch (InvalidInputException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input!", ex);
        }
        return new ResponseTransfer("Successfully created!");

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value ="/useranno")
    public List<AnnoData> getAnno() {
        try {
            return annoucementManager.userAnno();
        } catch (EmailExistException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping(value ="/deleteanno")
    public ResponseTransfer delete(@RequestBody DeleteAnnoData inputData) {
        try {
            annoucementManager.deletebyId(inputData);
        } catch (InvalidInputException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input!", ex);
        }
        return new ResponseTransfer("Successfully deleted!");

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value ="/userannos")
    public List<AnnoData> getUserAnno(@RequestParam Long id) {
        try {
            return annoucementManager.getUserAnno(id);
        } catch (EmailExistException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value ="/allanno")
    public List<AnnoData> getAll() {
        try {
            return annoucementManager.getall();
        } catch (EmailExistException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
    }
}
