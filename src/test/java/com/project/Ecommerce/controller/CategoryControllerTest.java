package com.project.Ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Ecommerce.entity.Category;
import com.project.Ecommerce.entity.Product;
import com.project.Ecommerce.impl.CategoryImpl;
import com.project.Ecommerce.impl.ProductImpl;
import com.project.Ecommerce.payload.CategoryDto;
import com.project.Ecommerce.payload.PageableResponse;
import com.project.Ecommerce.payload.ProductDto;
import com.sun.source.tree.ArrayAccessTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @MockBean
    private CategoryImpl categoryImpl;

    @MockBean
    private ProductImpl productImpl;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    Category category1;

    CategoryDto category2;

    CategoryDto category3;

    CategoryDto category4;

    Product product1;

    ProductDto product2;

    ProductDto product3;

    ProductDto product4;

    @BeforeEach
    public  void inti()
    {
        category1 = Category.builder()
                .title("Electronic")
                .description("This is Electronic Category")
                .coverImage("Electronic.png")
                .build();

        category2 = CategoryDto.builder()
                .title("Personal Care")
                .description("This is category of personal care")
                .coverImage("personal.png")
                .build();

        category3 = CategoryDto.builder()
                .title("Fashion")
                .description("This is category of Fashion")
                .coverImage("fashion.png")
                .build();

        category4 = CategoryDto.builder()
                .title("Audio Book")
                .coverImage("audiobook.png")
                .description("This is category of audio book")
                .build();

        product1 = Product.builder()
                .title("Iphone X")
                .description("This is iphone 10")
                .live(true)
                .price(40000)
                .quantity(20)
                .addDate(new Date())
                .discountedPrice(35000)
                .stock(true)
                .build();

        product2 = ProductDto.builder()
                .title("samsung s20")
                .description("This is samsung s20")
                .live(true)
                .price(140000)
                .quantity(250)
                .addDate(new Date())
                .discountedPrice(36000)
                .stock(true)
                .build();

        product3 = ProductDto.builder()
                .title("Mens Dress")
                .description("This is mens dress")
                .live(true)
                .price(80000)
                .quantity(120)
                .addDate(new Date())
                .discountedPrice(135000)
                .stock(true)
                .build();

        product4 = ProductDto.builder()
                .title("LG LED")
                .description("This is LG tv ")
                .live(true)
                .price(5000)
                .quantity(120)
                .addDate(new Date())
                .discountedPrice(56000)
                .stock(true)
                .build();

    }

    @Test
    void createCategoryTest() throws Exception {

        CategoryDto categoryDto = this.mapper.map(category1, CategoryDto.class);

        Mockito.when(categoryImpl.createCategory(Mockito.any())).thenReturn(categoryDto);

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/category/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(category1))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(category1.getTitle()))
                .andExpect(jsonPath("$.description").value(category1.getDescription()))
                .andExpect(jsonPath("$.coverImage").value(category1.getCoverImage()));
    }

    @Test
    void updateCategoryTest() throws Exception {

        Long categoryId =10L;

        CategoryDto categoryDto = this.mapper.map(category1, CategoryDto.class);

        Mockito.when(this.categoryImpl.updateCategory(Mockito.any(),Mockito.anyLong())).thenReturn(categoryDto);

        this.mockMvc.perform(
                MockMvcRequestBuilders.put("/category/"+categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(category1))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(category1.getTitle()))
                .andExpect(jsonPath("$.description").value(category1.getDescription()))
                .andExpect(jsonPath("$.coverImage").value(category1.getCoverImage()));
    }

    @Test
    void deleteCategoryTest() throws Exception {

        Long categoryId=10L;

        CategoryDto categoryDto = this.mapper.map(category1, CategoryDto.class);

        Mockito.doNothing().when(categoryImpl).deleteCategory(categoryId);

        this.mockMvc.perform(
                MockMvcRequestBuilders.delete("/category/"+categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(category1))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getAllCategoryTest() throws Exception {

        PageableResponse<CategoryDto> pageableResponse = new PageableResponse<>();

        pageableResponse.setContent(Arrays.asList(category2,category3,category4));

        pageableResponse.setLastPage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(100);
        pageableResponse.setTotalElements(1000);

        Mockito.when(this.categoryImpl.getAllCategory(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/category/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(category1))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getSingleCategoryTest() throws Exception {

        Long categoryId = 10L;

        CategoryDto categoryDto = this.mapper.map(category1, CategoryDto.class);

        Mockito.when(this.categoryImpl.getSingleCategory(Mockito.anyLong())).thenReturn(categoryDto);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/category/"+ categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(category1))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void uploadUserImageTest() {
    }

    @Test
    void serveImageTest() {
    }

    @Test
    void createProductWithCategory() throws Exception {

        Long categoryId = 10L;

        ProductDto productDto = this.mapper.map(product1, ProductDto.class);

        Mockito.when(productImpl.createWithCategory(Mockito.any(),Mockito.anyLong())).thenReturn(productDto);

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/category/"+categoryId+"/products/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(product1))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void updateCategoryOfProduct() throws Exception {
        Long categoryId=10L;

        Long productId=11L;

        ProductDto productDto1 = this.mapper.map(product1, ProductDto.class);

        Mockito.when(productImpl.updateCategory(Mockito.anyLong(),Mockito.anyLong())).thenReturn(productDto1);

        this.mockMvc.perform(
                MockMvcRequestBuilders.put("/category/"+categoryId+"/products/"+productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(product1))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    void getProductsOfCategory() throws Exception {
        PageableResponse<ProductDto> pageableResponse = new PageableResponse<>();

        pageableResponse.setContent(Arrays.asList(product2,product4,product3));

        pageableResponse.setLastPage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(100);
        pageableResponse.setTotalElements(1000);

        Long categoryId =10L;

        ProductDto productDto = this.mapper.map(product1, ProductDto.class);

        Mockito.when(this.productImpl.getAllOfCategory(Mockito.anyLong(),Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/category/"+categoryId+"/products/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(product1))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    private String convertObjectToJsonString(Object category) {

        try {
            return new ObjectMapper().writeValueAsString(category);
        }catch (Exception e){

            e.printStackTrace();
            return  null;
        }
    }
}