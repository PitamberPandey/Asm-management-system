package com.amsmanagament.system.serviceImpl;

import com.amsmanagament.system.exception.ResourceNotFoundException;
import com.amsmanagament.system.model.Farmer;
import com.amsmanagament.system.model.Inventory;
import com.amsmanagament.system.model.Product;
import com.amsmanagament.system.model.Seller;
import com.amsmanagament.system.repo.FarmerRepo;
import com.amsmanagament.system.repo.InventoryRepo;
import com.amsmanagament.system.repo.ProductRepo;
import com.amsmanagament.system.repo.SellerRepo;
import com.amsmanagament.system.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepo inventoryRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private SellerRepo sellerRepo;

    @Autowired
    FarmerRepo farmerRepo;

    // ---------------- CHECK STOCK ----------------

    @Override
    public boolean isProductInStock(Long productId, int requiredQuantity) {

        Inventory inventory = inventoryRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found for product id " + productId));

        return inventory.getQuantity() >= requiredQuantity;
    }

    // ---------------- GET INVENTORY ----------------

    @Override
    public Inventory getInventoryById(Long inventoryId) {
        return inventoryRepo.findById(inventoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));
    }

    @Override
    public Inventory getInventoryByProductId(Long productId) {
        Inventory inventory = inventoryRepo.findByProduct_Id(productId);

        if (inventory == null) {
            throw new ResourceNotFoundException("Inventory not found for product");
        }

        return inventory;
    }

    @Override
    public List<Inventory> searchInventoryByProductName(String keyword) {
        return inventoryRepo.searchByProductName(keyword);
    }

    @Override
    public List<Inventory> getAllInventor() {
        return inventoryRepo.findAll();
    }

    // ---------------- CREATE INVENTORY ----------------

    @Override
    public Inventory createInventory(Long productId, Long farmerId, int quantity, double price) {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Farmer farmer = farmerRepo.findById(farmerId)
                .orElseThrow(() -> new ResourceNotFoundException("Farmer not found"));

        Inventory newInventory = new Inventory();
        newInventory.setProduct(product);
//        newInventory.setFarmer(farmer);
        newInventory.setQuantity(quantity);
        newInventory.setPrice(price);
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

    @Override
    public List<Inventory> getAll() {

        return inventoryRepo.findAll();
    }

    // ---------------- STOCK MANAGEMENT ----------------

    @Transactional
    @Override
    public void increaseStock(Long InventoryId, int quantity) {

        Optional<Inventory> inventory = inventoryRepo.findById(InventoryId);

        if (inventory.isEmpty()) {
            throw new ResourceNotFoundException("Inventory not found");
        }

        inventory.get().setQuantity(inventory.get().getQuantity() + quantity);
        inventory.get().setUpdatedAt(LocalDateTime.now());
    }

    @Transactional
    @Override
    public void reduceStock(Long productId, int quantity) {

       Optional<Inventory> inventory = inventoryRepo.findById(productId);

        if (inventory == null) {
            throw new ResourceNotFoundException("Inventory not found for product");
        }

        if (inventory.get().getQuantity()< quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        inventory.get().setQuantity(inventory.get().getQuantity() - quantity);
    }

    // ---------------- DELETE INVENTORY ----------------

    @Override
    public void deleteInventory(Long inventoryId) {

        Inventory inventory = inventoryRepo.findById(inventoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));

        inventoryRepo.delete(inventory);
    }

    @Override
    public Long getTotalInventoryByFarmer(Long farmerId) {

        return inventoryRepo.getTotalInventoryCountByUser(farmerId);
    }

    @Override
    public List<Inventory> getInventoriesByFarmer(Long farmerId) {
        return inventoryRepo.getInventoriesByFarmer(farmerId);
    }
}
