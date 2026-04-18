package com.amsmanagament.system.controller;



import com.amsmanagament.system.Response.*;
import com.amsmanagament.system.exception.ResourceNotFoundException;
import com.amsmanagament.system.model.*;
import com.amsmanagament.system.repo.UserRepo;
import com.amsmanagament.system.serviceImpl.OtpService;
import com.amsmanagament.system.services.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.amsmanagament.system.model.User;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminContoller {

    @Autowired
    private UserService userService;

    @Autowired
    Farmerservice farmerservice;

    @Autowired
    BuyerServices buyerServices;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;
    @Autowired
    SmsService smsService;

    @Autowired
    PasswordEncoder passwordEncoder;



    @Autowired
    OtpService otpService;


    @Autowired
    SellerService sellerService;

    @Autowired
    OrderServices orderServices;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    DeliveryService deliveryService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    UserRepo userRepo;

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) throws Exception {
        User user = userService.findByUserId(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/phone/{number}")
    public ResponseEntity<User> getUserByPhoneNumber(@PathVariable String number) throws Exception {
        User user = userService.findUserByNumber(number);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/user/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) throws Exception {
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);

    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) throws Exception {
        userService.deleteUser(id);
        return ResponseEntity.ok(new ApiResponse("User deleted successfully!", true));
    }

    @GetMapping("/farmer")
    public List<Farmer> getAllFarmer() throws Exception {
        return farmerservice.getallfarmer();
    }

    @GetMapping("/farmer/{id}")
    public ResponseEntity<Farmer> getFarmerById(@PathVariable Long id) throws Exception {
        Farmer user = farmerservice.findById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("farmer/verify/{id}")
    public ResponseEntity<ApiCreateResponse> verifyFarmer(@PathVariable Long id) throws Exception {

        Farmer farmer = farmerservice.verifyFarmer(id);

        ApiCreateResponse apiResponse = new ApiCreateResponse(
                "Farmer verified successfully",
                true,
                farmer
        );

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("farmer/reject/{id}")
    public ResponseEntity<ApiCreateResponse> rejectFarmer(@PathVariable Long id) throws Exception {

        Farmer farmer = farmerservice.rejectFarmer(id);

        ApiCreateResponse apiResponse = new ApiCreateResponse(
                "Farmer is rejected for improper document",
                false,
                farmer
        );

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/buyer/{id}")
    public ResponseEntity<Buyer> getBuyerById(@PathVariable Long id) throws Exception {
        Buyer user = buyerServices.getBuyerById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/buyer")
    public List<Buyer> getAllBuyer() throws Exception {
        return buyerServices.getAllBuyers();
    }

    @DeleteMapping("/buyer/delete/{id}")
    public ResponseEntity<ApiResponse> deleteBuyer(@PathVariable Long id) throws Exception {
        buyerServices.deleteBuyer(id);
        return ResponseEntity.ok(new ApiResponse("Buyer deleted successfully!", true));
    }

    @GetMapping("/buyer/search")
    public List<Buyer> searchBuyersByName(@RequestParam String name) throws Exception {
        return buyerServices.searchBuyersByName(name);
    }

    @PostMapping("/create/category")
    public ResponseEntity<ApiResponseCategory> createCategory(@RequestBody Category category) throws Exception {
        Category createdCategory = categoryService.createCategory(category);
        ApiResponseCategory apiResponse = new ApiResponseCategory("Category created successfully", true, createdCategory);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/update/category")
    public ResponseEntity<ApiResponseCategory> updateCategory(@RequestBody Category category) throws Exception {
        Category updatedCategory = categoryService.updateCatogory(category);
        ApiResponseCategory apiResponse = new ApiResponseCategory("Category updated successfully", true, updatedCategory);
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete/category/{id}")
    public ResponseEntity<ApiResponseCategory> deleteCategory(@PathVariable Long id) throws Exception {
        Category deletedCategory = categoryService.deleteByCategoryid(id);
        ApiResponseCategory apiResponse = new ApiResponseCategory("Category deleted successfully", true, deletedCategory);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/search/category")
    public ResponseEntity<ApiResponseCategory> searchCategoryByName(@RequestParam String name) throws Exception {
        Category category = categoryService.searchbycatorgoryname(name);
        ApiResponseCategory apiResponse = new ApiResponseCategory("Category found successfully", true, category);
        return ResponseEntity.ok(apiResponse);

    }


    @GetMapping("/farmer/product/{id}")
    public ResponseEntity<Product> getProductsByfarmer(@PathVariable Long id) throws Exception {
        List<Product> products = productService.getProductsBySeller(id);
        return ResponseEntity.ok().body((products.get(0)));
    }


    @PostMapping("/delete/{id}")
    public ResponseEntity<SellerResponse> deleteSeller(@PathVariable("id") Long id) throws Exception {
        try {
            Seller seller1 = sellerService.deleteSeller(id);
            SellerResponse sellerResponse = new SellerResponse("seller delete successfully", true, seller1);
            return ResponseEntity.ok(sellerResponse);
        } catch (Exception e) {
            SellerResponse sellerResponse = new SellerResponse(" failed  for deleteUser seller", false, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(sellerResponse);
        }


    }

    @GetMapping("/seller")
    public List<Seller> sellers() throws Exception {
        List<Seller> sellers = sellerService.getAllSellers();
        return sellers;
    }


    @GetMapping("seller/{id}")
    public Seller getSeller(@PathVariable("id") Long id) throws Exception {
        try {
            Seller seller = sellerService.getSellerById(id);
            return seller;
        } catch (Exception e) {
            throw new ResourceNotFoundException("seller not found this id" + id);
        }

    }

    @DeleteMapping("seller/{id}")
    public Seller deleteseller(@PathVariable("id") Long id) throws Exception {
        try {
            Seller seller = sellerService.getSellerById(id);
            return seller;
        } catch (Exception e) {
            throw new ResourceNotFoundException("seller not found this id" + id);
        }

    }


    @PutMapping("/update/order/{id}")
    public ResponseEntity<ApiOrderResponse> Updateorder(@RequestBody Order order, @PathVariable("id") Long id) throws Exception {
        try {
            Order order1 = orderServices.updateOrder(id, order);
            ApiOrderResponse api = new ApiOrderResponse("Order Update successfully", true, order1);
            return ResponseEntity.ok(api);
        } catch (Exception e) {
            ApiOrderResponse api = new ApiOrderResponse("failed to update order", false, null);
            return ResponseEntity.badRequest().body(api);
        }

    }

    @DeleteMapping("/delete/order/{id}")
    public ResponseEntity<ApiOrderResponse> deleteOrder(@PathVariable("id") Long id) throws Exception {
        try {
            Order order1 = orderServices.deleteOrder(id);
            ApiOrderResponse api = new ApiOrderResponse("delete order successfully", true, order1);
            return ResponseEntity.ok(api);
        } catch (Exception e) {
            ApiOrderResponse api = new ApiOrderResponse("failed to order order", false, null);
            return ResponseEntity.badRequest().body(api);
        }
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrders() throws Exception {
        List<Order> orders = orderServices.getAllOrders();
        return ResponseEntity.ok(orders);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) throws Exception {
        Optional<Order> orderOpt = orderServices.getOrderById(id);
        if (orderOpt.isPresent()) {
            return ResponseEntity.ok(orderOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 2️⃣ Get all orders for a specific user
    @GetMapping("/userorder/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Long userId) throws Exception {
        List<Order> orders = orderServices.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    // 3️⃣ Update order status
    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam String status) throws Exception {
        Order updatedOrder = orderServices.updateOrderStatus(id, status);
        return ResponseEntity.ok(updatedOrder);
    }

    // 4️⃣ Track order
    @GetMapping("/{id}/track")
    public ResponseEntity<Order> trackOrder(@PathVariable Long id) throws Exception {
        Order order = orderServices.trackOrder(id);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/admin/{id}/verify")
    public ResponseEntity<Order> verifyOrder(@PathVariable Long id) throws Exception {
        Order order = orderServices.verifyOrder(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/orderitems")
    public ResponseEntity<List<OrderItem>> getAllOrderItems() throws Exception {
        List<OrderItem> orderItems = orderItemService.getAllOrderItems();
        return ResponseEntity.ok(orderItems);
    }

    @GetMapping("/deliveries")
    public ResponseEntity<List<Delivery>> getAllDeliveries() throws Exception {
        List<Delivery> deliveries = deliveryService.getAllDeliveries();
        return ResponseEntity.ok(deliveries);
    }

    @PostMapping("/createNotification")
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequestDto request) {

        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        notificationService.createNotification(
                user,
                null, // sender can be null for admin notifications
                request.getActionType(),
                request.getMessage(),
                request.getReferenceId()
        );
        return ResponseEntity.ok("Notification sent successfully");
    }

    @GetMapping("/notifications/{userId}")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable Long userId) {
        List<Notification> notifications = notificationService.getUserNotifications(userId);
        return ResponseEntity.ok(notifications);
    }
    @GetMapping("/notifications")
    public ResponseEntity<List<Notification>> getAllNotifications() {
        List<Notification> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }
    @DeleteMapping("/notifications/delete/{id}")
    public ResponseEntity<Notification> deleteNotification(@PathVariable Long id) {
        Notification deletedNotification = notificationService.deleteNotification(id);
        return ResponseEntity.ok(deletedNotification);
    }

    @PutMapping("/notifications/markAsRead/{id}")
    public ResponseEntity<String> markNotificationAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok("Notification marked as read");
    }


    @GetMapping("/inventory")
    public List<Inventory> getALL() {
        List<Inventory> inventories = inventoryService.getAll();
        return inventories;
    }

    @DeleteMapping("/delete/farmer/{id}")
    public ResponseEntity<FarmerReponse> deleteFarmer(@PathVariable Long id) throws Exception {
        farmerservice.deleteFarmer(id);

        FarmerReponse response = new FarmerReponse(
                "Farmer deleted successfully",
                true,
                null
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/user")
    public List<User> getAllUsers() throws Exception {
        List<User> users = userService.getAllUsers();
        return users;
    }

    @GetMapping("/search/user")
    public List<User> searchUsersByName(@RequestParam String name) throws Exception {
        return userService.searchUsersByUsername(name);
    }

    @PostMapping("/change-password/send-otp")
    public ResponseEntity<String> sendOtp() throws Exception {

        String phoneNumber = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepo.findByPhoneNumber(phoneNumber);

        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        String otp = otpService.generateOtp(phoneNumber);

        smsService.sendSms("+977" + phoneNumber, "Your OTP is: " + otp);

        return ResponseEntity.ok("OTP sent successfully");
    }

    @PostMapping("/change-password/verify")
    public ResponseEntity<String> verifyAndChangePassword(
            Authentication auth,
            @RequestParam String otp,
            @RequestParam String newPassword
    ) {

        String phoneNumber = auth.getName();

        boolean valid = otpService.validateOtp(phoneNumber, otp);

        if (!valid) {
            return ResponseEntity.badRequest().body("Invalid OTP");
        }

        User user = userRepo.findByPhoneNumber(phoneNumber);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);

        return ResponseEntity.ok("Password changed successfully");
    }
}






