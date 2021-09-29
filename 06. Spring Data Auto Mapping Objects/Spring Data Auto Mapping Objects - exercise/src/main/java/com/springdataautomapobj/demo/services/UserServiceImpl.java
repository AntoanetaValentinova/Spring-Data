package com.springdataautomapobj.demo.services;

import com.springdataautomapobj.demo.models.dto.UserLoginDto;
import com.springdataautomapobj.demo.models.dto.UserRegisterDto;
import com.springdataautomapobj.demo.models.entities.Game;
import com.springdataautomapobj.demo.models.entities.User;
import com.springdataautomapobj.demo.repositories.GameRepository;
import com.springdataautomapobj.demo.repositories.UserRepository;
import com.springdataautomapobj.demo.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final GameRepository gameRepository;
    private User loggedIn;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gameRepository = gameRepository;
    }


    @Override
    public void registerUser(UserRegisterDto userRegisterDto) {
        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {
            System.out.println("Passwords dont match!");
            return;
        }

        Set<ConstraintViolation<UserRegisterDto>> violations = validationUtil.violation(userRegisterDto);

        if (!violations.isEmpty()) {
            violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            return;
        }

        User user=modelMapper.map(userRegisterDto,User.class);
        if(userRepository.findAll().isEmpty()) {
            user.setAdmin(true);
        }
        userRepository.save(user);
        System.out.printf("%s was registered!%n",userRegisterDto.getFullName());
    }

    @Override
    public void loginUser(UserLoginDto userLoginDto) {
        Set<ConstraintViolation<UserLoginDto>> violations = validationUtil.violation(userLoginDto);

        if(!violations.isEmpty()) {
            violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            return;
        }

        User user = this.userRepository.findByEmailAndPassword(userLoginDto.getEmail(), userLoginDto.getPassword()).orElse(null);

        if (user==null) {
            System.out.println("Incorrect username / password!");
            return;
        }

        loggedIn=user;
        System.out.printf("Successfully logged in %s%n",user.getFullName());
    }

    @Override
    public void logoutUser() {
        if (loggedIn==null) {
            System.out.println("Cannot log out. No user was logged in.");
            return;
        }

        System.out.printf("User %s successfully logged out%n",loggedIn.getFullName());
        loggedIn=null;
    }

    @Override
    public boolean isUserAdmin() {
        if (loggedIn==null||loggedIn.isAdmin()==false) {
            return false;
        }
        return true;
    }

    @Override
    public User getCurrentUser() {
        return loggedIn;
    }


    @Override
    public void purchase(String title) {
        Game game=this.gameRepository.findByTitle(title).orElse(null);
        loggedIn.addGame(game);
        this.userRepository.save(loggedIn);
        System.out.println("You successfully purchase game!");
    }




}
