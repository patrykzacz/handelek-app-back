package com.engthesis.demo.validator.availability.User;

import com.engthesis.demo.dao.entity.User;
import com.engthesis.demo.repository.UserRepository;
import org.springframework.stereotype.Component;
import static com.engthesis.demo.validator.ErrorMessages.NOT_AVAILABLE_EMAIL_MESSAGE;
import java.util.Optional;

@Component
public class UserEmailAvailability  {

    private final UserRepository userRepository;

    public UserEmailAvailability(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String validate(User user){
        Optional<User> email=userRepository.findByEmail(user.getEmail());
        return email.map(e-> NOT_AVAILABLE_EMAIL_MESSAGE).orElse(null);
    }

}
