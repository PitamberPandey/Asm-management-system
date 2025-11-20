package com.amsmanagament.system.serviceImpl;



import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {

    private final Map<String, String> otpStorage = new HashMap<>(); // phone -> otp

    public String generateOtp(String phoneNumber) {
        String otp = String.format("%06d", new Random().nextInt(999999));
        otpStorage.put(phoneNumber, otp);
        return otp;
    }

    public boolean validateOtp(String phoneNumber, String otp) {
        if (!otpStorage.containsKey(phoneNumber)) return false;
        boolean isValid = otpStorage.get(phoneNumber).equals(otp);
        if (isValid) otpStorage.remove(phoneNumber); // remove after successful validation
        return isValid;
    }
}
