package com.amsmanagament.system.serviceImpl;


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

import java.util.List;
import java.util.Optional;


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
    public Product createProduct(Product product, Integer userid) throws Exception {
        String phoneNumber= SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User loggedInUser=userRepo.findByPhoneNumber(phoneNumber);
        Farmer farmer=farmerRepo.findByUser(loggedInUser).orElseThrow(()->new Exception("Farmer profile not found for the logged-in user"));
        if(loggedInUser==null){
            throw new Exception("Logged in user not found");
        }
        Product product1=productRepo.findByCategory(product.getCategory()).stream().findFirst().orElseThrow(()->new Exception("Product not found on this catogroy"));
        product1.setId(product.getId());
        product1.setCategory(product.getCategory());
        product1.setDescription(product.getDescription());
        product1.setPrice(product.getPrice());
        product1.setProductName(product.getProductName());
        product1.setFarmer(farmer);
        
        
                

        return productRepo.save(product1);
    }

    @Override
    public Product updateProduct(Long productId, Product product) throws Exception {
        Product existingProduct=productRepo.findById(productId).orElseThrow(()->new Exception("Product not found"));
        existingProduct.setProductName(product.getProductName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
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
        Product product=productRepo.findById(productId).orElseThrow(()->new Exception("Product not found"));
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
            throw new RuntimeException("Product not found with keyword: " + keyword);
        }


        return searchProduct;
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        // Check if category exists
        Category category = catogoriesRepo.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));

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

        // Fetch all products by this farmer
        List<Product> products = (List<Product>) productRepo.findByFarmerId(farmer.getId());

        if (products.isEmpty()) {
            throw new RuntimeException("No products found for this seller");
        }

        return products;
    }



////


}

