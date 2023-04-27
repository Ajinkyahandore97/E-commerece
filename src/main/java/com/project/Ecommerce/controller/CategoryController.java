package com.project.Ecommerce.controller;

import com.project.Ecommerce.config.AppConstant;
import com.project.Ecommerce.impl.CategoryImpl;
import com.project.Ecommerce.impl.CoverImageImpl;
import com.project.Ecommerce.impl.ProductImpl;
import com.project.Ecommerce.payload.*;
import com.project.Ecommerce.service.CoverImageService;
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
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryImpl categoryImpl;

    @Autowired
    private CoverImageImpl coverImpl;

    @Autowired
    private ProductImpl productImpl;

    @Autowired
    private CoverImageService coverService;

    @Value("${user.profile.cover.path}")
    private String coverImageUploadPath;


    /**
     * @apiNote create category
     * @param categoryDto
     * @return
     */

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){

        log.info("Initiating request for Create Category");

        CategoryDto category = this.categoryImpl.createCategory(categoryDto);

        log.info("Completed request for Create Category");

        return  new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    /**
     * @apiNote update Category Api
     * @param categoryDto
     * @param categoryId
     * @return
     */
    @PutMapping("/{categoryId}")
    public  ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody  CategoryDto categoryDto ,
                                                       @PathVariable Long categoryId){

        log.info("Initiating request for update Category");

        CategoryDto categoryDto1 = this.categoryImpl.updateCategory(categoryDto, categoryId);

        log.info("Completed request for Update Category");

        return  ResponseEntity.ok(categoryDto1);
    }

    /**
     * @apiNote Delete Category Api
     * @param categoryId
     * @return
     */

    @DeleteMapping("/{categoryId}")
    public  ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable Long categoryId){

        log.info("Initiating request for Delete Category");

        this.categoryImpl.deleteCategory(categoryId);

        ApiResponseMessage message= ApiResponseMessage.builder()
                .message(AppConstant.CATEGORY_DELETE)
                .success(true)
                .status(HttpStatus.OK)
                .build();

        log.info("Completed request for Delete Category");

        return new ResponseEntity<>( message ,HttpStatus.OK);
    }

    /**
     * @apiNote Get All Category api with pagination and sorting
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    @GetMapping("/")
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategory (@RequestParam(value ="pageNumber", defaultValue = AppConstant.PAGE_NUMBER,required = false) int pageNumber,
                                                                         @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE,required = false) int pageSize,
                                                                         @RequestParam(value = "sortBy" , defaultValue = AppConstant.DEFAULT_CAT_SORT_BY,required = false) String sortBy,
                                                                         @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR,required = false) String sortDir){
        log.info("Completed request for get All Category");

        return ResponseEntity.ok(this.categoryImpl.getAllCategory(pageNumber,pageSize,sortBy,sortDir));
    }

    /**
     * @apiNote Get single Category api
     * @param categoryId
     * @return
     */


    @GetMapping("/{categoryId}")
    public  ResponseEntity<CategoryDto> getSingleCategory(@PathVariable Long categoryId){

        log.info("Completed request for get Single Category");

        return  ResponseEntity.ok(this.categoryImpl.getSingleCategory(categoryId));
    }

    /**
     * @apiNote cover image upload api
     * @param image
     * @param categoryId
     * @return
     * @throws IOException
     */
    @PostMapping("/image/{categoryId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("categoryImage") MultipartFile image,
                                                         @PathVariable Long categoryId) throws IOException {


        String imageName = coverImpl.uploadImage(image, coverImageUploadPath);

        CategoryDto singleCategory = categoryImpl.getSingleCategory(categoryId);


        singleCategory.setCoverImage(imageName);

        CategoryDto categoryDto = categoryImpl.updateCategory(singleCategory, categoryId);

        ImageResponse imageResponse= ImageResponse.builder().imageName(imageName).message(AppConstant.IMAGE_UPLOAD).success(true).status(HttpStatus.CREATED).build();

        log.info("Completed request for Image create");

        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }

    /**
     * @apiNote Get image api
     * @param categoryId
     * @param response
     * @throws IOException
     */
    @GetMapping("/image/{categoryId}")
    public void serveImage(@PathVariable Long categoryId , HttpServletResponse response) throws IOException {

        CategoryDto singleCategory = categoryImpl.getSingleCategory(categoryId);

        log.info("User image Name :{} " , singleCategory.getCoverImage());

        InputStream resource = coverService.getResource(coverImageUploadPath, singleCategory.getCoverImage());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource,response.getOutputStream());
    }

    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto> createProductWithCategory(
                                      @PathVariable("categoryId") Long categoryId,
                                      @RequestBody ProductDto productDto
    ) {
        ProductDto withCategory = productImpl.createWithCategory(productDto, categoryId);

        return new ResponseEntity<>(withCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductDto> updateCategoryOfProduct(
            @PathVariable Long categoryId,
            @PathVariable Long productId
    ) {
        ProductDto productDto = productImpl.updateCategory(productId, categoryId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    //get products of categories
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<PageableResponse<ProductDto>> getProductsOfCategory(
            @PathVariable Long categoryId,
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.DEFAULT_PRODUCT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir



    ) {

        PageableResponse<ProductDto> response = productImpl.getAllOfCategory(categoryId,pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}

