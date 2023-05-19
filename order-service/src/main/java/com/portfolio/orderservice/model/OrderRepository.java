package com.portfolio.orderservice.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Iterable<OrderEntity> findByUserId(String userId);
}
