package com.project.Ecommerce.payload;

import com.project.Ecommerce.util.ImageNameValid;
import lombok.*;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto extends BaseEntityDto {

    private Long categoryId;

    @Size(min=3 , max=20, message = "Invalid  message")
    private String title;

    @Size(min=5 ,max =200 ,message = "Invalid Description")
    private String description;

    @ImageNameValid
    private String  coverImage;
}
