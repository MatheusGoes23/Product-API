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

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(value = "id") long id) {

        Optional<Product> productOptional = productService.findById(id);

        if (!productOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            productService.delete(productOptional.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") long id, @RequestBody @Valid Product product) {

        Optional<Product> productOptional = productService.findById(id);

        if (!productOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            product.setId(productOptional.get().getId());
            return new ResponseEntity<Product>(productService.save(product), HttpStatus.OK);
        }
    }
}
