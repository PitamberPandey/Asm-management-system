package com.amsmanagament.system.Response;

import com.amsmanagament.system.model.Farmer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FarmerReponse {
    private String message;
    private boolean status;
    private Farmer data;




    }
