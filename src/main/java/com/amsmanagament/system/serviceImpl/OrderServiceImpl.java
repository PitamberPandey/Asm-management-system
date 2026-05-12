package com.amsmanagament.system.serviceImpl;

import com.amsmanagament.system.exception.ResourceNotFoundException;
import com.amsmanagament.system.model.*;
import com.amsmanagament.system.repo.ByerRepo;
import com.amsmanagament.system.repo.FarmerRepo;
import com.amsmanagament.system.repo.OrderItemRepo;
import com.amsmanagament.system.repo.OrderRepo;
import com.amsmanagament.system.services.BuyerServices;
import com.amsmanagament.system.services.OrderServices;
import com.amsmanagament.system.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderServices {

    @Autowired
    private UserService userService;

    @Autowired
    private BuyerServices buyerServices;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private FarmerRepo farmerRepo;

    @Autowired
    ByerRepo byerRepo;

    @Autowired
    OrderItemRepo orderItemRepo;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private NotificationEventPublisher eventPublisher;

    // ---------------- CREATE ORDER ----------------

    @Override
    public Order createOrder(Order order) throws Exception {

        String phoneNumber = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal()
                .toString();

        User user = userService.findUserByNumber(phoneNumber);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // ✅ Buyer fetch
        Buyer buyer = byerRepo.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Buyer profile not found"));

        // ✅ Set fields
        order.setUser(user);
        order.setBuyer(buyer);
        Farmer farmer = order.getOrderItems()
                .get(0)
                .getProduct()
                .getFarmer();

        order.setFarmer(farmer);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderupdatedate(LocalDateTime.now());

        if (order.getStatus() == null || order.getStatus().isEmpty()) {
            order.setStatus("PENDING");
        }
        if(order.getPaymentStatus()==null ){
            order.setPaymentStatus(Payment_Status.PENDING);
        }
        // ✅ Payment Logic
        if (order.getPaymentMethod() == PaymentMethod.CASH_ON_DELIVERY) {
            order.setPaymentStatus(Payment_Status.COMPLETED);
        } else if (order.getPaymentMethod() == PaymentMethod.KHALTI) {
            order.setPaymentStatus(Payment_Status.PENDING);
        }


        // total calculation

        order.setTotalPrice(0L);
        Order savedOrder = orderRepo.save(order);

        // 🔔 SEND NOTIFICATION HERE
        eventPublisher.publish(
                new NotificationEvent(
                        user,  // receiver
                        null,  // sender (system)
                        NotificationAction.ORDER_STATUS, // or ORDER_PLACED (better)
                        "🛒 Order placed successfully (ID: " + savedOrder.getId() + ")",
                        savedOrder.getId()
                )
        );

        return savedOrder;
    }


    // ---------------- UPDATE ORDER ----------------
    @Override
    public Order updateOrder(Long id, Order order) throws Exception {

        Order existingOrder = orderRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + id));

        existingOrder.setOrderItems(order.getOrderItems());

        existingOrder.setStatus(order.getStatus());
        existingOrder.setOrderupdatedate(LocalDateTime.now());


        if (order.getBuyer() != null) {
            Buyer buyer = buyerServices.getBuyerById(order.getBuyer().getId());
            existingOrder.setBuyer(buyer);
        }

        Order savedOrder = orderRepo.save(existingOrder);

        // 🔥 SEND REAL-TIME LOCATION TO FRONTEND




        return savedOrder;
    }

    // ---------------- DELETE ORDER ----------------
    @Override
    public Order deleteOrder(Long id) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + id));
        orderRepo.delete(order);
        return order;
    }

    // ---------------- GET ALL ORDERS ----------------
    @Override
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    // ---------------- GET ORDER BY ID ----------------
    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepo.findById(id);
    }

    // ---------------- GET ORDERS BY USER ID ----------------
    @Override
    public List<Order> getOrdersByUserId(Long userId) throws Exception {
        User user = userService.findByUserId(userId);
        List<Order> orders = orderRepo.getOrderByUser(user);

        if (orders == null || orders.isEmpty())
            throw new ResourceNotFoundException("No orders found for this user");

        return orders;
    }

    // ---------------- UPDATE ORDER STATUS ----------------
    @Override
    public Order updateOrderStatus(Long id, String status) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + id));
        order.setStatus(status);
        return orderRepo.save(order);
    }

    // ---------------- TRACK ORDER ----------------
    @Override
    public Order trackOrder(Long id) {
        return orderRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + id));
    }

    // ---------------- VERIFY ORDER ----------------
    @Override
    public Order verifyOrder(Long id) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + id));
        order.setVerify("VERIFIED");
        return orderRepo.save(order);
    }

    @Override
    public Order cancelOrder(Long id, User currentUser) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + id));

        String status = order.getStatus();
        System.out.println("Order ID: " + id);
        System.out.println("Order status: " + order.getStatus());
        System.out.println("Current user ID: " + currentUser.getId());
        System.out.println("Current user role: " + currentUser.getRole());


        if ("PENDING".equals(status)) {
            // Any user can cancel if order is pending
            order.setStatus("CANCELLED");
        } else if ("Approved".equals(status) && "ROLE_ADMIN".equals(currentUser.getRole())) {
            // Only admin can cancel approved orders
            order.setStatus("CANCELLED");
        } else {
            throw new RuntimeException("You are not authorized to cancel this order");
        }

        return orderRepo.save(order);
    }




    // ---------------- CALCULATE ORDER TOTAL ----------------


    @Override
    public Order trackOrderLocation(Long orderId) throws Exception {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + orderId));

        // Optionally, you can check if latitude and longitude exist


        // Return the order with its location
        return order;
    }

    @Override
    public List<Order> findordeforfarmerid(Long farmerid) {

        Long farmer= farmerRepo.findById(farmerid).orElseThrow(()->new ResourceNotFoundException("Farmer not found with id "+farmerid)).getId();
        List<Order> orders=orderRepo.findOrdersForFarmer(farmer);
        return orders;
    }
}
