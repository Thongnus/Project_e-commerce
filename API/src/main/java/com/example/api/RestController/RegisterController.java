package com.example.api.RestController;

import com.example.api.Entity.User;
import com.example.api.Model.changepassword;
import com.example.api.Service.Serviceimpl.SmsService;
import com.example.api.Service.Serviceimpl.User_impl;
import com.example.api.Service.User_Service;
import com.twilio.rest.verify.v2.service.Verification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class RegisterController {
    @Autowired
    SmsService smsService;
    @Autowired
    User_Service userImpl;
    String phone;
    @PostMapping("/api/sendOtp")
    public String send(@RequestBody String sdt){
        smsService.sendSMS(sdt);
        phone=sdt;
        System.out.println(sdt);
        return "Đã gửi phone:"+sdt;


    }
    @PostMapping("/api/forgetpass")
    public String sendotp(@AuthenticationPrincipal User u){

        smsService.sendSMS(u.getNumberphone());
        return "Đã gửi mã tới:"+u.getNumberphone();


    }
    @PostMapping("api/submitpass")
    public ResponseEntity<?> oke(@RequestParam("otp") String otp,@AuthenticationPrincipal User user, @RequestBody changepassword changepassword){

        User existingUser = userImpl.findbyId(user.getId());

        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Người dùng không tồn tại");
        }

        // Xác minh mã OTP
        boolean otpVerified = smsService.CkeckingOTp(otp);
        if (!otpVerified) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sai OTP");
        }
        existingUser.setPassword(changepassword.getNewPassword());
        userImpl.updatepassword(existingUser);
        return ResponseEntity.ok("thành công");
    }


    @PostMapping("/register/verify")
    public ResponseEntity<?> verifyOTP(@RequestParam("otp") String otp,@RequestBody User user) {
        User isxistUser = userImpl.findbyUsername(user.getUsername());
        if(isxistUser!=null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("đã tồn tại Username");
        }
        // Xác minh mã OTP
        boolean otpVerified = smsService.CkeckingOTp(otp);
        if (otpVerified) {
            log.info(String.valueOf(user));
            user.setNumberphone(phone);
            user.setVerify_phone(true);
            // Mã OTP hợp lệ, đăng kí
            User savedUser = userImpl.saveTT(user);

            return ResponseEntity.ok().body(savedUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mã OTP không hợp lệ hoặc đã hết hạn");
        }
    }
//    @PostMapping("/register")
//    public ResponseEntity<?> verifyOTP(@RequestBody User user) {
//        User isxistUser = userImpl.findbyUsername(user.getUsername());
//        if(isxistUser!=null){
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("đã tồn tại Username");
//        }
//        // Xác minh mã OTP
//            log.info(String.valueOf(user));
//            user.setNumberphone(phone);
//            user.setVerify_phone(true);
//            // Mã OTP hợp lệ, đăng kí
//            User savedUser = userImpl.save(user);
//
//            return ResponseEntity.ok().body(savedUser);
//
//    }
}
