package com.amsmanagament.system.serviceImpl;

import com.amsmanagament.system.model.Inventory;
import com.amsmanagament.system.model.Product;
import com.amsmanagament.system.model.Seller;
import com.amsmanagament.system.repo.InventoryRepo;
import com.amsmanagament.system.repo.ProductRepo;
import com.amsmanagament.system.repo.SellerRepo;
import com.amsmanagament.system.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepo inventoryRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private SellerRepo sellerRepo;

    // ---------------- CHECK STOCK ----------------

    @Override
    public boolean isProductInStock(Long productId, int requiredQuantity) {
        Inventory inventory = inventoryRepo.findByProduct_Id(productId);
        return inventory != null && inventory.getQuantity() >= requiredQuantity;
    }

    // ---------------- GET INVENTORY ----------------

    @Override
    public Inventory getInventoryById(Long inventoryId) {
        return inventoryRepo.findById(inventoryId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
    }

    @Override
    public Inventory getInventoryByProductId(Long productId) {
        Inventory inventory = inventoryRepo.findByProduct_Id(productId);

        if (inventory == null) {
            throw new RuntimeException("Inventory not found for product");
        }

        return inventory;
    }

    // ---------------- CREATE INVENTORY ----------------

    @Override
    public Inventory createInventory(Inventory inventory, Long productId, Long sellerId) {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Seller seller = sellerRepo.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        Inventory newInventory = new Inventory();
        newInventory.setProduct(product);
        newInventory.setSeller(seller);
        newInventory.setQuantity(inventory.getQuantity());
        newInventory.setPrice(inventory.getPrice());
        newInventory.setCreatedAt(LocalDateTime.now());
        newInventory.setUpdatedAt(LocalDateTime.now());

        return inventoryRepo.save(newInventory);
    }

    // ---------------- UPDATE INVENTORY ----------------

    @Override
    public Inventory updateInventory(Long inventoryId, Inventory inventory) {

        Inventory existingInventory = inventoryRepo.findById(inventoryId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        existingInventory.setQuantity(inventory.getQuantity());
        existingInventory.setPrice(inventory.getPrice());
        existingInventory.setUpdatedAt(LocalDateTime.now());

        return inventoryRepo.save(existingInventory);
    }

    // ---------------- STOCK MANAGEMENT ----------------

    @Transactional
    @Override
    public void increaseStock(Long productId, int quantity) {

        Inventory inventory = inventoryRepo.findByProduct_Id(productId);

        if (inventory == null) {
            throw new RuntimeException("Inventory not found for product");
        }

        inventory.setQuantity(inventory.getQuantity() + quantity);
        inventory.setUpdatedAt(LocalDateTime.now());
    }

    @Transactional
    @Override
    public void reduceStock(Long productId, int quantity) {

        Inventory inventory = inventoryRepo.findByProduct_Id(productId);

        if (inventory == null) {
            throw new RuntimeException("Inventory not found for product");
        }

        if (inventory.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        inventory.setQuantity(inventory.getQuantity() - quantity);
        inventory.setUpdatedAt(LocalDateTime.now());
    }

    // ---------------- DELETE INVENTORY ----------------

    @Override
    public void deleteInventory(Long inventoryId) {

        Inventory inventory = inventoryRepo.findById(inventoryId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        inventoryRepo.delete(inventory);
    }
}
