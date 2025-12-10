package com.amsmanagament.system.serviceImpl;

import com.amsmanagament.system.exception.ResourceNotFoundException;
import com.amsmanagament.system.model.Category;
import com.amsmanagament.system.model.Farmer;
import com.amsmanagament.system.model.Product;
import com.amsmanagament.system.repo.CatogoriesRepo;
import com.amsmanagament.system.repo.UserRepo;
import com.amsmanagament.system.services.Farmerservice;
import com.amsmanagament.system.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductServiceimpl implements ProductService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    Farmerservice farmerservice;

    @Autowired
    CatogoriesRepo catogoriesRepo;

    @Override
    public Product createProduct(Product product, Long id) throws Exception {




        return null;
    }

    @Override
    public Product updateProduct(Long productId, Product product) throws Exception {
        return null;
    }

    @Override
    public void deleteProduct(Long productId) throws Exception {

    }

    @Override
    public Product getProductById(Long productId) throws Exception {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        return List.of();
    }

    @Override
    public List<Product> getProductsByCategory(Long categoryId) {
        return List.of();
    }

    @Override
    public List<Product> getProductsBySeller(Long userId) {
        return List.of();
    }
}
