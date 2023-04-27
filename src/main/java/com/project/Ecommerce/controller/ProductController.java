package com.project.Ecommerce.controller;

import com.project.Ecommerce.config.AppConstant;
import com.project.Ecommerce.impl.ProductImpl;
import com.project.Ecommerce.payload.*;
import com.project.Ecommerce.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

@RestController
@Slf4j
@RequestMapping("/product")
public class ProductController {


    @Autowired
    private ProductImpl productImpl;

    @Autowired
    private FileService fileService;

    @Value("${product.image.path}")
    private String imagePath;

    @PostMapping("/")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto)
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
    public  ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable Long productId){

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
    public  ResponseEntity<ProductDto> getSingleProduct(@PathVariable Long productId){

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
        log.info("Completed request for get All Product By Title");

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

    @PostMapping("/Image/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage(
            @PathVariable Long productId,
            @RequestParam("productImage")MultipartFile image) throws IOException {

        String uploadImage = fileService.uploadImage(image, imagePath);

        ProductDto singleProduct = productImpl.getSingleProduct(productId);

        singleProduct.setProductImage(uploadImage);

        ProductDto productDto = productImpl.updateProduct(singleProduct, productId);

        ImageResponse imageUploadSucessfully = ImageResponse.builder().imageName(productDto.getProductImage()).message("Image upload Sucessfully").status(HttpStatus.CREATED).build();

        return  new ResponseEntity<>(imageUploadSucessfully,HttpStatus.CREATED);

    }

    @GetMapping("/Image/{productId}")
    public void serveImage(@PathVariable Long productId, HttpServletResponse response) throws IOException {



        ProductDto singleProduct = productImpl.getSingleProduct(productId);

        InputStream resource = fileService.getResource(imagePath, singleProduct.getProductImage());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource,response.getOutputStream());
    }




}
