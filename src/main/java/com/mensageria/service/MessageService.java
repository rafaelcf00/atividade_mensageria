package com.mensageria.service;

import com.mensageria.entities.*;
import com.mensageria.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final SkuRepository skuRepository;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public MessageService(
       CustomerRepository customerRepository,
       OrderRepository orderRepository,
       SkuRepository skuRepository,
       OrderItemRepository orderItemRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.skuRepository = skuRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public ResponseEntity<String> processMessage(Map<String, Object> payload) {
        try {

            Map<String, Object> customerData = (Map<String, Object>) payload.get("customer");
            Long customerId = Long.valueOf(String.valueOf(customerData.get("id")));
            String customerName = (String) customerData.get("name");
            CustomerEntity customer = customerRepository.findById(customerId)
                    .orElseGet(() -> {
                        CustomerEntity newCustomer = new CustomerEntity();
                        newCustomer.setId(customerId);
                        newCustomer.setName(customerName);
                        return customerRepository.save(newCustomer);
                    });

            String orderId = (String) payload.get("uuid");
            
            OrderEntity order = orderRepository.findById(orderId)
                    .orElseGet(() -> {
                    	OrderEntity newOrder = new OrderEntity();
                    	newOrder.setId(orderId);
                    	newOrder.setType((String) payload.get("type"));
                    	newOrder.setCustomer(customer);
                        return orderRepository.save(newOrder);
                    });
            

            String createdAtStr = (String) payload.get("created_at");
            LocalDateTime createdAt = LocalDateTime.parse(createdAtStr.replace(" ", "T"));
            order.setCreatedAt(createdAt);
            orderRepository.save(order);

            List<Map<String, Object>> items = (List<Map<String, Object>>) payload.get("items");
            for (Map<String, Object> itemData : items) {
                Map<String, Object> skuData = (Map<String, Object>) itemData.get("sku");
                String skuId = (String) skuData.get("id");
                Double skuValue = Double.valueOf(String.valueOf(skuData.get("value")));
                Integer quantity = (Integer) itemData.get("quantity");
                Map<String, Object> categoryData = (Map<String, Object>) itemData.get("category");
                String categoryId = (String) categoryData.get("id");
                Map<String, Object> subCategoryData = (Map<String, Object>) categoryData.get("sub_category");
                String subCategoryId = (String) subCategoryData.get("id");
                Integer orderItemId = (Integer) itemData.get("id");
                
  
                SkuEntity sku = skuRepository.findById(skuId)
                        .orElseGet(() -> {
                            SkuEntity newSku = new SkuEntity();
                            newSku.setId(skuId);
                            newSku.setValue(skuValue);
                            return skuRepository.save(newSku);
                        });
                
                
                OrderItemEntity orderItem = new OrderItemEntity();
                orderItem.setId(orderItemId);
                orderItem.setSku(sku);
                orderItem.setOrder(order);
                orderItem.setCategoryId(categoryId);
                orderItem.setSubCategoryId(subCategoryId);
                orderItem.setQuantity(quantity);
                orderItemRepository.save(orderItem);
                
            };
            return ResponseEntity.ok("MENSAGEM RECEBIDA COM SUCESSO");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao processar mensagem: " + e.getMessage());
        }
    }
} 