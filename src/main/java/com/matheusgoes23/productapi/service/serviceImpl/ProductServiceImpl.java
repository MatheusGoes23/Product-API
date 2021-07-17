package com.matheusgoes23.productapi.service.serviceImpl;

import com.matheusgoes23.productapi.exception.ProductNoContentException;
import com.matheusgoes23.productapi.exception.ProductNotFoundException;
import com.matheusgoes23.productapi.model.Product;
import com.matheusgoes23.productapi.repository.ProductRepository;
import com.matheusgoes23.productapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Page<Product> findAll(Pageable pageable) {

        Page<Product> productList = productRepository.findAll(pageable);

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
    public List<Product> findByName(String name) {

        List<Product> productList = productRepository.findByName(name);

        if (productList.isEmpty()) {
            throw new ProductNotFoundException();
        } else {
            return productList;
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
            throw new ProductNoContentException();
        }
    }
}
