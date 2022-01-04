package com.ranajit.productservice.repository;

import com.ranajit.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Product findById(long id);
}
