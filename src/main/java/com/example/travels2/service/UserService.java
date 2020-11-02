package com.example.travels2.service;

import com.example.travels2.entity.User;

public interface UserService {

    void register(User user);

    User login(User user);
}
