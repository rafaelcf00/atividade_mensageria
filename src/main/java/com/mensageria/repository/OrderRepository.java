package com.mensageria.repository;

import com.mensageria.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {
    
	Optional<OrderEntity> findById(String id);
	
	  List<OrderEntity> findByCustomer_Id(Long customerId); 
	  
	  @Query("SELECT DISTINCT o FROM OrderEntity o JOIN o.items i WHERE i.sku.id = :skuId")
	    List<OrderEntity> findBySkuId(@Param("skuId") String skuId);
}
