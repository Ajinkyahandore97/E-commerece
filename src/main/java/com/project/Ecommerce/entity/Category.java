package com.project.Ecommerce.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="Category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category extends BaseEntityClass{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(name = "Title")
    private String title;

    @Column(name = "Description")
    private String description;

    @Column(name="coverImage")
    private String  coverImage;
}
