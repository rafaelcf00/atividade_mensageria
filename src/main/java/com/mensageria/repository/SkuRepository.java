package com.mensageria.repository;

import com.mensageria.entities.SkuEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkuRepository extends JpaRepository<SkuEntity, String> {
    
}
