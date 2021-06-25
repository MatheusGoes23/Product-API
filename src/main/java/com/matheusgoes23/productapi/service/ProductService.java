package com.matheusgoes23.productapi.service;

import com.matheusgoes23.productapi.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findById(long id);
}
