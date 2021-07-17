package com.matheusgoes23.productapi.controller;

import com.matheusgoes23.productapi.model.Product;
import com.matheusgoes23.productapi.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api")
@Api(value = "REST API Products")
public class ProductController {

    @Autowired
    ProductService productService;

    @ApiOperation(value = "Returns a list of Products")
    @GetMapping("/products")
    ResponseEntity<Page<Product>> getAllProducts(@PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<Product> productList = productService.findAll(pageable);
        for (Product product : productList) {
            long id = product.getId();
            product.add(linkTo(methodOn(ProductController.class).getProductById(id)).withSelfRel());
        }

        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @ApiOperation(value = "Returns a single Product by Id")
    @GetMapping("/products/{id}")
    ResponseEntity<Product> getProductById(@PathVariable(value = "id") Long id) {

        Product product = productService.findById(id);
        product.add(linkTo(methodOn(ProductController.class).getAllProducts(null)).withRel("Product List"));

        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

    @ApiOperation(value = "Returns a list Product by Name")
    @GetMapping("/products/find")
    ResponseEntity<List<Product>> getProductByName(@RequestParam String name) {

        List<Product> productList = productService.findByName(name);
        for (Product product : productList) {
            long id = product.getId();
            product.add(linkTo(methodOn(ProductController.class).getProductById(id)).withSelfRel());
        }

        return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
    }

    @ApiOperation(value = "Save a Product")
    @PostMapping("/products")
    public ResponseEntity<Product> saveProduct(@Valid @RequestBody Product product) {
        product.add(linkTo(methodOn(ProductController.class).getAllProducts(null)).withRel("Product List"));
        return new ResponseEntity<Product>(productService.save(product), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update a Product")
    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") Long id, @Valid @RequestBody Product product) {

        Product productUpdated = productService.update(id, product);
        productUpdated.add(linkTo(methodOn(ProductController.class).getAllProducts(null)).withRel("Product List"));

        return new ResponseEntity<Product>(productUpdated, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a Product")
    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(value = "id") Long id) {
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
