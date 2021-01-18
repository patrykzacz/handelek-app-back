package com.engthesis.demo.Controller;

import com.engthesis.demo.dao.*;
import com.engthesis.demo.exception.*;
import com.engthesis.demo.manager.AdressManager;

import com.engthesis.demo.manager.UserManager;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PermissionDeniedDataAccessException;
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

    @ApiOperation(value = "Login as user")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "{\n" +
            "    \"token\": \"string\"\n" +
            "}"),
            @ApiResponse(code = 400, message = "\"Invalid input!\" or \"Email don't exist!\" or \"Invalid password!\""),
            @ApiResponse(code = 500, message = "Server Error!")})
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value ="/login")
    public ResponseLoginTransfer  login(   @ApiParam(value = "Required email, password", required = true)
            @RequestBody LoginData userInput)throws InvalidInputException
            , UserNotFoundException ,ResponseStatusException {
            return new ResponseLoginTransfer("Successfully Logged",
                    userManager.login(userInput));
    }


    @ApiOperation(value = "Create new user")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully created!"),
            @ApiResponse(code = 400, message = "\"Invalid input!\" or \"Email already taken!\""),
            @ApiResponse(code = 500, message = "User cannot be registered!")})
    @PostMapping(value = "/register")
    public ResponseTransfer register(
            @ApiParam( value = "Required email, name, surname, password, phone number", required = true)
            @RequestBody RegisterData inputData) throws InvalidInputException {
            userManager.addUser(inputData);
            adressManager.addCleanAdress(inputData.getEmail());
        return new ResponseTransfer("Successfully created!");

    }
    @CrossOrigin(origins = "http://localhost:3000")
    @ApiOperation(value = "Get current user", authorizations = {@Authorization(value = "authkey")})
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Permission Denied"),
            @ApiResponse(code = 500, message = "Server Error!")})
    @GetMapping(value ="/user")
    public UserData getUser() throws EntityNotFoundException, PermissionDeniedDataAccessException {
            String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            return userManager.getUser(email);

    }

    @ApiOperation(value = "Change password", authorizations = {@Authorization(value = "authkey")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated!"),
            @ApiResponse(code = 400, message = "\"Wrong Password Regex\" or \"Password don't match!\" or \"Password Canno't be the same!\""),
            @ApiResponse(code = 401, message = "Permission Denied"),
            @ApiResponse(code = 500, message = "Password Cannot be changed")})
    @PutMapping(value = "/password")
    public ResponseTransfer changePassowrd(
            @ApiParam(value = "Required oldPassword, newPassword", required = true)
            @RequestBody UserPassword inputData) throws InvalidPasswordException, PasswordMatchedException, PermissionDeniedDataAccessException {
            userManager.changePassword(inputData);
        return new ResponseTransfer("Successfully changed!");
    }



    @PutMapping(value = "/userPatch")
    public ResponseTransfer changeUserData(
            @ApiParam(value = "Required email, name, surname, password, phone number", required = true)
            @RequestBody UserData userInput) throws InvalidInputException, EmailExistException {
            userManager.updateUserData(userInput);
        return new ResponseTransfer("Successfully updated!");
    }

    @GetMapping(value = "/test")
    public String test(){
        return "Test";
    }





}
