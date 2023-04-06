package com.project.Ecommerce.payload;

import com.project.Ecommerce.util.ImageNameValid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends BaseEntityDto{

    private String userId;

    @Size(min = 3 , max = 10 , message = "invalid Name")
    private String name;

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$",message = "Invalid Email")
    @Email(message = "Email is required")
    private String email;

    @NotBlank(message = "password is required")
    private String password;


    @Size(min=4, max=6 ,message = "Invalid Gender")
    private String  gender;

    @ImageNameValid
    private String imageName;

    @NotBlank(message ="write something about yourself")
    private String about;

}
