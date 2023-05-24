package com.portfolio.productservice.model.product;

import com.portfolio.productservice.model.product.clothes.Clothes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "product")
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TYPE", discriminatorType = DiscriminatorType.STRING)
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int stock;
//    private LocalDateTime registeredDate;
    private int price;
    private String userId;
    private String companyName;
    private String productId;

    public void purchaseUpdate(int stock) {
        this.stock -= stock;
    }

    public ProductEntity(int stock, LocalDateTime registeredDate, int price, String userId, String companyName, String productId) {
        this.stock = stock;
//        this.registeredDate = registeredDate;
        this.price = price;
        this.userId = userId;
        this.companyName = companyName;
        this.productId = productId;
    }
}
