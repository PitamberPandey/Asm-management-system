package com.amsmanagament.system.services;

import com.amsmanagament.system.model.Inventory;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public interface InventoryService {

    // Check stock availability
    boolean isProductInStock(Long productId, int requiredQuantity);

    // Get inventory by inventory ID
    Inventory getInventoryById(Long inventoryId);

    // Get inventory by product ID
    Inventory getInventoryByProductId(Long productId);
    List<Inventory> searchInventoryByProductName(String keyword);

    List<Inventory> getAllInventor();

    // Create inventory for a product
    Inventory createInventory(Inventory inventory, Long productId, Long sellerId);

    // Update inventory details (price, location, etc.)
    Inventory updateInventory(Long inventoryId, Inventory inventory);
     List<Inventory> getAll();

    // Increase stock
    void increaseStock(Long InventoryId, int quantity);

    // Reduce stock
    void reduceStock(Long productId, int quantity);

    // Delete inventory
    void deleteInventory(Long inventoryId);
}
