package com.portfolio.productservice.model.product.clothes;

import com.portfolio.productservice.model.product.ProductEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@PrimaryKeyJoinColumn(name = "ID")
@DiscriminatorValue("PRODUCT_CLOTHES")
public class Clothes extends ProductEntity {

    @Enumerated(EnumType.STRING)
    private ClothesType clothesType;

    @Enumerated(EnumType.STRING)
    private LengthType lengthType;

    // Define the foreign key relationship with the product table
    @OneToOne
    @JoinColumn(name = "PRODUCT_ENTITY_ID")
    private ProductEntity product;


    public Clothes(int stock, LocalDateTime registeredDate, ClothesType clothesType, LengthType lengthType, int price, String userId, String companyName, String productId) {
        super(stock, registeredDate, price, userId, companyName, productId);
        this.clothesType = clothesType;
        this.lengthType = lengthType;
    }

}
