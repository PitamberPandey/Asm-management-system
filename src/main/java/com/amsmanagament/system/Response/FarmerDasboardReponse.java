package com.amsmanagament.system.Response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FarmerDasboardReponse {

    private int totalProducts;
    private Long totalOrders;
    private Long totalSoldItems;
    private int TotalDeliveredItems;
    private int totalDelivered;
    private int pendingDeliveries;
    private  Double  TotalEarn_Price;





}