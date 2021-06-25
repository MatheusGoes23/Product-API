package com.matheusgoes23.productapi.service.serviceImpl;

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
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(long id) {
        return Optional.of(productRepository.findById(id).get());
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(Product product) {
        productRepository.delete(product);
    }
}
