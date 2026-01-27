package com.amsmanagament.system.services;

import com.amsmanagament.system.model.Inventory;

public interface InventoryService {

    // Check stock availability
    boolean isProductInStock(Long productId, int requiredQuantity);

    // Get inventory by inventory ID
    Inventory getInventoryById(Long inventoryId);

    // Get inventory by product ID
    Inventory getInventoryByProductId(Long productId);

    // Create inventory for a product
    Inventory createInventory(Inventory inventory, Long productId, Long sellerId);

    // Update inventory details (price, location, etc.)
    Inventory updateInventory(Long inventoryId, Inventory inventory);

    // Increase stock
    void increaseStock(Long productId, int quantity);

    // Reduce stock
    void reduceStock(Long productId, int quantity);

    // Delete inventory
    void deleteInventory(Long inventoryId);
}
