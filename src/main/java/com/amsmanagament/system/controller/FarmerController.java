package com.amsmanagament.system.controller;


import com.amsmanagament.system.Response.*;


import com.amsmanagament.system.model.*;

import com.amsmanagament.system.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/farmer")
public class FarmerController {

    @Autowired
    Farmerservice farmerservice;

    @Autowired
    ProductService productService;


    @Autowired
    SellerService sellerService;

    @Autowired
    DeliveryService deliveryService;

    @Autowired
    InventoryService inventoryService;

    @GetMapping("/user/{id}")
    public ResponseEntity<Farmer> getUserById(@PathVariable Long id) throws Exception {
        Farmer user = farmerservice.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Farmer>> getUsersByUsername(@RequestParam String username) throws Exception {
        return ResponseEntity.ok(farmerservice.findByUsername(username));
    }

    @PostMapping("/create/farmer")
    public ResponseEntity<ApiCreateResponse> createFarmer(@RequestBody Farmer farmer) {
        try {
            Farmer createdFarmer = farmerservice.createFarmer(farmer);
            ApiCreateResponse response = new ApiCreateResponse("Farmer created successfully", true, createdFarmer);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiCreateResponse response = new ApiCreateResponse("Failed to create farmer: " + e.getMessage(), false, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @PutMapping("/update/farmer")
    public ResponseEntity<ApiCreateResponse> updatedFarmers(@RequestBody Farmer farmer) {
        try {
            // Call the update method in your service
            Farmer updatedFarmer = farmerservice.updateFarmer(farmer);

            ApiCreateResponse response = new ApiCreateResponse(
                    "Farmer updated successfully",
                    true,
                    updatedFarmer
            );
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ApiCreateResponse response = new ApiCreateResponse(
                    "Failed to update farmer: " + e.getMessage(),
                    false,
                    null // use null because update failed
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/create/product")
    public ResponseEntity<ProductResponse> createproduct( @RequestBody  Product product) {
        try {
            Product createdProduct = productService.createProduct(product);
            ProductResponse response = new ProductResponse("Product created successfully", true, createdProduct);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ProductResponse response = new ProductResponse("Product created failed"+e.getMessage(), false, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/update/product/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable("id") Long productId,
            @RequestBody Product product
    ) {
        try {
            // set id to product to ensure correct update
          product.getId();
            Product updatedProduct = productService.updateProduct( productId, product);

            ProductResponse response = new ProductResponse(
                    "Product updated successfully",
                    true,
                    updatedProduct
            );
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ProductResponse response = new ProductResponse(
                    "Failed to update product: " + e.getMessage(),
                    false,
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
     @DeleteMapping("/delete/product/{id}")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable ("id") Long id) {
         try {
              productService.deleteProduct(id);
             ProductResponse response = new ProductResponse("Product deleted successfully", true, null);
             return ResponseEntity.ok(response);
         } catch (Exception e) {
             ProductResponse response = new ProductResponse("Failed to delete product: " + e.getMessage(), false, null);
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
         }
     }

    @GetMapping("/get/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) throws Exception {

        Product product = productService.getProductById(id);

        return ResponseEntity.ok(product);
    }

    @PostMapping("/create/seller")
    public ResponseEntity<SellerResponse> createSeller(@RequestBody Seller seller) throws Exception {
        try {
            Seller seller1 = sellerService.createSeller(seller);
            SellerResponse sellerResponse=new SellerResponse("seller create successful",true,seller1);
            return ResponseEntity.ok(sellerResponse);
        } catch (Exception e) {
            SellerResponse sellerResponse=new SellerResponse(" failed  create seller",false,null);
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(sellerResponse);
        }


    }
    @PutMapping("/update/seller")
    public ResponseEntity<SellerResponse> UpdateSeller(@RequestBody Seller seller) throws Exception {
        try {
            Seller seller1 = sellerService.updateSeller(seller);
            SellerResponse sellerResponse = new SellerResponse("seller update successful", true, seller1);
            return ResponseEntity.ok(sellerResponse);
        } catch (Exception e) {
            SellerResponse sellerResponse = new SellerResponse(" failed  for update seller", false, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(sellerResponse);
        }
    }
    @PostMapping("/create/delivery")
    public ResponseEntity<ApiDeliveryResponse> createDelivery(@RequestBody Delivery delivery) throws  Exception {
        try {
            Delivery delivery1 = deliveryService.createDelivery(delivery, delivery.getOrder().getId());
            ApiDeliveryResponse response = new ApiDeliveryResponse("Delivery created successfully", true, delivery1);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiDeliveryResponse response = new ApiDeliveryResponse("Failed to create delivery: " + e.getMessage(), false, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }


    }

    @PutMapping("/update/delivery/{deliveryid}")
    public ResponseEntity<ApiDeliveryResponse> updateDelivery(@RequestBody Delivery delivery,@PathVariable("deliveryid") Long deliveryid) throws  Exception {
        try {
            Delivery delivery1 = deliveryService.updateDelivery(deliveryid, delivery);
            ApiDeliveryResponse response = new ApiDeliveryResponse("Delivery updated successfully", true, delivery1);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiDeliveryResponse response = new ApiDeliveryResponse("Failed to update delivery: " + e.getMessage(), false, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/delete/delivery/{id}")
    public ResponseEntity<ApiDeliveryResponse> deleteDelivery(@PathVariable ("id") Long id) {
        try {
            deliveryService.deleteDelivery(id);
            ApiDeliveryResponse response = new ApiDeliveryResponse("Delivery deleted successfully", true, null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiDeliveryResponse response = new ApiDeliveryResponse("Failed to delete delivery: " + e.getMessage(), false, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/delivery/{id}")
    public ResponseEntity<ApiDeliveryResponse> verifyDelivery(@PathVariable("id") Long deliveryId) {
        try {
            Delivery verifiedDelivery = deliveryService.verifyDelivery(deliveryId);
            ApiDeliveryResponse response = new ApiDeliveryResponse("Delivery verified successfully", true, verifiedDelivery);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiDeliveryResponse response = new ApiDeliveryResponse("Failed to verify delivery: " + e.getMessage(), false, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/get/delivery/{id}")
    public ResponseEntity<Delivery> getDeliveryById(@PathVariable("id") Long id) throws Exception {
        Delivery delivery = deliveryService.getDeliveryById(id);
        return ResponseEntity.ok(delivery);
    }

    @GetMapping("/getdelivery/order/{orderId}")
    public ResponseEntity<Delivery> getDeliveryByOrderId(@PathVariable("orderId") Long orderId) throws Exception {
        Delivery delivery = deliveryService.findByOrder(orderId);
        return ResponseEntity.ok(delivery);
    }

    @PostMapping("/Inventory/seller/{sellerId}/product/{productId}")
    public ResponseEntity<ApiResponseInventory> createInventory(@PathVariable Long sellerId, @PathVariable Long productId, @RequestParam int quantity,@RequestBody Inventory inventory) {
        try {
            Inventory inventorys=inventoryService.createInventory(inventory,productId,sellerId);// Adjust as needed
            ApiResponseInventory response = new ApiResponseInventory("Inventory created successfully", true, inventorys);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponseInventory response = new ApiResponseInventory("Failed to create inventory: " + e.getMessage(), false, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/update/inventory/{id}")
    public ResponseEntity<ApiResponseInventory> updateInventory(@PathVariable("id") Long inventoryId, @RequestBody Inventory inventory) {
        try {
            Inventory updatedInventory = inventoryService.updateInventory(inventoryId, inventory);
            ApiResponseInventory response = new ApiResponseInventory("Inventory updated successfully", true, updatedInventory);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponseInventory response = new ApiResponseInventory("Failed to update inventory: " + e.getMessage(), false, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/delete/inventory/{id}")
    public ResponseEntity<ApiResponseInventory> deleteInventory(@PathVariable("id") Long inventoryId) {
        try {
            inventoryService.deleteInventory(inventoryId);
            ApiResponseInventory response = new ApiResponseInventory("Inventory deleted successfully", true, null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponseInventory response = new ApiResponseInventory("Failed to delete inventory: " + e.getMessage(), false, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/get/inventory/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable("id") Long id ) throws Exception {
        Inventory inventory = inventoryService.getInventoryById(id);
        return ResponseEntity.ok(inventory);
    }
@PutMapping("/increaestock/{id}")
    public ResponseEntity<ApiResponseInventory> increaseStock(@PathVariable("productId") Long productId,@RequestParam int quantity) {
    try {
        inventoryService.increaseStock(productId, quantity);
        ApiResponseInventory response = new ApiResponseInventory("Inventory stock increased successfully", true, null);
        return ResponseEntity.ok(response);
    } catch (Exception e) {
        ApiResponseInventory response = new ApiResponseInventory("Failed to increase inventory stock: " + e.getMessage(), false, null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}

@PostMapping("/decreasestock/{id}")
    public ResponseEntity<ApiResponseInventory> decreaseStock(@PathVariable("productId") Long productId,@RequestParam int quantity) {
    try {
        inventoryService.reduceStock(productId, quantity);
        ApiResponseInventory response = new ApiResponseInventory("Inventory stock decreased successfully", true, null);
        return ResponseEntity.ok(response);
    } catch (Exception e) {
        ApiResponseInventory response = new ApiResponseInventory("Failed to decrease inventory stock: " + e.getMessage(), false, null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}

}












