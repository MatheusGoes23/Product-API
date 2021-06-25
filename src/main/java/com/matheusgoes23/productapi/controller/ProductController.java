package com.matheusgoes23.productapi.controller;

import com.matheusgoes23.productapi.model.Product;
import com.matheusgoes23.productapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/products")
    ResponseEntity<List<Product>> getAllProducts() {

        List<Product> productList = productRepository.findAll();

        if (productList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
        }
    }
}
