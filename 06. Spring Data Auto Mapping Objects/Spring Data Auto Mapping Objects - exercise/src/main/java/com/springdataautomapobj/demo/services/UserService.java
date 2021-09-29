package com.springdataautomapobj.demo.services;

import com.springdataautomapobj.demo.models.dto.UserLoginDto;
import com.springdataautomapobj.demo.models.dto.UserRegisterDto;
import com.springdataautomapobj.demo.models.entities.Game;
import com.springdataautomapobj.demo.models.entities.User;

public interface UserService {
    void registerUser(UserRegisterDto userRegisterDto);

    void loginUser(UserLoginDto userLoginDto);

    void logoutUser();

    boolean isUserAdmin();

    User getCurrentUser();

    void purchase(String title);
}
