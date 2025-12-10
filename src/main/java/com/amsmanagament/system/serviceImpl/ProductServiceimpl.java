package com.amsmanagament.system.serviceImpl;

import com.amsmanagament.system.model.Product;
import com.amsmanagament.system.services.ProductService;

import java.util.List;

public class ProductServiceimpl implements ProductService {
    @Override
    public Product createProduct(Product product) throws Exception {
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
