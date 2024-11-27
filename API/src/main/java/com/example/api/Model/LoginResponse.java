package com.example.api.Model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginResponse {
    private String Token;
    private int id;
    private String refeshToken;

    public LoginResponse(String token, int id) {
        Token = token;
        this.id = id;
    }

    public LoginResponse(String token, int id, String refeshToken) {
        Token = token;
        this.id = id;
        this.refeshToken = refeshToken;
    }
}
