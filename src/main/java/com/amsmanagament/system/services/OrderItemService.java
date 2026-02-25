package com.amsmanagament.system.services;

import com.amsmanagament.system.model.OrderItem;

import java.util.List;

public interface OrderItemService {

    OrderItem addItemToOrder(Long orderId, Long productId, int quantity);

    OrderItem updateOrderItemQuantity(Long orderItemId, int quantity);

    OrderItem removeOrderItem(Long orderItemId);

    OrderItem getOrderItemById(Long orderItemId);
    List<OrderItem> getAllOrderItems();

    List<OrderItem> getOrderItemsByOrderId(Long orderId);
    Long calculateTotalAmountByOrderId(Long orderId);




}
