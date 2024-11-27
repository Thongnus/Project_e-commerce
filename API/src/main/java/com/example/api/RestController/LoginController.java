package com.example.api.RestController;

import com.example.api.Entity.User;
import com.example.api.Model.LoginResponse;
import com.example.api.Model.LoginRequest;
import com.example.api.Service.Serviceimpl.JwtService;
import com.example.api.Service.Serviceimpl.SmsService;
import com.example.api.Service.Serviceimpl.UserServiceimp;
import com.example.api.Service.User_Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
            String jwt = jwtService.generatetoken((User) authentication.getPrincipal());
            String jwtrefesh =jwtService.generaterefeshtoken((User) authentication.getPrincipal());
            return ResponseEntity.ok(new LoginResponse(jwt,u.getId(),jwtrefesh));
        } catch (Exception e) {
            // Xác thực không thành công, trả về mã trạng thái 401 (Unauthorized)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("sai thông tin");
        }
    }
    private LoginResponse refreshAccessToken(String refreshToken) throws Exception {
        if (!jwtService.validateJwtTokenrefesh(refreshToken)) {
            throw new Exception("Refresh token hết hạn");
        }
        String username = jwtService.extractUsernamefromRefeshToken(refreshToken);
        User user = (User) userServiceimp.loadUserByUsername(username);
        return new LoginResponse(
                jwtService.generatetoken(user),
                user.getId()
        );
    }
    @PostMapping("/refreshToken")
    public ResponseEntity<?> accessTokenFromRefreshToken(@RequestParam("refresh") String refreshToken) throws Exception {
            LoginResponse response = refreshAccessToken(refreshToken);
            return ResponseEntity.ok(response);

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
