package com.matheusgoes23.productapi.controller;

import com.matheusgoes23.productapi.model.Product;
import com.matheusgoes23.productapi.service.serviceImpl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    ProductServiceImpl productService;

    @GetMapping("/products")
    ResponseEntity<List<Product>> getAllProducts() {

        List<Product> productList = productService.findAll();

        if (productList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
        }
    }

    @GetMapping("/products/{id}")
    ResponseEntity<Product> getOneProduct(@PathVariable(value = "id") long id) {

        Optional<Product> productOptional = productService.findById(id);

        if (!productOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Product>(productOptional.get(), HttpStatus.OK);
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> saveProduct(@RequestBody @Valid Product product) {
        return new ResponseEntity<Product>(productService.save(product), HttpStatus.CREATED);
    }
}
