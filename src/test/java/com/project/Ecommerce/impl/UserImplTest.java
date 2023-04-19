package com.project.Ecommerce.impl;

import com.project.Ecommerce.entity.User;
import com.project.Ecommerce.exception.ResourceNotFoundException;
import com.project.Ecommerce.payload.PageableResponse;
import com.project.Ecommerce.payload.UserDto;
import com.project.Ecommerce.repository.UserRepository;
import com.project.Ecommerce.service.UserService;
import net.bytebuddy.TypeCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.util.*;
import java.util.function.Function;

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

    User user1;

    User user2;

    User user3;

    User user4;

    List<User> users;

    UserDto userDto;

    @BeforeEach
    void init(){

         user2 = User.builder()
                .name("Ajinkya")
                 .email("handoreajinkya21@gmail.com")
                .password("ajinkya21")
                .gender("male")
                .imageName("Ajinkya.png")
                .about("Software Engg")
                .build();



        userDto = UserDto.builder()
                .name("Rajesh")
                .gender("male")
                .email("rajesh21@gmail.com")
                .imageName("raj.png")
                .password("rajesh26")
                .build();

        user1 = User.builder()
                .name("Shumbham")
                .email("handoreshubham@gmail.com")
                .password("shubham123")
                .imageName("shubham.png")
                .gender("Male")
                .about("Lawyer")
                .build();


      users=new ArrayList<>();
      users.add(user);
      users.add(user1);


    }

    @Test
    void createUserTest() {

        Mockito.when(userRepo.save(Mockito.any())).thenReturn(user);

        UserDto user1 = userService.createUser(modelMapper.map(user, UserDto.class));

        Assertions.assertEquals("ajinkya21",user1.getPassword());

    }

    @Test
    void updateUserTest() {

        Long userid= 10L;

        Mockito.when(userRepo.findById(userid)).thenReturn(Optional.of(user));
        Mockito.when(userRepo.save(Mockito.any())).thenReturn(user);

        UserDto updateUser = userService.updateUser(userDto, userid);


        Assertions.assertNotNull(updateUser);
        Assertions.assertEquals(userDto.getName(),updateUser.getName());
    }

    @Test
    void deleteUser() {

        Long userid=10L;

        Mockito.when(userRepo.findById(userid)).thenReturn(Optional.of(user));

        userService.deleteUser(userid);

        Mockito.verify(userRepo,Mockito.times(1)).save(user);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(111L));

    }

    @Test
    void getAllUser() {

        Page<User> page = new PageImpl<>(users);

        Mockito.when(userRepo.findAll((Pageable) Mockito.any())).thenReturn(page);

        PageableResponse<UserDto> allUser = userService.getAllUser(1,2,"name","asc");

       Assertions.assertEquals(2,allUser.getContent().size());

    }

    @Test
    void getSingleUser() {

        Long userid=10L;

        Mockito.when(userRepo.findById(userid)).thenReturn(Optional.of(user));

        UserDto singleUser = userService.getSingleUser(userid);

        Assertions.assertEquals(user.getName(),singleUser.getName());

    }

    @Test
    void getUserByEmail() {

        String email ="handoreajnkya21@gmail.com";

        Mockito.when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));

        UserDto userByEmail = userService.getUserByEmail(email);

        Assertions.assertEquals(user.getEmail(),userByEmail.getEmail());

    }

    @Test
    void getUserByEmailAndPassword() {





    }

    @Test
    void searchByUser() {

        user3 = User.builder()
                .name("Rushikesh Handore")
                .email("rushikesh21@gmail.com")
                .password("rushikesh5")
                .gender("male")
                .imageName("rushi.png")
                .about("Bussinessman")
                .build();

        user4 = User.builder()
                .name("Pallavi Handore")
                .email("pallavihandore@gmail.com")
                .password("pallavi1@gmail.com")
                .gender("female")
                .imageName("pallavi.png")
                .about("Dancer")
                .build();

        String keywords="Pallavi";

        Mockito.when(userRepo.findByNameContaining(Mockito.anyString())).thenReturn(Arrays.asList(user3,user4));

        List<UserDto> userDtos = userService.searchByUser(keywords);


        assertEquals(2,userDtos.size());

    }
}