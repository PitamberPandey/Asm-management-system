package com.amsmanagament.system.controller;

import com.amsmanagament.system.Response.ApiCreateBuyer;
import com.amsmanagament.system.Response.ApiOrderResponse;
import com.amsmanagament.system.exception.ResourceNotFoundException;
import com.amsmanagament.system.model.Buyer;
import com.amsmanagament.system.model.Order;
import com.amsmanagament.system.model.Seller;
import com.amsmanagament.system.services.BuyerServices;
import com.amsmanagament.system.services.OrderServices;
import com.amsmanagament.system.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiOrderResponse> cancelorder(@PathVariable("id") Long id) throws Exception {
        try {
            Order order1 = orderServices.cancelOrder(id);
            ApiOrderResponse api = new ApiOrderResponse("Order cancel successfully", true, order1);
            return ResponseEntity.ok(api);
        } catch (Exception e) {
            ApiOrderResponse api = new ApiOrderResponse("failed to cancel order", false, null);
            return ResponseEntity.badRequest().body(api);
        }
    }
    @GetMapping("/{orderId}/location")
    public ResponseEntity<Order> trackOrderLocation(@PathVariable Long orderId) throws Exception {
        Order order = orderServices.trackOrderLocation(orderId);
        return ResponseEntity.ok(order);
    }
}
