package com.matheusgoes23.productapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "tb_product")
public class Product extends RepresentationModel<Product> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    private String name;

    @PositiveOrZero
    private BigDecimal value;

    @NotBlank
    @Lob
    private String description;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate expirationDate;
}
