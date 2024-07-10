package com.example.api.RestController;

import com.example.api.Entity.User;
import com.example.api.Model.LoginReponse;
import com.example.api.Model.LoginRequest;
import com.example.api.Service.Serviceimpl.JwtService;
import com.example.api.Service.Serviceimpl.SmsService;
import com.example.api.Service.Serviceimpl.UserServiceimp;
import com.example.api.Service.Serviceimpl.User_impl;
import com.example.api.Service.User_Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Slf4j
public class LoginController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtService jwtService;
    @Autowired
    User_Service userImpl;
    @Autowired
    SmsService smsService;
    @Autowired
    private UserServiceimp userServiceimp;
    @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest ) {
            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                User u = (User) authentication.getPrincipal();

            System.out.println(u);
            String jwt = jwtService.generationtoken((User) authentication.getPrincipal());
            String jwtrefesh =jwtService.generationrefeshtoken((User) authentication.getPrincipal());
            return ResponseEntity.ok(new LoginReponse(jwt,u.getRoles(),u.getId(),jwtrefesh, jwtService.getExpirationTime()));
        } catch (Exception e) {
            // Xác thực không thành công, trả về mã trạng thái 401 (Unauthorized)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("sai thông tin");
        }
    }
    @PostMapping("/refreshToken")
    public ResponseEntity<?> accessTokenFromRefreshToken(@RequestParam("refesh") String refreshToken) {
        try {
            if(jwtService.isTokenExpiredrefesh(refreshToken)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Refresh token hết hạn");
            }

            String username = jwtService.extractUsernamefromRefeshToke(refreshToken);
            User user = (User) userServiceimp.loadUserByUsername(username);

            return ResponseEntity.ok(
                    new LoginReponse(
                            jwtService.generationtoken(user),
                            user.getRoles(),
                            user.getId(),
                            jwtService.generationrefeshtoken(user),
                            jwtService.getExpirationTime()
                    )
            );
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không hợp lệ");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        return ResponseEntity.ok("logout thành công");
    }
    //doi mat khau
    @PutMapping("/changepass/{id}")
    public ResponseEntity<?> changepass(@RequestParam("otp") String otp,@PathVariable("id") int id, @RequestBody User user){
        User check = userImpl.findbyId(id);
        if(check==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Khong tim thay user voi id"+id);

        }
        // Xác minh mã OTP
        boolean otpVerified = smsService.CkeckingOTp(otp);
        if (!otpVerified) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mã OTP không hợp lệ hoặc đã hết hạn");
        }
        check.setPassword(user.getPassword());
        return ResponseEntity.ok(userImpl.update(check));


    }


}
