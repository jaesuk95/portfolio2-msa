package com.portfolio.productservice.model.product.shoes;

import com.portfolio.productservice.model.product.ProductEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "shoes")
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@PrimaryKeyJoinColumn(name = "ID")      // product 의 아이디를 참조한다.
//@DiscriminatorValue("PRODUCT_SHOES")
//public class Shoes extends ProductEntity {
public class Shoes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ShoesType shoesType;
}
