package com.project.Ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Ecommerce.entity.User;
import com.project.Ecommerce.impl.UserImpl;
import com.project.Ecommerce.payload.PageableResponse;
import com.project.Ecommerce.payload.UserDto;
import com.project.Ecommerce.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @MockBean
    private UserImpl userImp;

    @Autowired
    private  ModelMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    User user;

    UserDto user2;

    UserDto user3;

    UserDto user4;


    @BeforeEach
    public void  init()
    {
     user = User.builder()
            .name("ajinkya")
            .email("handoreajinkya21@gmail.com")
            .about("Software Dev")
            .password("ajinkya21")
            .imageName("ajinkya.png")
            .gender("male")
            .build();

     user2 = UserDto.builder()
                .name("Pallavi handore")
                .email("handorepallavi@gmail.com")
                .password("pallavi2")
                .imageName("pallavi.png")
                .gender("female")
                .about("I am Doctor")
                .build();

     user3 = UserDto.builder()
                .name("Rajesh")
                .email("Rajesh21@gmail.com")
                .password("rajesh12")
                .imageName("rajesh.png")
                .gender("male")
                .about("I am dancer")
                .build();

     user4 = UserDto.builder()
                .name("Anuradha")
                .email("handoreauradha21@gmail.com")
                .password("anuradha12")
                .imageName("anuradha.png")
                .gender("female")
                .about("I am lawyer")
                .build();

    }


    @Test
    void createUserTest() throws Exception {

        UserDto userDto = mapper.map(user, UserDto.class);

        Mockito.when(userImp.createUser(Mockito.any())).thenReturn(userDto);

        this.mockMvc.perform(
                         MockMvcRequestBuilders.post("/users/")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(convertObjectToJsonString(user))
                                 .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.password").exists())
                .andExpect(jsonPath("$.about").exists())
                .andExpect(jsonPath("$.imageName").exists())
                .andExpect(jsonPath("$.gender").exists());
    }


    @Test
    void updateUserTest() throws Exception {

        Long userId = 10L;

        UserDto userDto = mapper.map(user, UserDto.class);

        Mockito.when(userImp.updateUser(Mockito.any(), Mockito.anyLong())).thenReturn(userDto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/users/" + userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(user))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.password").exists())
                .andExpect(jsonPath("$.about").exists())
                .andExpect(jsonPath("$.imageName").exists())
                .andExpect(jsonPath("$.gender").exists());

    }

    @Test
    void deleteUserTest() throws Exception {

        Long userId=10L;

        UserDto userDto = this.mapper.map(user, UserDto.class);

        Mockito.doNothing().when(userImp).deleteUser(userId);

        this.mockMvc.perform(
                MockMvcRequestBuilders.delete("/users/"+ userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getAllUserTest() throws Exception {

        PageableResponse<UserDto> pageableResponse = new PageableResponse<>();

        pageableResponse.setContent(Arrays.asList(user2,user4,user3));

        pageableResponse.setLastPage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(100);
        pageableResponse.setTotalElements(1000);


        Mockito.when(this.userImp.getAllUser(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void getSingleUserTest() throws Exception {

        Long userId=10L;

        UserDto userDto = mapper.map(user, UserDto.class);

        Mockito.when(userImp.getSingleUser(Mockito.anyLong())).thenReturn(userDto);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/users/" , userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());


    }

    @Test
    void getUserByEmailTest() throws Exception {

        String email = "handoreajinkya@gmial.com";

        UserDto userDto = mapper.map(user, UserDto.class);

        Mockito.when(userImp.getUserByEmail(Mockito.anyString())).thenReturn(userDto);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/users/email/"+email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    void searchUserByKeyword() throws Exception {

        String keyword= "Pallavi";

        List<UserDto> userDtos = Arrays.asList(user2, user3, user4);

        Mockito.when(userImp.searchByUser(Mockito.anyString())).thenReturn((userDtos));

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/users/search/"+ keyword)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void uploadUserImage() {
    }

    @Test
    void serveImage() {
    }


    private String convertObjectToJsonString(Object user) {

        try {
            return new ObjectMapper().writeValueAsString(user);
        }catch (Exception e){

            e.printStackTrace();
            return  null;
        }
    }

}