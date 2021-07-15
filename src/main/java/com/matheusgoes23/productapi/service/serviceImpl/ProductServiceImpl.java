package com.matheusgoes23.productapi.service.serviceImpl;

import com.matheusgoes23.productapi.exception.ProductNotFoundException;
import com.matheusgoes23.productapi.model.Product;
import com.matheusgoes23.productapi.repository.ProductRepository;
import com.matheusgoes23.productapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> findAll() {

        List<Product> productList = productRepository.findAll();

        if (productList.isEmpty()) {
            throw new ProductNotFoundException();
        } else {
            return productList;
        }
    }

    @Override
    public Product findById(Long id) {

        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            return productOptional.get();
        } else {
            throw new ProductNotFoundException();
        }
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product update(Long id, Product product) {

        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            product.setId(productOptional.get().getId());
            return productRepository.save(product);
        } else {
            throw new ProductNotFoundException();
        }
    }

    @Override
    public void delete(Long id) {

        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            productRepository.delete(productOptional.get());
        } else {
            throw new ProductNotFoundException();
        }
    }
}
