package com.project.Ecommerce.impl;

import com.project.Ecommerce.entity.Product;
import com.project.Ecommerce.payload.PageableResponse;
import com.project.Ecommerce.payload.ProductDto;
import com.project.Ecommerce.repository.ProductRepository;
import com.project.Ecommerce.service.ProductService;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductImplTest {

    @MockBean
    private ProductRepository productRepo;

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper modelmapper;

    Product product1;

    Product product2;

    Product product3;

    Product product4;

    List<Product> listOfProduct;

    ProductDto productDto;

    @BeforeEach
    public  void inti()
    {
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

        productDto = ProductDto.builder()
                .title("iphone X")
                .description("This is new iphone")
                .discountedPrice(30000)
                .quantity(15)
                .addDate(new Date())
                .live(true)
                .stock(true)
                .build();

        product2 = Product.builder()
                .title("samsung s20")
                .description("This is samsung s20")
                .live(true)
                .price(140000)
                .quantity(250)
                .addDate(new Date())
                .discountedPrice(36000)
                .stock(true)
                .build();

        product3 = Product.builder()
                .title("Mens Dress")
                .description("This is mens dress")
                .live(true)
                .price(80000)
                .quantity(120)
                .addDate(new Date())
                .discountedPrice(135000)
                .stock(true)
                .build();

        product4 = Product.builder()
                .title("LG LED")
                .description("This is LG tv ")
                .live(true)
                .price(5000)
                .quantity(120)
                .addDate(new Date())
                .discountedPrice(56000)
                .stock(true)
                .build();

        listOfProduct= new ArrayList<>();
        listOfProduct.add(product1);
        listOfProduct.add(product2);
        listOfProduct.add(product3);
        listOfProduct.add(product4);


    }
    @Test
    void createProductTest() {

        Mockito.when(productRepo.save(Mockito.any())).thenReturn(product1);

        ProductDto productDto = productService.createProduct(modelmapper.map(product1, ProductDto.class));

        Assertions.assertEquals(product1.getDiscountedPrice(),productDto.getDiscountedPrice());

    }

    @Test
    void updateProductTest() {

        Long productId=78L;

        Mockito.when(productRepo.findById(productId)).thenReturn(Optional.of(product1));

        Mockito.when(productRepo.save(Mockito.any())).thenReturn(product1);

        ProductDto productDto1 = productService.updateProduct(productDto, productId);

        Assertions.assertEquals(product1.getTitle(),productDto1.getTitle());
    }

    @Test
    void deleteProductTest() {

        Long productId=15L;

        Mockito.when(productRepo.findById(productId)).thenReturn(Optional.of(product1));

        productService.deleteProduct(productId);

        Mockito.verify(productRepo,Mockito.times(1)).save(product1);
    }

    @Test
    void getSingleProductTest() {

        Long productId= 45L;

        Mockito.when(productRepo.findById(productId)).thenReturn(Optional.of(product2));

        ProductDto singleProduct = productService.getSingleProduct(productId);

        Assertions.assertEquals(product2.getTitle(),singleProduct.getTitle());
    }

    @Test
    void getAllProductTest() {

        PageImpl<Product> page = new PageImpl<>(listOfProduct);

        Mockito.when(productRepo.findAll((Pageable) Mockito.any())).thenReturn(page);

        PageableResponse<ProductDto> allProduct = productService.getAllProduct(1, 2, "title", "asc");

        Assertions.assertEquals(4,allProduct.getContent().size());
    }

    @Test
    void searchByTitleTest() {

        String title="samsung s20";

        PageImpl<Product> page = new PageImpl<>(listOfProduct);

        Mockito.when(productRepo.findAllByTitleContaining(Mockito.anyString(),Mockito.any())).thenReturn(new PageImpl<>(listOfProduct));

        PageableResponse<ProductDto> productDtoPageableResponse = productService.searchByTitle("samsung s20", 1, 2, "title", "asc");

        Assertions.assertEquals(4,productDtoPageableResponse.getContent().size());
    }

    @Test
    void getAllLiveTest() {

        PageImpl<Product> page = new PageImpl<>(listOfProduct);

        Mockito.when(productRepo.findByLiveTrue(Mockito.anyBoolean(),Mockito.any())).thenReturn(new PageImpl<>(listOfProduct));

        PageableResponse<ProductDto> allLive = productService.getAllLive(true, 1, 2, "title", "asc");

        Assertions.assertEquals(listOfProduct.size(),allLive.getContent().size());
    }
}