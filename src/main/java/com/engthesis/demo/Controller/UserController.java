package com.engthesis.demo.Controller;

import com.engthesis.demo.dao.*;
import com.engthesis.demo.exception.*;
import com.engthesis.demo.manager.AdressManager;

import com.engthesis.demo.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/user")
public class UserController {


    private final UserManager userManager;
    private final AdressManager adressManager;


    @Autowired
    public UserController(UserManager userManager, AdressManager adressManager) {

        this.userManager = userManager;
        this.adressManager = adressManager;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value ="/login")
    public ResponseLoginTransfer  login(
            @RequestBody LoginData userInput)throws InvalidInputException
            , UserNotFoundException ,ResponseStatusException {
            return new ResponseLoginTransfer("Successfully Logged",
                    userManager.login(userInput));
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/register")
    public ResponseTransfer register(
            @RequestBody RegisterData inputData) throws InvalidInputException {
            userManager.addUser(inputData);
            adressManager.addCleanAdress(inputData.getEmail());
        return new ResponseTransfer("Successfully created!");

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value ="/user")
    public UserData getUser() throws EntityNotFoundException {
            String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            return userManager.getUser(email);

    }
    @PutMapping(value = "/password")
    public ResponseTransfer changePassowrd(
            @RequestBody UserPassword inputData) throws InvalidPasswordException, PasswordMatchedException {
            userManager.changePassword(inputData);
        return new ResponseTransfer("Successfully changed!");
    }

    @PutMapping(value = "/userPatch")
    public ResponseTransfer changeUserData(
            @RequestBody UserData userInput) throws InvalidInputException, EmailExistException {
            userManager.updateUserData(userInput);
        return new ResponseTransfer("Successfully updated!");
    }

    @GetMapping(value = "/test")
    public String test(){
        return "Test";
    }





}
