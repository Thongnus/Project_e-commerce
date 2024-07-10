package com.example.api.Model;

import com.example.api.Entity.Role;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Set;

@Data
@ToString
public class LoginReponse {
    private String Token;

    private Set<Role> roles;
    private int id;
    private Long expireaccesstoken;
    private String refeshToken;
    public LoginReponse(String token, Set<Role> roles,int id,String refeshToken,Long expireaccesstoken) {
        Token = token;

        this.roles = roles;
        this.id = id;
        this.refeshToken=refeshToken;
        this.expireaccesstoken=expireaccesstoken;
    }
}
