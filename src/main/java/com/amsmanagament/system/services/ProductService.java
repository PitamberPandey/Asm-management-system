package com.amsmanagament.system.services;

import com.amsmanagament.system.model.Product;

import java.util.List;

public interface ProductService {

    // Create a new product
    Product createProduct(Product product,Long id) throws Exception;

    // Update existing product
    Product updateProduct(Long productId, Product product) throws Exception;

    // Delete a product
    void deleteProduct(Long productId) throws Exception;

    // Get a single product by ID
    Product getProductById(Long productId) throws Exception;

    // Get all products
    List<Product> getAllProducts();

    // Search products by keyword (name or description)
    List<Product> searchProducts(String keyword);

    // Get products by category
    List<Product> getProductsByCategory(Long categoryId);

    // Get products by seller/creator
    List<Product> getProductsBySeller(Long userId);
}
