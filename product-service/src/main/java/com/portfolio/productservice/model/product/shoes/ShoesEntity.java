package com.portfolio.productservice.model.product.shoes;

import com.portfolio.productservice.model.product.ProductEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clothes")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShoesEntity extends ProductEntity {
    @Enumerated(EnumType.STRING)
    private ShoesType shoesType;
}
