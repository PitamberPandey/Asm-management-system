package com.amsmanagament.system.controller;


import com.amsmanagament.system.Response.ApiCreateResponse;


import com.amsmanagament.system.Response.ProductResponse;
import com.amsmanagament.system.Response.SellerResponse;
import com.amsmanagament.system.model.Farmer;

import com.amsmanagament.system.model.Product;
import com.amsmanagament.system.model.Seller;
import com.amsmanagament.system.services.Farmerservice;
import com.amsmanagament.system.services.ProductService;
import com.amsmanagament.system.services.SellerService;
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
    @PostMapping("/update/seller")
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


}












