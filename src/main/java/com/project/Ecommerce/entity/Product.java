package com.project.Ecommerce.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntityClass{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "Tittle")
    private String title;

    @Column(name = "Description")
    private String description;

    @Column(name = "Price")
    private Integer price;

    @Column(name = "DiscountedPrice")
    private Integer discountedPrice;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "Date")
    private Date addDate;

    @Column(name = "Live")
    private Boolean live;

    @Column(name = "Stock")
    private Boolean stock;

}
