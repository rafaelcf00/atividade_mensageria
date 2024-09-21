package com.mensageria.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    private String id;
    
    @Column(nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;
    
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderItemEntity> items;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public OrderEntity() {}

   @PrePersist
   public void prePersist() {
       if (createdAt == null) {
           createdAt = LocalDateTime.now();
       }
   }
   
   public String getId() {
	   return id;
   }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public String getType() {
    	return type;
    }
    
    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }
    
    public CustomerEntity getCustomer() {
        return customer;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
    	this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setItems(List<OrderItemEntity> items) {
        this.items = items;
    }
    
    public List<OrderItemEntity> getItems() {
        return items;
    }
    
    public Double getTotal() {
        if (items != null && !items.isEmpty()) {
            return items.stream()
                        .mapToDouble(OrderItemEntity::getTotal) 
                        .sum();
        }
        return 0.0;
    }
}
