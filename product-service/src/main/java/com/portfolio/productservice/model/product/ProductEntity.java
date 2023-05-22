package com.portfolio.productservice.model.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TYPE", discriminatorType = DiscriminatorType.STRING)
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brandName;
    private String companyRegistered;
    private int stock;
    private LocalDateTime registeredDate;
    private int price;

    public ProductEntity(String brandName, String companyRegistered, int stock, LocalDateTime registeredDate, int price) {
        this.brandName = brandName;
        this.companyRegistered = companyRegistered;
        this.stock = stock;
        this.registeredDate = registeredDate;
        this.price = price;
    }
}
