package com.amsmanagament.system.services;

import com.amsmanagament.system.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderServices {

    Order createOrder(Order order) throws Exception;

    Order updateOrder(Long id, Order order) throws Exception;

    void deleteOrder(Long id) throws Exception;

    List<Order> getAllOrders() throws Exception;

    Optional<Order> getOrderById(Long id) throws Exception;

    List<Order> getOrdersByUserId(Long userId) throws Exception;

    Order updateOrderStatus(Long id, String status)throws Exception;

    Order trackOrder(Long id) throws Exception;

    Order verifyOrder(Long id)throws Exception;

    Order cancelOrder(Long id)throws  Exception;

    Double calculateOrderTotal(Long orderId)throws  Exception;
    Order trackOrderLocation(Long orderId) throws  Exception;




}
