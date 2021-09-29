package com.bookstore.service;

import com.bookstore.entity.User;
import com.bookstore.entity.UserAuth;

import java.util.List;

public interface UserService {
    UserAuth checkAuth(String username, String userPassword);

    User getUser();

    User getUserById(Integer userId);

    boolean register(String username, String password,String name, String email);

    List<User> getAllUsers();

    void updateUserStatus(Integer userId,Boolean enabled);

    boolean registerCheck(String username);

}

