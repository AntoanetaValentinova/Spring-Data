package com.jsonprocessing.demo.model.dto;

import com.google.gson.annotations.Expose;
import com.jsonprocessing.demo.model.entity.User;

import java.util.List;

public class UserProductsDto {
    @Expose
    private Integer usersCount;
    @Expose
    List<UserFirstLastAgeSoldProductsDto> users;

    public UserProductsDto() {
    }

    public Integer getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(Integer usersCount) {
        this.usersCount = usersCount;
    }

    public List<UserFirstLastAgeSoldProductsDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserFirstLastAgeSoldProductsDto> users) {
        this.users = users;
    }
}
