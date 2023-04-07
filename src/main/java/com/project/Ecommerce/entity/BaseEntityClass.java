package com.project.Ecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntityClass {


    @Column(name = "Create_Date" , updatable = false)
    @UpdateTimestamp
    private LocalDate createDate;

    @Column(name = "Update_Date",insertable = false)
    @UpdateTimestamp
    private LocalDate updateDate;

    @Column(name = "isActive_switch")
    private String isActive;
}
