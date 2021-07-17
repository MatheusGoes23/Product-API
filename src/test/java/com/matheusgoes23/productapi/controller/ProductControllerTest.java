package com.matheusgoes23.productapi.controller;

import com.matheusgoes23.productapi.model.Product;
import com.matheusgoes23.productapi.service.ProductService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Product Controller")
class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productServiceMock;

    @BeforeEach
    void setUp() {

        PageImpl<Product> productPage = new PageImpl<>(List.of(ProductCreator.createValidProduct()));

        BDDMockito.when(productServiceMock.findAll(ArgumentMatchers.any()))
                .thenReturn(productPage);

        BDDMockito.when(productServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(ProductCreator.createValidProduct());

        BDDMockito.when(productServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(ProductCreator.createValidProduct()));

        BDDMockito.when(productServiceMock.save(ArgumentMatchers.any(Product.class)))
                .thenReturn(ProductCreator.createValidProduct());

        BDDMockito.when(productServiceMock.update(ArgumentMatchers.anyLong(), ArgumentMatchers.any(Product.class)))
                .thenReturn(ProductCreator.createValidProduct());

        BDDMockito.doNothing().when(productServiceMock).delete(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("getAllProducts returns list of product inside page object when successful")
    void getAllProducts_ReturnsListOfProductInsidePageObject_WhenSuccessful() {

        Product expectedProductList = ProductCreator.createValidProduct();

        Page<Product> productPage = productController.getAllProducts(null).getBody();

        Assertions.assertThat(productPage).isNotNull();

        Assertions.assertThat(productPage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(productPage.toList().get(0))
                .isEqualTo(expectedProductList);
    }

    @Test
    @DisplayName("getProductById returns a product when successful")
    void getProductById_ReturnsAProduct_WhenSuccessful() {

        Long expectedId = ProductCreator.createValidProduct().getId();

        Product product = productController.getProductById(1L).getBody();

        Assertions.assertThat(product).isNotNull();

        Assertions.assertThat(product.getId())
                .isNotNull()
                .isEqualTo(expectedId);
    }

    @Test
    @DisplayName("getProductByName returns list of product when successful")
    void getProductByName_ReturnsListOfProduct_WhenSuccessful() {

        String expectedName = ProductCreator.createValidProduct().getName();

        List<Product> productList = productController.getProductByName("Notebook").getBody();

        Assertions.assertThat(productList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(productList.get(0).getName())
                .isEqualTo(expectedName);
    }

    @Test
    @DisplayName("getProductByName returns empty list of product when product is not found")
    void getProductByName_ReturnsEmptyListOfProduct_WhenProductIsNotFound() {

        BDDMockito.when(productServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Product> productList = productController.getProductByName("Notebook").getBody();

        Assertions.assertThat(productList)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("saveProduct returns a product when successful")
    void saveProduct_ReturnsAProduct_WhenSuccessful() {

        Product expectedProduct = ProductCreator.createValidProduct();

        Product product = productController.saveProduct(Product.builder().build()).getBody();

        Assertions.assertThat(product)
                .isNotNull()
                .isEqualTo(expectedProduct);
    }

    @Test
    @DisplayName("updateProduct returns a product when successful")
    void updateProduct_ReturnsAProduct_WhenSuccessful() {

        Product expectedProduct = ProductCreator.createValidProduct();

        Product product = productController.updateProduct(1L, Product.builder().build()).getBody();

        Assertions.assertThat(product)
                .isNotNull()
                .isEqualTo(expectedProduct);
    }

    @Test
    @DisplayName("deleteProduct removes product when successful")
    void deleteProduct_RemovesProduct_WhenSuccessful() {

        Assertions.assertThatCode(() -> productController.deleteProduct(1L))
                .doesNotThrowAnyException();

        ResponseEntity<?> entity = productController.deleteProduct(1L);

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}