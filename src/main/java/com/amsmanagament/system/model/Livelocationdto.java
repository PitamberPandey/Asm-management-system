package com.amsmanagament.system.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Livelocationdto {
    private Long orderId;
    private Double latitude;
    private Double longitude;
}