package com.example.shopweb.service;



import com.example.shopweb.dto.UserDto;
import com.example.shopweb.model.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();
}
