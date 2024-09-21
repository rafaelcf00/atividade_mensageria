package com.mensageria.repository;

import com.mensageria.entities.OrderItemEntity;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, UUID> {
    
}
