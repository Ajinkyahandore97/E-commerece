package com.project.Ecommerce.impl;

import com.project.Ecommerce.entity.Category;
import com.project.Ecommerce.payload.CategoryDto;
import com.project.Ecommerce.payload.PageableResponse;
import com.project.Ecommerce.repository.CategoryRepository;
import com.project.Ecommerce.service.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryImplTest {

    @MockBean
    private CategoryRepository categoryRepo;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper mapper;

    Category category1;

    Category  category2;

    Category category3;

    Category category4;


    List<Category> listOfCategory;

    CategoryDto categoryDto;
    @BeforeEach
    public void  inti(){

        category1 = Category.builder()
                .title("Electronic")
                .description("This is category of electronic store")
                .coverImage("electronic.png")
                .build();

        categoryDto = CategoryDto.builder()
                .title("Electronic Appliances")
                .coverImage("electronicappliences.png")
                .description("This is store of electronic appliences")
                .build();

        category2 = Category.builder()
                .title("Fashion")
                .description("This is category of Fashion store")
                .coverImage("fashion.png")
                .build();


        category3 = Category.builder()
                .title("food")
                .description("This is category of food")
                .coverImage("food.png")
                .build();

        category4 = Category.builder()
                .title("Baby care")
                .description("This is category of baby care")
                .coverImage("babycare.png")
                .build();

        listOfCategory = new ArrayList<>();
        listOfCategory.add(category1);
        listOfCategory.add(category2);
        listOfCategory.add(category3);
        listOfCategory.add(category4);


    }
    @Test
    void createCategory() {

        Mockito.when(categoryRepo.save(Mockito.any())).thenReturn(category1);

        CategoryDto categoryDto = categoryService.createCategory(mapper.map(category1, CategoryDto.class));

        Assertions.assertEquals(category1.getTitle(),categoryDto.getTitle());
    }

    @Test
    void updateCategory() {

        Long categoryId=10L;

        Mockito.when(categoryRepo.findById(categoryId)).thenReturn(Optional.of(category1));

        Mockito.when(categoryRepo.save(Mockito.any())).thenReturn(category1);

        CategoryDto categoryDto1 = categoryService.updateCategory(categoryDto, categoryId);

        Assertions.assertEquals(categoryDto.getTitle(),categoryDto1.getTitle());

    }

    @Test
    void deleteCategory() {

        Long categoryId=10L;

        Mockito.when(categoryRepo.findById(categoryId)).thenReturn(Optional.of(category1));

        categoryService.deleteCategory(categoryId);

        Mockito.verify(categoryRepo,Mockito.times(1)).save(category1);


    }

    @Test
    void getAllCategory() {

        PageImpl<Category> page = new PageImpl<>(listOfCategory);

        Mockito.when(categoryRepo.findAll((Pageable) Mockito.any())).thenReturn(page);

        PageableResponse<CategoryDto> allCategory = categoryService.getAllCategory(1, 2, "title", "asc");

        Assertions.assertEquals(4,allCategory.getContent().size());

    }

    @Test
    void getSingleCategory() {

        Long  categoryId= 10L;

        Mockito.when(categoryRepo.findById(categoryId)).thenReturn(Optional.of(category1));

        CategoryDto singleCategory = categoryService.getSingleCategory(categoryId);

        Assertions.assertEquals(category1.getTitle(),singleCategory.getTitle());
    }
}