package com.amsmanagament.system.controller;

import com.amsmanagament.system.Response.AddOrderItemRequest;
import com.amsmanagament.system.Response.ApiCreateBuyer;
import com.amsmanagament.system.Response.ApiOrderItemResponse;
import com.amsmanagament.system.Response.ApiOrderResponse;
import com.amsmanagament.system.exception.ResourceNotFoundException;
import com.amsmanagament.system.model.*;
import com.amsmanagament.system.repo.UserRepo;
import com.amsmanagament.system.services.BuyerServices;
import com.amsmanagament.system.services.OrderItemService;
import com.amsmanagament.system.services.OrderServices;
import com.amsmanagament.system.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buyer")
public class BuyerController {

    @Autowired
    private BuyerServices buyerServices;


    @Autowired
    private SellerService sellerService;

    @Autowired
    private OrderServices orderServices;

    @Autowired
    UserRepo userRepo;

    @Autowired
    private OrderItemService orderItemService;

    // Create buyer profile
    @PostMapping("/create")
    public ResponseEntity<ApiCreateBuyer> createProfileBuyer(@RequestBody Buyer buyer) throws Exception {
        Buyer created = buyerServices.createProfile(buyer);
        ApiCreateBuyer apiResponse = new ApiCreateBuyer("Profile created successfully", true, created);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("buyer/{id}")
    public Buyer getBuyer(@PathVariable("id") Long id) throws Exception {
        Buyer buyer=buyerServices.getBuyerById(id);
        return buyer;
    }

    // Update buyer profile
    @PutMapping("/update")
    public ResponseEntity<ApiCreateBuyer> updateProfile(@RequestBody Buyer buyer) throws Exception {
        Buyer updated = buyerServices.updateProfile(buyer);
        ApiCreateBuyer apiResponse = new ApiCreateBuyer("Profile updated successfully", true, updated);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/search/seller")
    public List<Seller> search(@RequestParam String keyword) throws Exception {
        List<Seller> sellers=sellerService.searchSellersByName(keyword.trim());
        if(sellers==null){
            throw  new ResourceNotFoundException("Empty seller");
        }
        return sellers;
    }

    @GetMapping("/search")
    public List<Seller> searchSellers(
            @RequestParam("lat") double latitude,
            @RequestParam("lon") double longitude,
            @RequestParam("radius") double radiusKm
    ) {
        return sellerService.findSellersInRange(latitude, longitude, radiusKm);
    }


    @PostMapping("/create/order")
    public ResponseEntity<ApiOrderResponse> createorder(@RequestBody Order order) throws Exception{
        try {
            Order order1 =orderServices.createOrder(order);
            ApiOrderResponse api=new ApiOrderResponse("Order created successfully",true,order1);
            return ResponseEntity.ok(api);
        }catch (Exception e){
            ApiOrderResponse api=new ApiOrderResponse("failed to create order",false,null);
            return ResponseEntity.badRequest().body(api);
        }

    }

    @PutMapping("/cancel/order/{id}")
    public ResponseEntity<ApiOrderResponse> cancelOrder(
            @PathVariable("id") Long orderId,
            @RequestBody CancelOrderRequest request) {

        try {
            // Validate input
            if (orderId == null || request.getUserId() == null || request.getRole() == null) {
                return ResponseEntity.badRequest().body(
                        new ApiOrderResponse("Order ID, User ID, and Role cannot be null", false, null)
                );
            }

            // Fetch user from DB
            User currentUser = userRepo.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));



            // Call service
            Order cancelledOrder = orderServices.cancelOrder(orderId, currentUser);

            // Return success response
            ApiOrderResponse api = new ApiOrderResponse(
                    "Order canceled successfully",
                    true,
                    cancelledOrder
            );
            return ResponseEntity.ok(api);

        } catch (Exception e) {
            // Return error response
            ApiOrderResponse api = new ApiOrderResponse(
                    e.getMessage(),
                    false,
                    null
            );
            return ResponseEntity.badRequest().body(api);
        }
    }


    @GetMapping("/{orderId}/location")
    public ResponseEntity<Order> trackOrderLocation(@PathVariable Long orderId) throws Exception {
        Order order = orderServices.trackOrderLocation(orderId);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/order/items")
    public ResponseEntity<ApiOrderItemResponse> addOrderItems(@RequestBody AddOrderItemRequest request) throws Exception {
        try {
            OrderItem savedItems = orderItemService.addItemToOrder(request.getOrderId(),request.getProductId(), request.getQuantity()
            );
            ApiOrderItemResponse apiResponse = new ApiOrderItemResponse("Order items added successfully", true, savedItems);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            ApiOrderItemResponse apiResponse = new ApiOrderItemResponse("Failed to add order items: " + e.getMessage(), false, null);
            return ResponseEntity.badRequest().body(apiResponse);
        }


    }

    @PutMapping("/order/items/{itemId}/quantity")
    public ResponseEntity<ApiOrderItemResponse> updateOrderItemQuantity(
            @PathVariable Long itemId,
            @RequestParam int quantity) throws Exception {
        try {
            OrderItem updatedItem = orderItemService.updateOrderItemQuantity(itemId, quantity);
            ApiOrderItemResponse apiResponse = new ApiOrderItemResponse("Order item quantity updated successfully", true, updatedItem);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            ApiOrderItemResponse apiResponse = new ApiOrderItemResponse("Failed to update order item quantity: " + e.getMessage(), false, null);
            return ResponseEntity.badRequest().body(apiResponse);
        }
    }

    @DeleteMapping("/order/items/item/{itemId}")
    public ResponseEntity<ApiOrderItemResponse> removeOrderItem(@PathVariable Long itemId) throws Exception {
        try {
            OrderItem removedItem = orderItemService.removeOrderItem(itemId);
            ApiOrderItemResponse apiResponse = new ApiOrderItemResponse("Order item removed successfully", true, removedItem);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            ApiOrderItemResponse apiResponse = new ApiOrderItemResponse("Failed to remove order item: " + e.getMessage(), false, null);
            return ResponseEntity.badRequest().body(apiResponse);
        }
    }
}
