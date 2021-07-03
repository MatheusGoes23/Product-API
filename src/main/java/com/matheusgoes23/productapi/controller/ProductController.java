package com.matheusgoes23.productapi.controller;

import com.matheusgoes23.productapi.model.Product;
import com.matheusgoes23.productapi.service.serviceImpl.ProductServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api")
@Api(value = "API REST Products")
public class ProductController {

    @Autowired
    ProductServiceImpl productService;

    @ApiOperation(value = "Returns a list of Products")
    @GetMapping("/products")
    ResponseEntity<List<Product>> getAllProducts() {

        List<Product> productList = productService.findAll();

        if (productList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            for (Product product : productList) {
                long id = product.getId();
                product.add(linkTo(methodOn(ProductController.class).getOneProduct(id)).withSelfRel());
            }
            return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Returns a single Product")
    @GetMapping("/products/{id}")
    ResponseEntity<Product> getOneProduct(@PathVariable(value = "id") long id) {

        Optional<Product> productOptional = productService.findById(id);

        if (!productOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            productOptional.get().add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("Product List"));
            return new ResponseEntity<Product>(productOptional.get(), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Save a Product")
    @PostMapping("/products")
    public ResponseEntity<Product> saveProduct(@RequestBody @Valid Product product) {
        product.add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("Product List"));
        return new ResponseEntity<Product>(productService.save(product), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Delete a Product")
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

    @ApiOperation(value = "Update a Product")
    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") long id, @RequestBody @Valid Product product) {

        Optional<Product> productOptional = productService.findById(id);

        if (!productOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            product.setId(productOptional.get().getId());
            productOptional.get().add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("Product List"));
            return new ResponseEntity<Product>(productService.save(product), HttpStatus.OK);
        }
    }
}
