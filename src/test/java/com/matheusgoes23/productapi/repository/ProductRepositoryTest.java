package com.matheusgoes23.productapi.repository;

import com.matheusgoes23.productapi.model.Product;
import com.matheusgoes23.productapi.util.ProductCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for Product Repository")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("Save persists product when successful")
    void save_PersistProduct_WhenSuccessful() {

        Product productToBeSaved = ProductCreator.createProductToBeSalved();

        Product productSaved = this.productRepository.save(productToBeSaved);

        Assertions.assertThat(productSaved)
                .isNotNull()
                .isEqualTo(productToBeSaved);

        Assertions.assertThat(productSaved.getId()).isNotNull();
    }

    @Test
    @DisplayName("Save updates product when successful")
    void save_UpdatesProduct_WhenSuccessful() {

        Product productToBeSaved = ProductCreator.createProductToBeSalved();

        Product productSaved = this.productRepository.save(productToBeSaved);

        productSaved.setName("Laptop");
        productSaved.setValue(BigDecimal.valueOf(3600));
        productSaved.setDescription("Acer Nitro");
        productSaved.setExpirationDate(LocalDate.parse("25-12-2033", DateTimeFormatter.ofPattern("dd-MM-yyyy")));

        Product productUpdated = this.productRepository.save(productToBeSaved);

        Assertions.assertThat(productSaved)
                .isNotNull()
                .isEqualTo(productToBeSaved);

        Assertions.assertThat(productUpdated.getId()).isNotNull();
    }

    @Test
    @DisplayName("Delete removes product when successful")
    void delete_RemovesProduct_WhenSuccessful() {

        Product productToBeSaved = ProductCreator.createProductToBeSalved();

        Product productSaved = this.productRepository.save(productToBeSaved);

        this.productRepository.delete(productSaved);

        Optional<Product> productOptional = this.productRepository.findById(productSaved.getId());

        Assertions.assertThat(productOptional).isEmpty();
    }

    @Test
    @DisplayName("Find By Name returns list of product when successful")
    void findByName_ReturnsListOfProduct_WhenSuccessful() {

        Product productToBeSaved = ProductCreator.createProductToBeSalved();

        Product productSaved = this.productRepository.save(productToBeSaved);

        String name = productSaved.getName();

        List<Product> productList = this.productRepository.findByName(name);

        Assertions.assertThat(productList).
                isNotEmpty()
                .contains(productSaved);
    }

    @Test
    @DisplayName("Find By Name returns empty list when no product is found")
    void findByName_ReturnsEmptyList_WhenProductIsNotFound() {

        List<Product> productList = this.productRepository.findByName("ProductNotExistent");

        Assertions.assertThat(productList).isEmpty();
    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when name is empty")
    void save_ThrowsConstraintViolationException_WhenNameIsEmpty() {

        Product product = new Product();

        Assertions.assertThatThrownBy(() -> this.productRepository.save(product))
                .isInstanceOf(ConstraintViolationException.class);
    }
}