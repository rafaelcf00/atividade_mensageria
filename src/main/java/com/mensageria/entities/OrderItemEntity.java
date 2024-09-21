package com.mensageria.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "order_items")
public class OrderItemEntity {

    @Id
    private Integer id;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private OrderEntity order;
    
    @ManyToOne
    @JoinColumn(name = "sku_id", nullable = false)
    private SkuEntity sku;

    @Column(name = "category_id", nullable = true)
    private String categoryId;
    
    @Column(name = "sub_category_id", nullable = true)
    private String subCategoryId;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public OrderItemEntity() {}

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OrderEntity getOrder() {
        return order;
    }
    
    public void setOrder(OrderEntity order) {
        this.order = order;
    }
    
    public SkuEntity getSku() {
        return sku;
    }

    public void setSku(SkuEntity sku) {
        this.sku = sku;
    }
    
    public String getCategoryId() {
    	return categoryId;
    }
    
    public void setCategoryId(String categoryId) {
    	this.categoryId = categoryId;
    }
    
    public String getSubCategoryId() {
    	return subCategoryId;
    }
    
    public void setSubCategoryId(String subCategoryId) {
    	this.subCategoryId = subCategoryId;
    }
    
    @JsonIgnore
    public Double getTotal() {
        return sku != null ? sku.getValue() * this.quantity : 0.0;
    }
}
