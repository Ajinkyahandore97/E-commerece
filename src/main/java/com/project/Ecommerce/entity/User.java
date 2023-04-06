package com.project.Ecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="USER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends  BaseEntityClass{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name="Name")
    private String name;

    @Column(name="Email", unique = true)
    private String email;

    @Column(name="Password", length = 10)
    private String password;

    @Column(name="Gender")
    private  String gender;

    @Column(name="User_Image_Name")
    private String imageName;

    @Column(name="About" , length = 10)
    private String about;
}
