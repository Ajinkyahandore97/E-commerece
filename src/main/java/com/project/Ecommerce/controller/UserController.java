package com.project.Ecommerce.controller;

import com.project.Ecommerce.config.AppConstant;
import com.project.Ecommerce.impl.FileImpl;
import com.project.Ecommerce.impl.UserImpl;
import com.project.Ecommerce.payload.ApiResponseMessage;
import com.project.Ecommerce.payload.ImageResponse;
import com.project.Ecommerce.payload.PageableResponse;
import com.project.Ecommerce.payload.UserDto;
import com.project.Ecommerce.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserImpl userImpl;


    @Autowired
    private FileImpl fileImpl;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){

        log.info("Initiating request for Create User");

        UserDto user =this.userImpl.createUser(userDto);

        log.info("Completed request for Create User");

        return  new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Long userId){

        log.info("Initiating request for update User");

        UserDto userDto1 = this.userImpl.updateUser(userDto, userId);

        log.info("Completed request for Update User");

        return ResponseEntity.ok(userDto1);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage>deleteUser(@PathVariable Long userId){

        log.info("Initiating request for Delete User");

        this.userImpl.deleteUser(userId);
        ApiResponseMessage message = ApiResponseMessage
                .builder()
                .message(AppConstant.USER_DELETE)
                .success(true)
                .status(HttpStatus.OK)
                .build();

        log.info("Completed request for Delete User");

        return new ResponseEntity<>( message ,HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<PageableResponse<UserDto>> getAllUser(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir
    ){

        log.info("Completed request for get All User");

        return ResponseEntity.ok(this.userImpl.getAllUser(pageNumber,pageSize,sortBy,sortDir));
    }


    @GetMapping("/{userId}")
    public ResponseEntity <UserDto> getSingleUser(@PathVariable Long userId){

        log.info("Completed request for get Single User");

        return ResponseEntity.ok(this.userImpl.getSingleUser(userId));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity <UserDto> getUserByEmail(@PathVariable String email){

        log.info("Completed request for get Single User");

        return new ResponseEntity<>(this.userImpl.getUserByEmail(email),HttpStatus.OK);

    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity <List<UserDto>> searchUserByKeyword(@PathVariable String keyword){

        log.info("Completed request for Search User");

        return  new ResponseEntity<>(this.userImpl.searchByUser(keyword),HttpStatus.OK);

    }

    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage") MultipartFile image,
                                                         @PathVariable Long userId) throws IOException {

        String imageName = fileImpl.uploadImage(image,imageUploadPath);

        UserDto singleUser = userImpl.getSingleUser(userId);

        singleUser.setImageName(imageName);

        UserDto userDto =userImpl.updateUser(singleUser, userId);

        ImageResponse imageResponse= ImageResponse.builder().imageName(imageName).message(AppConstant.IMAGE_UPLOAD).success(true).status(HttpStatus.CREATED).build();

        log.info("Completed request for Image create");

        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }


    //serve Image
    @GetMapping("/image/{userId}")
    public void serveImage(@PathVariable Long userId, HttpServletResponse response) throws IOException {

        UserDto singleUser = userImpl.getSingleUser(userId);

        log.info("User image Name :{} " , singleUser.getImageName());

        InputStream resource = fileService.getResource(imageUploadPath, singleUser.getImageName());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource,response.getOutputStream());
    }



}
