package com.project.Ecommerce.service;

import com.project.Ecommerce.payload.PageableResponse;
import com.project.Ecommerce.payload.UserDto;

import java.util.List;

public interface UserService {

    // Create User
    UserDto createUser(UserDto userdto);

    // Update User

    UserDto updateUser(UserDto userDto, Long userId);

    // Delete User
    void deleteUser(Long userId);

    // Get All User
    PageableResponse<UserDto> getAllUser(int pageNumber , int pageSize, String sortBy, String sortDir);

    //Get Single User
    UserDto getSingleUser(Long userId);

    //Get Single User By Email
    UserDto getUserByEmail(String email);

    UserDto getUserByEmailAndPassword(String email,String password);

    //search User
    List<UserDto> searchByUser(String keyword);
}
