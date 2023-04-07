package com.project.Ecommerce.service;

import com.project.Ecommerce.payload.CategoryDto;
import com.project.Ecommerce.payload.PageableResponse;

public interface CategoryService {

    // create
    CategoryDto createCategory(CategoryDto categoryDto);

    //update
    CategoryDto updateCategory(CategoryDto categoryDto , Long categoryId);

    //delete
    void deleteCategory(Long categoryId);

    //all category
    PageableResponse<CategoryDto> getAllCategory(int pageNumber , int pageSize, String sortBy, String sortDir);

    //single category
    CategoryDto getSingleCategory(Long categoryId);
}
