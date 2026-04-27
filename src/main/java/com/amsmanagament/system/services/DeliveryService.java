package com.amsmanagament.system.services;

import com.amsmanagament.system.model.Delivery;

import java.util.List;

public interface DeliveryService {

    Delivery createDelivery(Delivery delivery,Long orderId);
    Delivery findByOrder(Long orderId);

    Delivery updateDelivery(Long id, Delivery delivery);

    Delivery getDeliveryById(Long id);

    List<Delivery> getAllDeliveries();
    Delivery verifyDelivery(Long id);

    void deleteDelivery(Long id);

    Delivery compeleteDelivery(Long id);

    int countTotalDeliveriesByFarmer(Long farmerId);

    int countCompletedDeliveriesByFarmer(Long farmerId);

    int countPendingDeliveriesByFarmer(Long farmerId);


}
