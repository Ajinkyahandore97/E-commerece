package com.project.Ecommerce.impl;

import com.project.Ecommerce.config.AppConstant;
import com.project.Ecommerce.entity.User;
import com.project.Ecommerce.exception.ResourceNotFoundException;
import com.project.Ecommerce.helper.Helper;
import com.project.Ecommerce.payload.PageableResponse;
import com.project.Ecommerce.payload.UserDto;
import com.project.Ecommerce.repository.UserRepository;
import com.project.Ecommerce.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public UserDto createUser(UserDto userdto) {

        log.info("Initiating DAO Call for Create User");

        User user = this.modelMapper.map(userdto, User.class);

        user.setIsActive(AppConstant.YES);

        User createdUser = this.userRepo.save(user);

        log.info("Completing DAO Call for Create User");

        return this.modelMapper.map(createdUser,UserDto.class);

    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {

        log.info("Initiating DAO Call for Update User");

        User user = this.userRepo.findById(userId).orElseThrow(() -> new RuntimeException(AppConstant.USER_NOT_FOUND));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setGender(userDto.getGender());
        user.setAbout(userDto.getAbout());
        user.setImageName(userDto.getImageName());

        User savedUser = this.userRepo.save(user);

        log.info("Completing DAO Call For Update User");

        return this.modelMapper.map(savedUser,UserDto.class);
    }

    @Override
    public void deleteUser(Long userId) {

        log.info("Initiating DAO Call for Delete User");

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.USER_NOT_FOUND));


        // soft delete User

        user.setIsActive(AppConstant.NO);

        userRepo.save(user);

        log.info("Completing DAO Call for Delete User");

        // Hard delete
        // this.userRepo.delete(user);


    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {

        log.info("Initiating DAO Call for get All User");

        Sort sort =(sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<User> page =this.userRepo.findAll(pageable);

        log.info("Completing DAO Call for get All User");

        PageableResponse<UserDto> pageableResponse = Helper.getPageableResponse(page, UserDto.class);

        return pageableResponse;
    }

    @Override
    public UserDto getSingleUser(Long userId) {

        log.info(" Initiating DAO Call for get Single User");

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.USER_NOT_FOUND));

        log.info("Completing DAO Call for get Single User");

        return this.modelMapper.map(user,UserDto.class);

    }

    @Override
    public UserDto getUserByEmail(String email) {

        log.info(" Initiating DAO Call for get User By Email");

        User user = this.userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(AppConstant.EMAIL_NOT_FOUND));

        log.info(" Completing DAO Call for get User By Email");

        return  this.modelMapper.map(user,UserDto.class);
    }

    @Override
    public UserDto getUserByEmailAndPassword(String email, String password) {
        return  null;
    }

    @Override
    public List<UserDto> searchByUser(String keyword)
    {
        log.info(" Initiating DAO Call for Search User");

        List<User> users = this.userRepo.findByNameContaining(keyword);

        List<UserDto> collect = users.stream().map(user -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());

        log.info(" Completing DAO Call for Search User");

        return collect;
    }
}
