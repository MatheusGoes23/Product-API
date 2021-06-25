package com.matheusgoes23.productapi.service;

import com.matheusgoes23.productapi.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();

    Optional<Product> findById(long id);

    Product save(Product product);
}
