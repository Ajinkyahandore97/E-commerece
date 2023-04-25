package com.project.Ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Ecommerce.entity.Product;
import com.project.Ecommerce.impl.ProductImpl;
import com.project.Ecommerce.payload.CategoryDto;
import com.project.Ecommerce.payload.PageableResponse;
import com.project.Ecommerce.payload.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @MockBean
    private ProductImpl productImpl;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelMapper mapper;

    private  Product product1;

    private ProductDto product2;

    private ProductDto product3;

    private ProductDto product4;

    @BeforeEach
    public void init()
    {
        product1 = Product.builder()
                .title("Iphone X")
                .description("This is iphone ")
                .quantity(4500)
                .price(150000)
                .live(true)
                .stock(true)
                .discountedPrice(65000)
                .addDate(new Date())
                .build();

        product2 = ProductDto.builder()
                .title("Men's T-shirt")
                .description("This is men's Fashion")
                .live(true)
                .quantity(45)
                .price(2300)
                .addDate(new Date())
                .discountedPrice(700)
                .build();

        product3 = ProductDto.builder()
                .title("LG TV")
                .description("This is LG tv")
                .live(true)
                .quantity(450)
                .price(45000)
                .addDate(new Date())
                .discountedPrice(35000)
                .build();

        product4 = ProductDto.builder()
                .title("Headphone")
                .description("Bluetooth Headphone")
                .live(true)
                .quantity(455)
                .price(2300)
                .stock(true)
                .addDate(new Date())
                .discountedPrice(1500)
                .build();


    }

    @Test
    void createProductTest() throws Exception {

        ProductDto productDto = this.mapper.map(product1, ProductDto.class);

        Mockito.when(this.productImpl.createProduct(Mockito.any())).thenReturn(productDto);

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/product/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(product1))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(product1.getTitle()))
                .andExpect(jsonPath("$.description").value(product1.getDescription()))
                .andExpect(jsonPath("$.live").value(product1.getLive()))
                .andExpect(jsonPath("$.quantity").value(product1.getQuantity()))
                .andExpect(jsonPath("$.price").value(product1.getPrice()))
                .andExpect(jsonPath("$.stock").value(product1.getStock()))
                .andExpect(jsonPath("$.discountedPrice").value(product1.getDiscountedPrice()));
    }

    @Test
    void updateCategoryTest() throws Exception {

        Long productId= 10L;

        ProductDto productDto = this.mapper.map(product1, ProductDto.class);

        Mockito.when(productImpl.updateProduct(Mockito.any(),Mockito.anyLong())).thenReturn(productDto);

        this.mockMvc.perform(
                MockMvcRequestBuilders.put("/product/"+productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(product1))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(product1.getTitle()))
                .andExpect(jsonPath("$.description").value(product1.getDescription()))
                .andExpect(jsonPath("$.live").value(product1.getLive()))
                .andExpect(jsonPath("$.quantity").value(product1.getQuantity()))
                .andExpect(jsonPath("$.price").value(product1.getPrice()))
                .andExpect(jsonPath("$.stock").value(product1.getStock()))
                .andExpect(jsonPath("$.discountedPrice").value(product1.getDiscountedPrice()));
    }

    @Test
    void deleteProductTest() throws Exception {

        Long productId= 10L;

        ProductDto productDto = this.mapper.map(product1, ProductDto.class);

        Mockito.doNothing().when(productImpl).deleteProduct(productId);

        this.mockMvc.perform(
                MockMvcRequestBuilders.delete("/product/"+productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(product1))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void getSingleCategoryTest() throws Exception {

        Long productId = 10L;

        ProductDto productDto = this.mapper.map(product1, ProductDto.class);

        Mockito.when(productImpl.getSingleProduct(Mockito.anyLong())).thenReturn(productDto);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/product/"+productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(product1))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.description").value(product1.getDescription()))
                .andExpect(jsonPath("$.live").value(product1.getLive()))
                .andExpect(jsonPath("$.quantity").value(product1.getQuantity()))
                .andExpect(jsonPath("$.price").value(product1.getPrice()))
                .andExpect(jsonPath("$.stock").value(product1.getStock()))
                .andExpect(jsonPath("$.discountedPrice").value(product1.getDiscountedPrice()));

    }

    @Test
    void getAllProductTest() throws Exception {

        PageableResponse<ProductDto> pageableResponse = new PageableResponse<>();

        pageableResponse.setContent(Arrays.asList(product2,product3,product4));

        pageableResponse.setLastPage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(100);
        pageableResponse.setTotalElements(1000);

        Mockito.when(productImpl.getAllProduct(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/product/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(product1))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getProductByTitle() throws Exception {

        PageableResponse<ProductDto> pageableResponse = new PageableResponse<>();

        pageableResponse.setContent(Arrays.asList(product2,product3,product4));

        pageableResponse.setLastPage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(100);
        pageableResponse.setTotalElements(1000);

        String title = "Iphone X";


        Mockito.when(productImpl.searchByTitle(Mockito.anyString(),Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/product/Title/"+title)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(product1))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getProductByTrueTest() throws Exception {

        PageableResponse<ProductDto> pageableResponse = new PageableResponse<>();

        pageableResponse.setContent(Arrays.asList(product2,product3,product4));

        pageableResponse.setLastPage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(100);
        pageableResponse.setTotalElements(1000);

        Boolean live =true;


        Mockito.when(productImpl.getAllLive(Mockito.anyBoolean(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/product/Live/"+live)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(product1))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private String convertObjectToJsonString(Object Product) {

        try {
            return new ObjectMapper().writeValueAsString(product1);
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }

}