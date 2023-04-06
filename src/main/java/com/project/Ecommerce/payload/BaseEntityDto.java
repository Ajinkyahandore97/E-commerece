package com.project.Ecommerce.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntityDto {

    private String isActive;
    private LocalDate createDate;
    private LocalDate updateDate;
}
