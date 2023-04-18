package com.project.Ecommerce.impl;

import com.project.Ecommerce.entity.User;
import com.project.Ecommerce.payload.UserDto;
import com.project.Ecommerce.repository.UserRepository;
import com.project.Ecommerce.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserImplTest {

    @MockBean
    private UserRepository userRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    User user;

    @BeforeEach
    void init(){

         user = User.builder()
                .name("Ajinkya")
                .password("ajinkya21")
                .gender("male")
                .imageName("Ajinkya.png")
                .about("Software Engg")
                .build();

    }

    @Test
    void createUserTest() {

        Mockito.when(userRepo.save(Mockito.any())).thenReturn(user);

        UserDto user1 = userService.createUser(modelMapper.map(user, UserDto.class));

        Assertions.assertEquals("ajinkya21",user.getPassword());

    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void getAllUser() {
    }

    @Test
    void getSingleUser() {
    }

    @Test
    void getUserByEmail() {
    }

    @Test
    void getUserByEmailAndPassword() {
    }

    @Test
    void searchByUser() {
    }
}