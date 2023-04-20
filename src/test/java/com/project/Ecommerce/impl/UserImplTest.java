package com.project.Ecommerce.impl;

import com.project.Ecommerce.entity.User;
import com.project.Ecommerce.exception.ResourceNotFoundException;
import com.project.Ecommerce.payload.PageableResponse;
import com.project.Ecommerce.payload.UserDto;
import com.project.Ecommerce.repository.UserRepository;
import com.project.Ecommerce.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.input.LineSeparatorDetector;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserImplTest {


    @MockBean
    private UserRepository userRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    User user1;

    User user2;

    User user3;

    User user4;

    UserDto userDto;

    List<User> ListOfusers;

    @BeforeEach
    public void inti()
    {

         user1 = User.builder()
                .name("Ajinkya")
                .email("handoreajinkya21@gmail.com")
                .password("ajinkya32")
                .imageName("ajinkya.png")
                .gender("male")
                .about("I am Software Dev")
                .build();

         userDto = UserDto.builder()
                .name("Ajinkya Handore")
                .gender("male")
                .imageName("ajinkya12.png")
                .email("handoreajinkya@gmail.com")
                .about("I am software Tester")
                .build();

        user2 = User.builder()
                .name("Pallavi handore")
                .email("handorepallavi@gmail.com")
                .password("pallavi2")
                .imageName("pallavi.png")
                .gender("female")
                .about("I am Doctor")
                .build();

        user3 = User.builder()
                .name("Rajesh")
                .email("Rajesh21@gmail.com")
                .password("rajesh12")
                .imageName("rajesh.png")
                .gender("male")
                .about("I am dancer")
                .build();

        user4 = User.builder()
                .name("Anuradha")
                .email("handoreauradha21@gmail.com")
                .password("anuradha12")
                .imageName("anuradha.png")
                .gender("female")
                .about("I am lawyer")
                .build();

        ListOfusers= new ArrayList<>();
        ListOfusers.add(user1);
        ListOfusers.add(user2);
        ListOfusers.add(user3);
        ListOfusers.add(user4);
    }


    @Test
    void createUserTest() {

        Mockito.when(userRepo.save(Mockito.any())).thenReturn(user1);

        UserDto createdUser = userService.createUser(mapper.map(user1, UserDto.class));

        Assertions.assertEquals(user1.getName(),createdUser.getName());


    }

    @Test
    void updateUserTest() {

        Long userId=10L;

        Mockito.when(userRepo.findById(userId)).thenReturn(Optional.of(user1));

        Mockito.when(userRepo.save(Mockito.any())).thenReturn(user1);

        UserDto updateUser = userService.updateUser(userDto, userId);

        Assertions.assertEquals(userDto.getName(),updateUser.getName());

    }

    @Test
    void deleteUserTest() {

        Long userId = 10L;

        Mockito.when(userRepo.findById(userId)).thenReturn(Optional.of(user1));

        userService.deleteUser(userId);

        Mockito.verify(userRepo,Mockito.times(1)).save(user1);

        Assertions.assertThrows(ResourceNotFoundException.class,()->userService.deleteUser(11L));
    }

    @Test
    void getAllUserTest() {

        PageImpl<User> page = new PageImpl<>(ListOfusers);

        Mockito.when(userRepo.findAll((Pageable) Mockito.any())).thenReturn(page);

        PageableResponse<UserDto> allUser = userService.getAllUser(1, 2, "name", "asc");

        Assertions.assertEquals(4,allUser.getContent().size());
    }

    @Test
    void getSingleUserTest() {

       Long userId=10L;

       Mockito.when(userRepo.findById(userId)).thenReturn(Optional.of(user1));

        UserDto singleUser = userService.getSingleUser(userId);

        Assertions.assertEquals("ajinkya32",singleUser.getPassword());


    }

    @Test
    void getUserByEmailTest() {

        String email="handoreajinkya@gmail.com";

        Mockito.when(userRepo.findByEmail(email)).thenReturn(Optional.of(user1));

        UserDto userByEmail = userService.getUserByEmail(email);

        Assertions.assertEquals(user1.getEmail(),userByEmail.getEmail());
    }

    @Test
    void searchByUserTest() {

        String keyword="Pallavi";

        Mockito.when(userRepo.findByNameContaining(keyword)).thenReturn(ListOfusers);

        List<UserDto> userDtos = userService.searchByUser(keyword);

        Assertions.assertEquals(4,userDtos.size());


    }
}