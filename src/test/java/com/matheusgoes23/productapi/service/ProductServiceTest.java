package com.matheusgoes23.productapi.service;

import com.matheusgoes23.productapi.exception.ProductNoContentException;
import com.matheusgoes23.productapi.exception.ProductNotFoundException;
import com.matheusgoes23.productapi.model.Product;
import com.matheusgoes23.productapi.repository.ProductRepository;
import com.matheusgoes23.productapi.service.serviceImpl.ProductServiceImpl;
import com.matheusgoes23.productapi.util.ProductCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Product Service")
class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepositoryMock;

    @BeforeEach
    void setUp() {

        PageImpl<Product> productPage = new PageImpl<>(List.of(ProductCreator.createValidProduct()));

        BDDMockito.when(productRepositoryMock.findAll(ArgumentMatchers.any(Pageable.class)))
                .thenReturn(productPage);

        BDDMockito.when(productRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(ProductCreator.createValidProduct()));

        BDDMockito.when(productRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(ProductCreator.createValidProduct()));

        BDDMockito.when(productRepositoryMock.save(ArgumentMatchers.any(Product.class)))
                .thenReturn(ProductCreator.createValidProduct());

        BDDMockito.doNothing().when(productRepositoryMock).delete(ArgumentMatchers.any(Product.class));
    }

    @Test
    @DisplayName("findAll returns list of product inside page object when successful")
    void findAll_ReturnsListOfProductInsidePageObject_WhenSuccessful() {

        Product expectedProductList = ProductCreator.createValidProduct();

        Page<Product> productPage = productService.findAll(PageRequest.of(1, 1));

        Assertions.assertThat(productPage).isNotNull();

        Assertions.assertThat(productPage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(productPage.toList().get(0))
                .isEqualTo(expectedProductList);
    }

    @Test
    @DisplayName("findAll returns empty list of product when product list is not found")
    void findAll_ReturnsEmptyListOfProduct_WhenProductListIsNotFound() {

        BDDMockito.when(productRepositoryMock.findAll(PageRequest.of(1, 1)))
                .thenReturn(Page.empty());

        Assertions.assertThatThrownBy(() -> this.productService.findAll(PageRequest.of(1, 1)))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("findById returns a product when successful")
    void findById_ReturnsAProduct_WhenSuccessful() {

        Long expectedId = ProductCreator.createValidProduct().getId();

        Product product = productService.findById(1L);

        Assertions.assertThat(product).isNotNull();

        Assertions.assertThat(product.getId())
                .isNotNull()
                .isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findById throws ProductNotFoundException when product is not found")
    void findById_ThrowsProductNotFoundException_WhenProductIsNotFound() {

        BDDMockito.when(productRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> this.productService.findById(1L))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("findByName returns list of product when successful")
    void findByName_ReturnsListOfProduct_WhenSuccessful() {

        String expectedName = ProductCreator.createValidProduct().getName();

        List<Product> productList = productService.findByName("Notebook");

        Assertions.assertThat(productList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(productList.get(0).getName())
                .isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns empty list of product when product is not found")
    void findByName_ReturnsEmptyListOfProduct_WhenProductIsNotFound() {

        BDDMockito.when(productRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        Assertions.assertThatThrownBy(() -> this.productService.findByName("Notebook"))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("save returns a product when successful")
    void save_ReturnsAProduct_WhenSuccessful() {

        Product expectedProduct = ProductCreator.createValidProduct();

        Product product = productService.save(Product.builder().build());

        Assertions.assertThat(product)
                .isNotNull()
                .isEqualTo(expectedProduct);
    }

    @Test
    @DisplayName("update returns a product when successful")
    void update_ReturnsAProduct_WhenSuccessful() {

        Product expectedProduct = ProductCreator.createValidProduct();

        Product product = productService.update(1L, Product.builder().build());

        Assertions.assertThat(product)
                .isNotNull()
                .isEqualTo(expectedProduct);
    }

    @Test
    @DisplayName("update throws ProductNotFoundException when product is not found")
    void update_ThrowsProductNotFoundException_WhenProductIsNotFound() {

        BDDMockito.when(productRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> this.productService.update(1L, Product.builder().build()))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("delete removes product when successful")
    void delete_RemovesProduct_WhenSuccessful() {

        Assertions.assertThatCode(() -> productService.delete(1L))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("delete throws ProductNoContentException when product is not found")
    void delete_ThrowsProductNoContentException_WhenProductIsNotFound() {

        BDDMockito.when(productRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> this.productService.delete(1L))
                .isInstanceOf(ProductNoContentException.class);
    }
}