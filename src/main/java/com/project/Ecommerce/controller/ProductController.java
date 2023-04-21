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
@Slf4j
@RequestMapping("/product")
public class ProductController {


    @Autowired
    private ProductImpl productImpl;

    @PostMapping("/")
    public ResponseEntity<ProductDto> getSingleProduct(@Valid @RequestBody ProductDto productDto)
    {
        log.info("Initiating request for Create Porduct");

        ProductDto product = this.productImpl.createProduct(productDto);

        log.info("Completed request for Create Product");

        return  new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public  ResponseEntity<ProductDto> updateCategory(@Valid @RequestBody  ProductDto productDto,
                                                       @PathVariable Long productId){

        log.info("Initiating request for update Product");

        ProductDto productDto1 = this.productImpl.updateProduct(productDto, productId);

        log.info("Completed request for Update Product");

        return  ResponseEntity.ok(productDto1);
    }

    @DeleteMapping("/{productId}")
    public  ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable Long productId){

        log.info("Initiating request for Delete Category");

        this.productImpl.deleteProduct(productId);

        ApiResponseMessage message= ApiResponseMessage.builder()
                .message(AppConstant.PRODUCT_DELETE)
                .success(true)
                .status(HttpStatus.OK)
                .build();

        log.info("Completed request for Delete Product");

        return new ResponseEntity<>( message ,HttpStatus.OK);
    }


    @GetMapping("/{productId}")
    public  ResponseEntity<ProductDto> getSingleCategory(@PathVariable Long productId){

        log.info("Completed request for get Single Category");

        return  ResponseEntity.ok(this.productImpl.getSingleProduct(productId));
    }


    @GetMapping("/")
    public ResponseEntity<PageableResponse<ProductDto>> getAllProduct(@RequestParam(value ="pageNumber", defaultValue = AppConstant.PAGE_NUMBER,required = false) int pageNumber,
                                                                      @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE,required = false) int pageSize,
                                                                      @RequestParam(value = "sortBy" , defaultValue = AppConstant.DEFAULT_CAT_SORT_BY,required = false) String sortBy,
                                                                      @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR,required = false) String sortDir
    )
    {
        log.info("Completed request for get All Product");

        return ResponseEntity.ok(this.productImpl.getAllProduct(pageNumber,pageSize,sortBy,sortDir));
    }

    @GetMapping("/Title/{title}")
    public ResponseEntity<PageableResponse<ProductDto>> getProductByTitle(@RequestParam(value ="pageNumber", defaultValue = AppConstant.PAGE_NUMBER,required = false) int pageNumber,
                                                                      @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE,required = false) int pageSize,
                                                                      @RequestParam(value = "sortBy" , defaultValue = AppConstant.DEFAULT_CAT_SORT_BY,required = false) String sortBy,
                                                                      @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR,required = false) String sortDir,
                                                                      @PathVariable String title
    )
    {
        log.info("Completed request for get All Product By Titile");

        return ResponseEntity.ok(this.productImpl.searchByTitle(title,pageNumber,pageSize,sortBy,sortDir));
    }


    @GetMapping("/Live/{live}")
    public ResponseEntity<PageableResponse<ProductDto>> getProductByTrue(@RequestParam(value ="pageNumber", defaultValue = AppConstant.PAGE_NUMBER,required = false) int pageNumber,
                                                                          @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE,required = false) int pageSize,
                                                                          @RequestParam(value = "sortBy" , defaultValue = AppConstant.DEFAULT_CAT_SORT_BY,required = false) String sortBy,
                                                                          @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR,required = false) String sortDir,
                                                                          @PathVariable Boolean live
    )
    {
        log.info("Completed request for get All Product By Live");

        return ResponseEntity.ok(this.productImpl.getAllLive(live,pageNumber,pageSize,sortBy,sortDir));
    }

}
