package com.matheusgoes23.productapi.util;

import com.matheusgoes23.productapi.model.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProductCreator {

    public static Product createProductToBeSalved() {
        return Product.builder()
                .name("Notebook")
                .value(BigDecimal.valueOf(3000))
                .description("Acer Aspire")
                .expirationDate(LocalDate.parse("02-05-2026", DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .build();
    }

    public static Product createValidProduct() {
        return Product.builder()
                .id(1L)
                .name("Notebook")
                .value(BigDecimal.valueOf(3000))
                .description("Acer Aspire")
                .expirationDate(LocalDate.parse("02-05-2026", DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .build();
    }

    public static Product createValidUpdatedProduct() {
        return Product.builder()
                .id(1L)
                .name("Notebook")
                .value(BigDecimal.valueOf(3000))
                .description("Acer Aspire")
                .expirationDate(LocalDate.parse("02-05-2026", DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .build();
    }
}
