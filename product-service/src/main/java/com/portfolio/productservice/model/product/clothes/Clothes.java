package com.portfolio.productservice.model.product.clothes;

import com.portfolio.productservice.model.product.ProductEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
//@Table(name = "clothes")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "ID")      // product 의 아이디를 참조한다.
@DiscriminatorValue("PRODUCT_CLOTHES")
public class Clothes extends ProductEntity {

    @Enumerated(EnumType.STRING)
    private ClothesType clothesType;

    @Enumerated(EnumType.STRING)
    private LengthType lengthType;

    public Clothes(int stock, LocalDateTime registeredDate, ClothesType clothesType, LengthType lengthType, int price, String userId, String companyName) {
        super(stock, registeredDate, price, userId, companyName);
        this.clothesType = clothesType;
        this.lengthType = lengthType;
    }

}
