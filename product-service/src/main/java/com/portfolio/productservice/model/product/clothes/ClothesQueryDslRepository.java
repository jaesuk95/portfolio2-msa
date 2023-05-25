package com.portfolio.productservice.model.product.clothes;

import com.portfolio.productservice.model.product.clothes.dto.ProductClothesDto;
import com.portfolio.productservice.querydsl.Querydsl4RepositorySupport;
import com.querydsl.core.types.Projections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


@Repository
public class ClothesQueryDslRepository extends Querydsl4RepositorySupport {

    public ClothesQueryDslRepository() {
        super(Clothes.class);
    }

    public Page<ProductClothesDto> getClothes(Pageable pageable) {
        return applyPagination(pageable, contentQuery -> contentQuery
                .select(Projections.bean(ProductClothesDto.class,
                        QClothes.clothes.id,
//                        QClothes.clothes.stock,
//                        QClothes.clothes.registeredDate,
                        QClothes.clothes.lengthType,
                        QClothes.clothes.clothesType
//                        QClothes.clothes.price,
//                        QClothes.clothes.companyName,
//                        QClothes.clothes.productId
                ))
                .from(QClothes.clothes)
                .where()
        );
    }
}
