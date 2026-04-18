package com.amsmanagament.system.controller;



import com.amsmanagament.system.model.User;
import com.amsmanagament.system.repo.UserRepo;
import com.amsmanagament.system.serviceImpl.OtpService;
import com.amsmanagament.system.services.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/send")
public class forgetPasswordController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OtpService otpService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Step 1: Send OTP
    @PostMapping("/sendotp")
    public ResponseEntity<String> sendOtp(@RequestParam String phoneNumber) throws Exception {
        User user = userRepo.findByPhoneNumber(phoneNumber);
        if (user == null) return ResponseEntity.badRequest().body("User not found");

        String otp = otpService.generateOtp(phoneNumber);
        smsService.sendSms("+977" + phoneNumber, "Your OTP is: " + otp); // +977 for Nepal
        return ResponseEntity.ok("OTP sent successfully");
    }

    // Step 2: Verify OTP
    @PostMapping("/forget-password/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestParam String phoneNumber, @RequestParam String otp) {
        boolean valid = otpService.validateOtp(phoneNumber, otp);
        if (!valid) return ResponseEntity.badRequest().body("Invalid OTP");
        return ResponseEntity.ok("OTP verified successfully");
    }

    // Step 3: Reset Password
    @PostMapping("/forget-password/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String phoneNumber, @RequestParam String newPassword,@RequestParam String otp) {
        User user = userRepo.findByPhoneNumber(phoneNumber);
        if (user == null) return ResponseEntity.badRequest().body("User not found");
        boolean valid = otpService.validateOtp(phoneNumber, otp);
        if (!valid) {
            return ResponseEntity.badRequest().body("Invalid or expired OTP");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);


        return ResponseEntity.ok("Password updated successfully");
    }
}
