package com.amsmanagament.system.serviceImpl;

import com.amsmanagament.system.exception.ResourceNotFoundException;
import com.amsmanagament.system.model.Order;
import com.amsmanagament.system.model.OrderItem;
import com.amsmanagament.system.model.Product;

import com.amsmanagament.system.repo.OrderItemRepo;
import com.amsmanagament.system.repo.OrderRepo;
import com.amsmanagament.system.repo.ProductRepo;
import com.amsmanagament.system.services.OrderItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class OrderItemImpl  implements OrderItemService {
@Autowired
private OrderItemRepo orderItemRepo;

@Autowired
private ProductRepo productRepo;

@Autowired
private OrderRepo orderRepo;



    @Override
    public OrderItem addItemToOrder(Long orderId, Long productId, int quantity) {

        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        Order order = orderRepo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(quantity);
        orderItem.setPrice(product.getPrice() );
        return orderItemRepo.save(orderItem);

    }

    @Override
    public OrderItem updateOrderItemQuantity(Long orderItemId, int quantity) {
        OrderItem orderItem= orderItemRepo.findById(orderItemId).orElseThrow(()->new ResourceNotFoundException("Order item not found"));
        orderItem.setQuantity(quantity);
        orderItem.setPrice(orderItem.getProduct().getPrice());

        return orderItemRepo.save(orderItem);
    }

    @Override
    public OrderItem removeOrderItem(Long orderItemId) {
        OrderItem orderItem= orderItemRepo.findById(orderItemId).orElseThrow(()->new ResourceNotFoundException("Order item not found"));
        orderItemRepo.delete(orderItem);

        return orderItem;
    }

    @Override
    public OrderItem getOrderItemById(Long orderItemId) {
        OrderItem orderItem= orderItemRepo.findById(orderItemId).orElseThrow(()->new ResourceNotFoundException("Order item not found"));
        return orderItem;
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepo.findAll();
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        Order order = orderRepo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return orderItemRepo.findByOrder(order);
    }

    @Override
    public Long calculateTotalAmountByOrderId(Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

       return orderItemRepo.calculateTotalAmountByOrderId(orderId);

    }
}
