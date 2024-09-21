package com.mensageria.controllers;

import com.mensageria.entities.OrderEntity;
import com.mensageria.repository.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/orders")
public class Order {

    private final OrderRepository orderRepository;

    public Order(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public List<OrderEntity> getOrders(
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) String skuId) {

        if (customerId != null) {
            return orderRepository.findByCustomer_Id(customerId);
        } 
        if (skuId != null) {
            return orderRepository.findBySkuId(skuId).stream()
                    .peek(order -> order.setItems(
                        order.getItems().stream()
                             .filter(item -> item.getSku().getId().equals(skuId))
                             .collect(Collectors.toList())
                    )).collect(Collectors.toList());
        } 
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderEntity> getOrderById(@PathVariable String id) {
        return orderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}

