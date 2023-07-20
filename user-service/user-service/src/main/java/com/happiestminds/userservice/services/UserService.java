package com.happiestminds.userservice.services;

import com.happiestminds.userservice.entities.User;

import java.util.List;

public interface UserService {

    // create User
    User saveUser(User user);

    // Get all user
    List<User> getAllUser();

    // GetUserById
    User getUserById(String userId);

    // update user
    User updateUser(String userId, User user);

    // delete user
    void deleteUser(String userId);
}
