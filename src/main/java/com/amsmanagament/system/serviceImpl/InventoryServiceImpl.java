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

import java.time.LocalDateTime;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    InventoryRepo inventoryRepo;

    @Autowired
    ProductRepo  productRepo;

    @Autowired
    SellerRepo sellerRepo;


    @Override
    public boolean isProductInStock(Long productId, int requiredQuantity) {
        Product product= productRepo.findById(productId).orElseThrow(()->new RuntimeException("Product not found"));

        Inventory inventory= inventoryRepo.findByProduct(product.getId());
        if(inventory!=null && inventory.getQuantity()>=requiredQuantity){

            return true;
        }

        return false;
    }

    @Override
    public Inventory getInventoryById(Long inventoryId) {
        Inventory inventory= inventoryRepo.findById(inventoryId).orElseThrow(()->new RuntimeException("Inventory not found"));

        return inventory;
    }

    @Override
    public Inventory getInventoryByProductId(Long productId) {
        Product product= productRepo.findById(productId).orElseThrow(()->new RuntimeException("Product not found"));
        Inventory inventory= inventoryRepo.findByProduct(product.getId());

        return inventory;
    }

    @Override
    public Inventory createInventory(Inventory inventory, Long productId, Long sellerId) {
        Product product= productRepo.findById(productId).orElseThrow(()->new RuntimeException("Product not found"));
        Seller seller= sellerRepo.findById(sellerId).orElseThrow(()->new RuntimeException("Seller not found"));
        Inventory inventory1=new Inventory();
        inventory1.setProduct(product);
        inventory1.setPrice(inventory.getPrice());
        inventory1.setCreatedAt(LocalDateTime.now());
        inventory1.setUpdatedAt(LocalDateTime.now());
        inventory1.setSeller(seller);
        inventory1.setQuantity(inventory.getQuantity());


        return  inventoryRepo.save(inventory1);
    }

    @Override
    public Inventory updateInventory(Long inventoryId, Inventory inventory) {
        Inventory inventory1= inventoryRepo.findById(inventoryId).orElseThrow(()->new RuntimeException("Inventory not found"));
        inventory1.setQuantity(inventory.getQuantity());
        inventory1.setPrice(inventory.getPrice());
        inventory1.setUpdatedAt(LocalDateTime.now());
        return inventoryRepo.save(inventory1);

    }

    @Override
    public void increaseStock(Long productId, int quantity) {
        Product product= productRepo.findById(productId).orElseThrow(()->new RuntimeException("Product not found"));
        Inventory inventory= inventoryRepo.findByProduct(product.getId());
        inventory.setQuantity(inventory.getQuantity()+quantity);
        inventoryRepo.save(inventory);

    }

    @Override
    public void reduceStock(Long productId, int quantity) {
        Product product= productRepo.findById(productId).orElseThrow(()->new RuntimeException("Product not found"));
        Inventory inventory= inventoryRepo.findByProduct(product.getId());
        inventory.setQuantity(inventory.getQuantity()-quantity);
        inventoryRepo.save(inventory);

    }

    @Override
    public void deleteInventory(Long inventoryId) {
        Product product= productRepo.findById(inventoryId).orElseThrow(()->new RuntimeException("Product not found"));
        Inventory inventory= inventoryRepo.findByProduct(product.getId());
        inventoryRepo.delete(inventory);

    }
}
