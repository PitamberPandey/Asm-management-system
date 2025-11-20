package com.amsmanagament.system.services;

public interface SmsService {

    void sendSms(String phoneNumber, String message) throws Exception;
}
