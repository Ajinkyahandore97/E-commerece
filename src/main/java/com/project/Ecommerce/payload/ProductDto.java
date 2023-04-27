package com.project.Ecommerce.payload;

import lombok.*;

import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto extends  BaseEntityDto{

    private Long productId;

    @Size(min = 3, max = 20, message = "Invalid Title")
    private String title;

    @Size(min = 4, max = 200, message = "Invalid Description")
    private String description;

    private Integer price;

    private Integer discountedPrice;

    private Integer quantity;

    private Date addDate;

    private Boolean live;

    private Boolean stock;

    private String productImage;
}
