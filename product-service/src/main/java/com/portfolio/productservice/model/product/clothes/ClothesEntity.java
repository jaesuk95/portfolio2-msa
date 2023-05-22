package com.portfolio.productservice.model.product.clothes;

import com.portfolio.productservice.model.product.ProductEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clothes")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClothesEntity extends ProductEntity {

    @Enumerated(EnumType.STRING)
    private ClothesType clothesType;

    @Enumerated(EnumType.STRING)
    private LengthType lengthType;

}
