package com.engthesis.demo.manager;


import com.engthesis.demo.dao.*;
import com.engthesis.demo.dao.entity.User;
import com.engthesis.demo.exception.*;
import com.engthesis.demo.repository.UserRepository;
import com.engthesis.demo.validator.availability.User.UserEmailAvailability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserManager {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtManager jwtManager;
    private final UserEmailAvailability userEmailAvailability;

    @Autowired
    public UserManager(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtManager jwtManager, UserEmailAvailability userEmailAvailability) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtManager = jwtManager;
        this.userEmailAvailability = userEmailAvailability;
    }


    public User getUserByEmail(String email) throws EmailExistException {
        return userRepository.findByEmail(email).orElseThrow(EmailExistException::new);

    }

    public UserData getUser(String email) throws EmailExistException {
        return userRepository.findUserData(email).orElseThrow(EmailExistException::new);
    }

    public User getLoggedUser() throws EmailExistException {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return getUserByEmail(email);
    }

    public void addUser(RegisterData inputData)  {
        User user= new User(inputData.getName(),
                inputData.getSurname(),
                inputData.getPassword(),
                inputData.getEmail(),
                inputData.getPhone());
        List<String> messages= validateRegisterData(user);
        if(!messages.isEmpty() ){
            throw new InvalidInputException(messages.toString());
        }
        user.setPassword(hashPassword(inputData.getPassword()));
        userRepository.save(user);
    }

    public Boolean verifyPassword(String password, String hash) throws InvalidPasswordException {
        if (!passwordEncoder.matches(password, hash)) throw new InvalidPasswordException("Old password doesn't match");
        return true;
    }

    public LoginData getUserDataIfExist(String email) {
        return userRepository.findUserPassword(email).orElseThrow(UserNotFoundException::new);
    }
    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public void updateUserData(UserData userInput){
    String email= SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    User user= userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        List<String> messages= validateData(userInput);
        if(!messages.isEmpty()){
            throw new InvalidInputException(messages.toString());
        }

        user.setName(userInput.getName());
        user.setSurname(userInput.getSurname());
        user.setEmail(userInput.getEmail());
        user.setNumber(userInput.getNumber());

        userRepository.save(user);
    }

    public void changePassword(UserPassword userInput){
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        if (userInput.getNewPassword().equals(userInput.getOldPassword())) {
            throw new PasswordMatchedException();
        }

        List<String> messages= validateData(userInput);
        if(!messages.isEmpty()){
            throw new InvalidInputException(messages.toString());
        }
        verifyPassword(userInput.getOldPassword(), user.getPassword());
        user.setPassword(hashPassword(userInput.getNewPassword()));
        userRepository.save(user);

    }

    public TokenData login(LoginData userInput) {
        List<String> messages=validateData(userInput);
        if(!messages.isEmpty())
            throw new InvalidInputException(messages.toString());
        LoginData loginData;
        loginData = this.getUserDataIfExist(userInput.getEmail());
        verifyPassword(userInput.getPassword(), loginData.getPassword());
        String token = jwtManager.sign(loginData.getEmail(),loginData.getRole());
        UserData userData = userRepository.findTokenUserData(loginData.getEmail()).orElseThrow(ObjectNotFoundException::new);
        return new TokenData(token, userData);
    }

    private List<String> validateRegisterData(User user){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        List<String> messages= new ArrayList<>();
        Set<ConstraintViolation<User>> errors = validator.validate(user);
        for (ConstraintViolation<User> violation : errors) {
            messages.add(violation.getMessage());
        }
        messages.add(userEmailAvailability.validate(user));
        return messages.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    private <T> List<String> validateData(T value){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        List<String> messages= new ArrayList<>();
        Set<ConstraintViolation<T>> errors = validator.validate(value);
        for (ConstraintViolation<T> violation : errors) {
            messages.add(violation.getMessage());
        }
        return messages.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

}
