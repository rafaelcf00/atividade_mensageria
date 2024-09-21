package com.mensageria.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.time.LocalDateTime;

@Entity
@Table(name = "skus")
public class SkuEntity {

    @Id
    private String id;
    
    @Column(nullable = false)
    private Double value;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


    public SkuEntity() {}

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
   
   public Double getValue() {
	   return value;
   }
   
   public void setValue(Double value) {
	   this.value = value;
   }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
    	this.createdAt = createdAt;
    }
    
}
