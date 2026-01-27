package com.amsmanagament.system.serviceImpl;

import com.amsmanagament.system.model.Inventory;
import com.amsmanagament.system.services.InventoryService;

public class InventoryServiceImpl implements InventoryService {
    @Override
    public boolean isProductInStock(Long productId, int requiredQuantity) {
        return false;
    }

    @Override
    public Inventory getInventoryById(Long inventoryId) {
        return null;
    }

    @Override
    public Inventory getInventoryByProductId(Long productId) {
        return null;
    }

    @Override
    public Inventory createInventory(Inventory inventory, Long productId, Long sellerId) {
        return null;
    }

    @Override
    public Inventory updateInventory(Long inventoryId, Inventory inventory) {
        return null;
    }

    @Override
    public void increaseStock(Long productId, int quantity) {

    }

    @Override
    public void reduceStock(Long productId, int quantity) {

    }

    @Override
    public void deleteInventory(Long inventoryId) {

    }
}
