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

    PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir);
}
