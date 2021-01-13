package com.engthesis.demo.manager;


import com.engthesis.demo.dao.*;
import com.engthesis.demo.dao.entity.Adress;
import com.engthesis.demo.dao.entity.User;
import com.engthesis.demo.exception.*;
import com.engthesis.demo.repository.AdressRepository;
import com.engthesis.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserManager {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtManager jwtManager;


    @Autowired
    public UserManager(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtManager jwtManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtManager = jwtManager;

    }

    public void validateLoginData(LoginData userInput) throws InvalidInputException {
        if (userInput.getEmail() == null || userInput.getPassword() == null ||
                userInput.getEmail().isEmpty() || userInput.getPassword().isEmpty())
            throw new InvalidInputException();
    }
    public User getUserByEmail(String email) throws EmailExistException {
        return userRepository.findByEmail(email).orElseThrow(EmailExistException::new);

    }

    public UserData getUser(String email) throws EmailExistException {
        return userRepository.findUserData(email).orElseThrow(EmailExistException::new);
    }

    public User getUserById(Long Id) throws UserNotFoundException {
        return userRepository.findById(Id).orElseThrow(UserNotFoundException::new);
    }

    public User getLoggedUser() throws EmailExistException {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return getUserByEmail(email);
    }

    public void addUser(RegisterData inputData) throws RuntimeException {
        userRepository.save(new User(inputData.getName(),
                inputData.getSurname(),
                hashPassword(inputData.getPassword()),
                inputData.getEmail(),
                inputData.getPhone()));

    }

    public Boolean verifyPassword(String password, String hash) throws InvalidPasswordException {
        if (!passwordEncoder.matches(password, hash)) throw new InvalidPasswordException();
        return true;
    }

    public void checkIfMailExist(String email) throws EmailExistException {
        if (userRepository.findByEmail(email).isPresent())
            throw new EmailExistException();
    }

    public LoginData getUserDataIfExist(String email) throws EmailExistException {
        return userRepository.findUserPassword(email).orElseThrow(EmailExistException::new);
    }
    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public void validateRegistrationData(RegisterData inputData) throws InvalidInputException {
        if (inputData.getEmail() == null || inputData.getEmail().length() < 5 || !inputData.getEmail().contains("@") ||
                inputData.getName() == null || inputData.getName().length() < 2 ||
                inputData.getName().length() > 40 || !inputData.getName().matches("[a-zA-Z]+") ||
                inputData.getSurname() == null || inputData.getSurname().length() < 2 ||
                inputData.getSurname().length() > 40 || !inputData.getSurname().matches("[a-zA-Z]+") ||
                inputData.getPassword() == null || inputData.getPassword().length() < 6)
            throw new InvalidInputException();
    }

    public void updateUserData(UserData userInput) throws UserNotFoundException{
    String email= SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    User user= userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        if( userInput.getName() == null || userInput.getName().equals("") )
            user.setName(user.getName());
        else if( userInput.getName().length() > 2 ||
                        userInput.getName().length() < 40
                        || userInput.getName().matches("[a-zA-Z]+"))
            user.setName(userInput.getName());
        if( userInput.getSurname() == null|| userInput.getSurname().equals(""))
            user.setSurname(user.getSurname());
        else if (userInput.getSurname().length() > 2 ||
                        userInput.getSurname().length() < 40 ||
                        userInput.getSurname().matches("[a-zA-Z]+"))
            user.setSurname(userInput.getSurname());
        if( userInput.getEmail() == null || userInput.getEmail().equals(""))
            user.setEmail(user.getEmail());
        else if( userInput.getEmail().length() > 5 ||
                        userInput.getEmail().contains("@"))
            user.setEmail(userInput.getEmail());
        if( userInput.getNumber() == null || userInput.getNumber().equals(""))
            user.setNumber(user.getNumber());
        else
            user.setNumber(userInput.getNumber());
        userRepository.save(user);
    }

    public void changePassword(UserPassword userInput) throws InvalidPasswordException, PasswordMatchedException {
        if (userInput.getOldPassword() == null || userInput.getNewPassword() == null
                || userInput.getNewPassword().length() < 6 || userInput.getOldPassword().length() < 6
                || userInput.getNewPassword().length() > 32 || userInput.getOldPassword().length() > 32)
            throw new InvalidInputException();
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        if (userInput.getNewPassword().equals(userInput.getOldPassword())) {
            throw new PasswordMatchedException();
        }
        verifyPassword(userInput.getOldPassword(), user.getPassword());
        user.setPassword(hashPassword(userInput.getNewPassword()));
        userRepository.save(user);
    }

    public TokenData login(LoginData userInput)  {
        this.validateLoginData(userInput);
        LoginData loginData;
        loginData = this.getUserDataIfExist(userInput.getEmail());
        this.verifyPassword(userInput.getPassword(), loginData.getPassword());
        String token = jwtManager.sign(loginData.getEmail(),loginData.getRole());
        UserData userData = userRepository.findTokenUserData(loginData.getEmail()).orElseThrow(ObjectNotFoundException::new);
        return new TokenData(token, userData);

    }

}
