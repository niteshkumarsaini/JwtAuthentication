package com.jwtAuthentication.Jwt.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestToken {
    private String username;
    private String password;
    
}
