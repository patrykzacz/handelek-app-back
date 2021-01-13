package com.engthesis.demo.Controller;

import com.engthesis.demo.dao.*;
import com.engthesis.demo.exception.EmailExistException;
import com.engthesis.demo.exception.InvalidInputException;
import com.engthesis.demo.exception.InvalidPasswordException;
import com.engthesis.demo.exception.PasswordMatchedException;
import com.engthesis.demo.manager.AdressManager;
import com.engthesis.demo.manager.JwtManager;
import com.engthesis.demo.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final JwtManager jwtManager;
    private final UserManager userManager;
    private final AdressManager adressManager;


    @Autowired
    public UserController(JwtManager jwtManager, UserManager userManager, AdressManager adressManager) {
        this.jwtManager = jwtManager;
        this.userManager = userManager;
        this.adressManager = adressManager;
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value ="/login")
    public TokenData login(
            @RequestBody LoginData userInput
            ) {
        try {
            return userManager.login(userInput);
        }catch (InvalidInputException ex ){
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid input")   ;
        }catch (InvalidPasswordException ex){
            throw new ResponseStatusException((HttpStatus.BAD_REQUEST),"Invalid Passowrd");
        }catch (EmailExistException ex) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email don't exist");
        }
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/register")
    public ResponseTransfer register(
            @RequestBody RegisterData inputData) {
        try {
            userManager.checkIfMailExist(inputData.getEmail());
            userManager.validateRegistrationData(inputData);
            userManager.addUser(inputData);
            adressManager.addCleanAdress(inputData.getEmail());
        } catch (EmailExistException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already taken!", ex);
        } catch (InvalidInputException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input!", ex);
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User cannot be registered!");
        }
        return new ResponseTransfer("Successfully created!");

    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value ="/user")
    public UserData getUser() {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            return userManager.getUser(email);
        } catch (EmailExistException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
    }
    @PatchMapping(value = "/password")
    public ResponseTransfer changePassowrd(
            @RequestBody UserPassword inputData) {
        try {
            userManager.changePassword(inputData);
        } catch (InvalidInputException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password too short or too long!", ex); }
        catch (InvalidPasswordException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password don't match!", ex);
        } catch (PasswordMatchedException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password Canno't be the same!", ex);
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Password Canno't be changed!", ex);
        }

        return new ResponseTransfer("Successfully changed!");
    }

    @PatchMapping(value = "/userPatch")
    public ResponseTransfer changeUserData(
            @RequestBody UserData userInput) {
        try {
            userManager.updateUserData(userInput);
        } catch (InvalidInputException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input!");
        }
        return new ResponseTransfer("Successfully updated!");
    }

    @GetMapping(value = "/test")
    public String test(){
        return "Test";
    }

}
