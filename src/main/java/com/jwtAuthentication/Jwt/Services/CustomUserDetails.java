package com.jwtAuthentication.Jwt.Services;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetails implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    
        if(username.equals("Niteshsaini")){
            return new User("Niteshsaini","12345678",new ArrayList<>());
        }
        else{
                throw new UsernameNotFoundException("User is not valid");
        }

    }
    
}
