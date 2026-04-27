package com.amsmanagament.system.serviceImpl;


import com.amsmanagament.system.exception.ResourceNotFoundException;
import com.amsmanagament.system.model.Category;
import com.amsmanagament.system.model.Farmer;
import com.amsmanagament.system.model.Product;

import com.amsmanagament.system.model.User;
import com.amsmanagament.system.repo.CatogoriesRepo;
import com.amsmanagament.system.repo.FarmerRepo;
import com.amsmanagament.system.repo.ProductRepo;
import com.amsmanagament.system.repo.UserRepo;
import com.amsmanagament.system.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;



@Service
public class ProductServiceimpl implements ProductService {
@Autowired
    UserRepo userRepo;

@Autowired
FarmerRepo farmerRepo;

@Autowired
ProductRepo productRepo;

@Autowired
    CatogoriesRepo catogoriesRepo;



    @Override
    public Product createProduct(Product product) throws Exception {
        String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User loggedInUser = userRepo.findByPhoneNumber(phoneNumber);

        if (loggedInUser == null) {
            throw new Exception("Logged in user not found");
        }
        Farmer farmer = farmerRepo.findByUser(loggedInUser)
                .orElseThrow(() -> new Exception("Farmer profile not found for the logged-in user"));



        Product newProduct = new Product();
        newProduct.setProductName(product.getProductName());
        newProduct.setDescription(product.getDescription());
        newProduct.setPrice(product.getPrice());
        newProduct.setAvailable(product.isAvailable());
        newProduct.setImageUrl(product.getImageUrl());
        newProduct.setCategory(product.getCategory());
        newProduct.setAddress(product.getAddress());// expects nested JSON { "id": 5 }
        newProduct.setFarmer(farmer);
        newProduct.setCreatedAt(LocalDateTime.now());
        newProduct.setUpdatedAt(LocalDateTime.now());

        return productRepo.save(newProduct);
    }


    @Override
    public Product updateProduct(Long productId, Product product) throws Exception {
        Product existingProduct=productRepo.findById(productId).orElseThrow(()->new Exception("Product not found"));
        existingProduct.setProductName(product.getProductName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setImageUrl(product.getImageUrl());
        existingProduct.setCategory(product.getCategory());
        productRepo.save(existingProduct);
            
        
        return  productRepo.save(existingProduct);
    }

    @Override
    public void deleteProduct(Long productId) throws Exception {
        Product product=productRepo.findById(productId).orElseThrow(()->new Exception("Product not found"));
        productRepo.delete(product);

    }

    @Override
    public Product getProductById(Long productId) throws Exception {
        Product product=productRepo.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product not found"));
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products=productRepo.findAll();
        return products;
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        List<Product> searchProduct=productRepo.findByProductNameContainingIgnoreCase(keyword);
        if (searchProduct.isEmpty()) {
            throw new ResourceNotFoundException("Product not found with keyword: " + keyword);
        }


        return searchProduct;
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        // Check if category exists
        Category category = catogoriesRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId));

        // Fetch products by category
        List<Product> products = productRepo.findByCategory(category);

        if (products.isEmpty()) {
            throw new RuntimeException("No products found for category ID: " + categoryId);
        }

        return products;
    }


    @Override
    public List<Product> getProductsBySeller(Long userId) {

        // Find user
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Find farmer profile
        Farmer farmer = farmerRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Farmer profile not found for user ID: " + userId));

        // Fetch products
        List<Product> products = productRepo.findByFarmer_Id(farmer.getId());

        if (products.isEmpty()) {
            throw new RuntimeException("No products found for this seller");
        }

        return products;
    }

    @Override
    public List<Product> getProductByaddress(String address)  {
        List<Product> products=productRepo.searchByAddress(address);
        if(products.isEmpty()){
            throw new ResourceNotFoundException("Product not found with address: "+address);
        }
        return products;
    }


}

