package com.amsmanagament.system.services;

import com.amsmanagament.system.model.Farmer;
import com.amsmanagament.system.model.User;

import java.util.List;

public interface Farmerservice {

    public Farmer findById(Long id) throws Exception;
    public List<Farmer> findByUsername(String username) throws Exception;
    Farmer createFarmer(Farmer farmer) throws  Exception;
    Farmer updateFarmer(Farmer farmer) throws  Exception;
   List<Farmer> getallfarmer()throws Exception;
   Farmer finduser(User user)throws Exception;
   Farmer verifyFarmer(Long id) throws  Exception;
    Farmer rejectFarmer(Long id) throws  Exception;
    void deleteFarmer(Long id) throws Exception;
}
