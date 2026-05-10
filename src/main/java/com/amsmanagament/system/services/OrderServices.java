package com.amsmanagament.system.services;

import com.amsmanagament.system.model.Order;
import com.amsmanagament.system.model.User;

import java.util.List;
import java.util.Optional;

public interface OrderServices {

    Order createOrder(Order order) throws Exception;

    Order updateOrder(Long id, Order order) throws Exception;//admin

    Order deleteOrder(Long id) throws Exception;//admin

    List<Order> getAllOrders() throws Exception;//admin

    Optional<Order> getOrderById(Long id) throws Exception;//admin

    List<Order> getOrdersByUserId(Long userId) throws Exception;//admin

    Order updateOrderStatus(Long id, String status)throws Exception;//admin

    Order trackOrder(Long id) throws Exception;//admim

    Order verifyOrder(Long id)throws Exception;//admin

    Order cancelOrder(Long id, User currentuser)throws  Exception;//buyer


    Order trackOrderLocation(Long orderId) throws  Exception;

    List<Order> findordeforfarmerid(Long farmerid);//buyer




}
