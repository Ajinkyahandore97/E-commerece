package com.project.Ecommerce.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto extends  BaseEntityDto{

    private Long productId;

    @Size(min = 3, max = 20, message = "Invalid Title")
    private String title;

    @Size(min = 4, max = 20, message = "Invalid Description")
    private String description;

    @Size(min = 4, max = 20, message = "Invalid Price")
    private Integer price;

    @Size(min = 4, max = 20, message = "Invalid Price")

    private Integer discountedPrice;

    @Size(min = 4, max = 20, message = "Invalid Price")
    private Integer quantity;

    private Date addDate;

    private Boolean live;

    private Boolean stock;
}
