package com.project.Ecommerce.impl;

import com.project.Ecommerce.config.AppConstant;
import com.project.Ecommerce.entity.Category;
import com.project.Ecommerce.exception.ResourceNotFoundException;
import com.project.Ecommerce.helper.Helper;
import com.project.Ecommerce.payload.CategoryDto;
import com.project.Ecommerce.payload.PageableResponse;
import com.project.Ecommerce.repository.CategoryRepository;
import com.project.Ecommerce.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CategoryImpl  implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        Category cat = this.modelMapper.map(categoryDto, Category.class);

        log.info("Initiating DAO Call for Create Category");

        cat.setIsActive(AppConstant.YES);

        Category savedCategory = this.categoryRepo.save(cat);

        log.info("Completing DAO Call for Create Category");

        return  this.modelMapper.map(savedCategory ,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {

        log.info("Initiating DAO Call for update the user data with categoryId {} : " , categoryId);

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_NOT_FOUND));

        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());

        Category savedCategory = this.categoryRepo.save(category);

        log.info("Completing dao call for update the category data with  categoryId {} :",categoryId);

        return this.modelMapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public void deleteCategory(Long categoryId) {

        log.info("Initiating DAO Call for Delete Category  with categoryId {} ", categoryId);

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_NOT_FOUND));

        category.setIsActive(AppConstant.NO);

        categoryRepo.save(category);

        log.info("Completing DAO Call for Delete Category with CategoryId {} ", categoryId);

    }

    @Override
    public PageableResponse<CategoryDto> getAllCategory(int pageNumber, int pageSize, String sortBy, String sortDir) {

        log.info("Initiating DAO Call for get All Category");

        Sort sort =(sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Category> page =this.categoryRepo.findAll(pageable);

        log.info("Completing DAO Call for get All Category");

        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);

        return pageableResponse;
    }

    @Override
    public CategoryDto getSingleCategory(Long categoryId) {

        log.info(" Initiating DAO Call for get Single Category with {}  ", categoryId);

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_NOT_FOUND));

        log.info("Completing DAO Call for get Single Category with {} ", categoryId);

        return this.modelMapper.map(category , CategoryDto.class);
    }
}
