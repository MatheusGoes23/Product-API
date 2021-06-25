package com.matheusgoes23.productapi.repository;

import com.matheusgoes23.productapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
