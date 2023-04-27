package com.project.Ecommerce.repository;

import com.project.Ecommerce.entity.Category;
import com.project.Ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {


    Page<Product> findAllByTitleContaining(String title, Pageable pageable);

    Page<Product> findByLiveTrue(Boolean live ,Pageable pageable);


    Page<Product> findByCategory(Category category, Pageable pageable);
}
