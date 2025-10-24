package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    void addUser(UserDto userDto);

    void updateUser(UserDto userDto, Long id);

    void deleteUser(Long id);

    List<User> getAllUsers();

    UserDto getUserById(Long id);

    User getUserByUsername(String username);
}
