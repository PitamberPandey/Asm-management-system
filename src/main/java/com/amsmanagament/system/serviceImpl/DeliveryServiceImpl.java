package com.amsmanagament.system.serviceImpl;

import com.amsmanagament.system.exception.ResourceNotFoundException;
import com.amsmanagament.system.model.Delivery;
import com.amsmanagament.system.model.Order;
import com.amsmanagament.system.repo.DeliveryRepo;
import com.amsmanagament.system.repo.OrderRepo;
import com.amsmanagament.system.services.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private DeliveryRepo deliveryRepo;

    @Override
    public Delivery createDelivery(Delivery delivery, Long orderId) {
        Order orderRepo1 = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        Delivery delivery1 = new Delivery();
        delivery1.setDeliveryAddress(delivery.getDeliveryAddress());
        delivery1.setOrder(orderRepo1);
        delivery1.setDeliveryStatus("Pending");
        delivery1.setContactNumber(delivery.getContactNumber());
        delivery1.setRecipientName(delivery.getRecipientName());
        delivery1.setLatitude(delivery.getLatitude());
        delivery1.setLongitude(delivery.getLongitude());
        delivery1.setDeliveryTime(delivery.getDeliveryTime());

        return deliveryRepo.save(delivery1);
    }

    @Override
    public Delivery findByOrder(Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        return deliveryRepo.findByOrder(order);
    }

    @Override
    public Delivery updateDelivery(Long id, Delivery delivery) {
        Delivery existingDelivery = deliveryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found"));

        existingDelivery.setDeliveryAddress(delivery.getDeliveryAddress());
        existingDelivery.setContactNumber(delivery.getContactNumber());
        existingDelivery.setDeliveryStatus(delivery.getDeliveryStatus());
        existingDelivery.setRecipientName(delivery.getRecipientName());
        existingDelivery.setDeliveryTime(delivery.getDeliveryTime());
        existingDelivery.setLatitude(delivery.getLatitude());
        existingDelivery.setLongitude(delivery.getLongitude());

        return deliveryRepo.save(existingDelivery);
    }

    @Override
    public Delivery getDeliveryById(Long id) {
        return deliveryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found"));
    }

    @Override
    public List<Delivery> getAllDeliveries() {
        return deliveryRepo.findAll();
    }

    @Override
    public Delivery verifyDelivery(Long id) {
        Delivery find = deliveryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found"));

        find.setDeliveryStatus("Verified");
        return deliveryRepo.save(find);
    }

    @Override
    public void deleteDelivery(Long id) {
        Delivery delivery = deliveryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found"));
        deliveryRepo.delete(delivery);
    }

    @Override
    public Delivery compeleteDelivery(Long id) {
        Delivery delivery=deliveryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Delivery not found"));
        delivery.setDeliveryStatus("Completed");
         return deliveryRepo.save(delivery);

    }
    @Override
    public int countTotalDeliveriesByFarmer(Long farmerId) {
        return deliveryRepo.countTotalDeliveriesByFarmer(farmerId);
    }

    @Override
    public int countCompletedDeliveriesByFarmer(Long farmerId) {
        return deliveryRepo.countDeliveredByFarmer(farmerId);
    }

    @Override
    public int countPendingDeliveriesByFarmer(Long farmerId) {
        return deliveryRepo.countPendingByFarmer(farmerId);
    }
}
