package com.matheusgoes23.productapi.service;

import com.matheusgoes23.productapi.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Page<Product> findAll(Pageable pageable);

    Product findById(Long id);

    List<Product> findByName(String name);

    Product save(Product product);

    Product update(Long id, Product product);

    void delete(Long id);
}
