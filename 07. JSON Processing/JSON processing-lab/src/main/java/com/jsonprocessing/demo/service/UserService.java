package com.jsonprocessing.demo.service;


import com.jsonprocessing.demo.model.dto.UserProductsDto;
import com.jsonprocessing.demo.model.dto.UserWithSoldProductsDto;
import com.jsonprocessing.demo.model.entity.User;

import java.io.IOException;
import java.util.List;

public interface UserService {
    void seedUsers() throws IOException;

    User selectRandomUser();

    List<UserWithSoldProductsDto> findAllUsersWithAtLeastOneSoldProductOrderByLastNameAndFirstName();

    UserProductsDto findAllUsersWithAtLeatOneSoldProductOrderByCountOfProductsAndLastName();
}
