package com.project.Ecommerce.controller;

import com.project.Ecommerce.config.AppConstant;
import com.project.Ecommerce.impl.ProductImpl;
import com.project.Ecommerce.payload.ApiResponseMessage;
import com.project.Ecommerce.payload.PageableResponse;
import com.project.Ecommerce.payload.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {

    @Autowired
    private ProductImpl productImpl;

    @PostMapping("/")
    public ResponseEntity<ProductDto> createUser(@Valid @RequestBody ProductDto productDto) {

        log.info("Initiating request for Create User");

        ProductDto product = this.productImpl.createProduct(productDto);

        log.info("Completed request for Create User");

        return new ResponseEntity<>(productDto, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateUser(@Valid @RequestBody ProductDto productDto, @PathVariable Long productId) {

        log.info("Initiating request for update User");

        ProductDto productDto1 = this.productImpl.updateProduct(productDto, productId);

        log.info("Completed request for Update User");

        return ResponseEntity.ok(productDto1);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable Long productId) {

        log.info("Initiating request for Delete Product");

        this.productImpl.deleteProduct(productId);
        ApiResponseMessage message = ApiResponseMessage
                .builder()
                .message(AppConstant.PRODUCT_DELETE)
                .success(true)
                .status(HttpStatus.OK)
                .build();

        log.info("Completed request for Delete Product");

        return new ResponseEntity<>(message, HttpStatus.OK);
    }


    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable Long productId) {

        log.info("Completed request for get Single Product ");

        return ResponseEntity.ok(this.productImpl.getSingleProduct(productId));
    }

    @GetMapping("/")
    public ResponseEntity<PageableResponse<ProductDto>> getAllProduct(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.DEFAULT_PRODUCT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir
    ) {

        log.info("Completed request for get All Product");

        PageableResponse<ProductDto> allProduct = productImpl.getAllProduct(pageNumber, pageSize, sortDir, sortBy);

        return new ResponseEntity<>(allProduct, HttpStatus.OK);
    }


    @GetMapping("/getAll/")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLive(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.DEFAULT_PRODUCT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir
    ) {

        log.info("Completed request for get All Product");

        PageableResponse<ProductDto> allProduct = productImpl.getAllProduct(pageNumber, pageSize, sortDir, sortBy);

        return new ResponseEntity<>(allProduct, HttpStatus.OK);
    }

    @GetMapping("/Title/{title}")
    public ResponseEntity<PageableResponse<ProductDto>> getByTitle(
            @PathVariable String title,
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.DEFAULT_PRODUCT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir

    ) {

        PageableResponse<ProductDto> product = productImpl.searchByTitle(title, pageNumber, pageSize, sortBy, sortDir);

        log.info("Completed request for get All Product");

        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
