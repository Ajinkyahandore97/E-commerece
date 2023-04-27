package com.project.Ecommerce.service;

import com.project.Ecommerce.payload.PageableResponse;
import com.project.Ecommerce.payload.ProductDto;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto, Long productId);

    void deleteProduct(Long productId);


    ProductDto getSingleProduct(Long productId);

    PageableResponse<ProductDto> getAllProduct(int pageNumber, int pageSize, String sortBy, String sortDir);

    PageableResponse<ProductDto> searchByTitle(String title, int pageNumber, int pageSize, String sortBy, String sortDir);

    PageableResponse<ProductDto> getAllLive(Boolean live ,int pageNumber, int pageSize, String sortBy, String sortDir);


    ProductDto createWithCategory(ProductDto productDto,Long categoryId);


    ProductDto updateCategory(Long productId,Long categoryId);


    PageableResponse<ProductDto> getAllOfCategory(Long categoryId, int pageNumber, int pageSize, String sortBy, String sortDir);
}
