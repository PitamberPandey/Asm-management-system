package com.amsmanagament.system.services;

import com.amsmanagament.system.model.Livelocationdto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class TrackingService {

    @Autowired
    private OrderServices orderServices;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void startMovement(Long orderId,
                              Double startLat, Double startLng,
                              Double endLat, Double endLng) {

        new Thread(() -> {

            int steps = 100;
            String currentStatus = null;   // ✅ FIXED: declare status tracker

            for (int i = 0; i <= steps; i++) {

                double t = (double) i / steps;

                Double lat = startLat + (endLat - startLat) * t;
                Double lng = startLng + (endLng - startLng) * t;

                // 📍 Send live location
                Livelocationdto dto = new Livelocationdto(
                        orderId,
                        lat,
                        lng
                );

                messagingTemplate.convertAndSend(
                        "/topic/order/" + orderId,
                        dto
                );

                // 🚚 STATUS LOGIC

                if (i == 0 && !"DISPATCHED".equals(currentStatus)) {
                    try {
                        orderServices.updateOrderStatus(orderId, "DISPATCHED");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    currentStatus = "DISPATCHED";
                }

                if (i == steps / 2 && !"IN_TRANSIT".equals(currentStatus)) {
                    try {
                        orderServices.updateOrderStatus(orderId, "IN_TRANSIT");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    currentStatus = "IN_TRANSIT";
                }

                if (i == steps && !"DELIVERED".equals(currentStatus)) {
                    try {
                        orderServices.updateOrderStatus(orderId, "DELIVERED");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    currentStatus = "DELIVERED";
                }
                System.out.println("-------------------------------------------------- tracking order " + orderId + " --------------------------------------------------");
                System.out.println("Order ID: " + orderId);
                System.out.println("Step " + i + ": Lat=" + lat + ", Lng=" + lng + ", Status=" + currentStatus);


                try {
                    Thread.sleep(1000); // speed control
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }
}