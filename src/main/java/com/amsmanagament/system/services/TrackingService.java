package com.amsmanagament.system.services;




import com.amsmanagament.system.model.Livelocationdto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class TrackingService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void startMovement(Long orderId,
                              Double startLat, Double startLng,
                              Double endLat, Double endLng) {

        new Thread(() -> {

            int steps = 100; // smooth movement

            for (int i = 0; i <= steps; i++) {

                double t = (double) i / steps;

                Double lat = startLat + (endLat - startLat) * t;
                Double lng = startLng + (endLng - startLng) * t;

                Livelocationdto dto = new Livelocationdto(
                        orderId,
                        lat,
                        lng
                );


                // 🔥 SEND VIA WEBSOCKET
                messagingTemplate.convertAndSend(
                        "/topic/order/" + orderId,
                        dto
                );

                try {
                    Thread.sleep(1000); // speed control
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }
}
