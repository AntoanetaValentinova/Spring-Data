package com.jsonprocessing.demo.service.impl;

import com.google.gson.Gson;
import com.jsonprocessing.demo.constants.GlobalConst;
import com.jsonprocessing.demo.model.dto.*;
import com.jsonprocessing.demo.model.entity.User;
import com.jsonprocessing.demo.repository.UserRepository;
import com.jsonprocessing.demo.service.UserService;
import com.jsonprocessing.demo.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.jsonprocessing.demo.constants.GlobalConst.RESOURCES_FILES_PATH;

@Service
public class UserServiceImpl implements UserService {
    public static final String USERS_PATH = "users.json";
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public void seedUsers() throws IOException {

        if (this.userRepository.count() > 0) {
            return;
        }

        UserSeedDataDto[] userSeedDataDtos =
                gson.fromJson(Files.readString(Path.of(RESOURCES_FILES_PATH + USERS_PATH)),
                        UserSeedDataDto[].class);

        Arrays.stream(userSeedDataDtos)
                .filter(validationUtil::isValid)
                .map(userSeedDataDto -> modelMapper.map(userSeedDataDto, User.class))
                .forEach(userRepository::save);
    }

    @Override
    public User selectRandomUser() {
        long randomId = ThreadLocalRandom.current().nextLong(1, this.userRepository.count() + 1);
        return this.userRepository.findById(randomId).orElse(null);
    }

    @Override
    public List<UserWithSoldProductsDto> findAllUsersWithAtLeastOneSoldProductOrderByLastNameAndFirstName() {
        return this.userRepository.findAllUsersWithMoreThanOneSoldProductOrderByLastNameFirstName()
                .stream()
                .map(user -> modelMapper.map(user, UserWithSoldProductsDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserProductsDto findAllUsersWithAtLeatOneSoldProductOrderByCountOfProductsAndLastName() {
        List<User> allUsers =
                this.userRepository.findAllUsersWithMoreThanOneProductSoldOrderByProductsSoldAndLastName();

        UserProductsDto userProductsDto = new UserProductsDto();

        userProductsDto.setUsersCount(allUsers.size());

        userProductsDto.setUsers(allUsers
                .stream()
                .map(user -> {
                    UserFirstLastAgeSoldProductsDto firstLastAgeSoldProductsDto =
                            modelMapper.map(user, UserFirstLastAgeSoldProductsDto.class);

                    ProductSoldDto productSoldDto=new ProductSoldDto();
                    productSoldDto.setCount(user.getProductsSold().size());
                    productSoldDto.setProducts(user
                            .getProductsSold()
                            .stream()
                            .map(product1 -> {
                                return modelMapper.map(product1, ProductNamePriceDto.class);
                            })
                            .collect(Collectors.toList()));
                    firstLastAgeSoldProductsDto.setSoldProducts(productSoldDto);
                    return firstLastAgeSoldProductsDto;
                })
                .collect(Collectors.toList())
        );
        return userProductsDto;
    }
}
