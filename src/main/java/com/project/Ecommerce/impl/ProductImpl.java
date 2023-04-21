package com.project.Ecommerce.impl;

import com.project.Ecommerce.config.AppConstant;
import com.project.Ecommerce.entity.Product;
import com.project.Ecommerce.exception.ResourceNotFoundException;
import com.project.Ecommerce.helper.Helper;
import com.project.Ecommerce.payload.PageableResponse;
import com.project.Ecommerce.payload.ProductDto;
import com.project.Ecommerce.repository.ProductRepository;
import com.project.Ecommerce.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class ProductImpl implements ProductService {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private ModelMapper mapper;



    @Override
    public ProductDto createProduct(ProductDto productDto) {

        Product product = this.mapper.map(productDto, Product.class);

        product.setAddDate(new Date());

        log.info("Initiating DAO Call for Create Product");

        product.setIsActive(AppConstant.YES);

        Product savedproduct = this.productRepo.save(product);

        log.info("Completing DAO Call for Create Product");

        return this.mapper.map(savedproduct,ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, Long productId) {


        log.info("Initiating DAO Call for update the user data with productId {} : " , productId);

        Product product = this.productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.PRODUCT_NOT_FOUND));

        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setAddDate(productDto.getAddDate());
        product.setLive(productDto.getLive());
        product.setLive(productDto.getLive());

        Product save = this.productRepo.save(product);

        log.info("Completing dao call for update the category data with  productId {} :",productId);

        return this.mapper.map(save,ProductDto.class);
    }

    @Override
    public void deleteProduct(Long productId) {

        log.info("Initiating DAO Call for Delete Product  with productId {} ", productId);

        Product product = this.productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.PRODUCT_NOT_FOUND));

        product.setIsActive(AppConstant.NO);

        productRepo.save(product);

        log.info("Completing DAO Call for Delete Product with productId {} ", productId);
    }

    @Override
    public ProductDto getSingleProduct(Long productId) {

        log.info(" Initiating DAO Call for get Single Product with {}  ", productId);

        Product product = this.productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.PRODUCT_NOT_FOUND));

        log.info("Completing DAO Call for get Single Product with {} ", productId);

        return this.mapper.map(product,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllProduct(int pageNumber, int pageSize, String sortBy, String sortDir) {

        log.info("Initiating DAO Call for get All Product");

        Sort sort = (sortDir.equalsIgnoreCase("asc")) ?(Sort.by(sortBy).ascending()) :(Sort.by(sortBy).ascending());

        PageRequest page = PageRequest.of(pageNumber, pageSize, sort);

        Page<Product> allproduct = this.productRepo.findAll(page);

        log.info("Completing DAO Call for get All product");

        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(allproduct, ProductDto.class);

        return pageableResponse;
    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String title, int pageNumber, int pageSize, String sortBy, String sortDir) {

        log.info("Initiating DAO Call for Search of Product by title");

       Sort sort = (sortDir.equalsIgnoreCase("asc")) ?(Sort.by(sortBy).ascending()) :(Sort.by(sortBy).ascending());

        PageRequest page = PageRequest.of(pageNumber, pageSize, sort);

        Page<Product> allByTitleContaining = this.productRepo.findAllByTitleContaining(title, page);

        log.info("Completing DAO Call for Search of Product by title ");

        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(allByTitleContaining, ProductDto.class);

        return pageableResponse;
    }

    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir) {

        log.info("Initiating DAO Call for Search of Product live");

        Sort sort = (sortDir.equalsIgnoreCase("asc")) ?(Sort.by(sortBy).ascending()) :(Sort.by(sortBy).ascending());

        PageRequest page = PageRequest.of(pageNumber, pageSize, sort);

        Page<Product> byLiveTrue = this.productRepo.findByLiveTrue(page);

        log.info("Completing DAO Call for Search of Product by Live");

        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(byLiveTrue, ProductDto.class);

        return  pageableResponse;
    }
}
