package com.jwtAuthentication.Jwt.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jwtAuthentication.Jwt.Model.RequestToken;
import com.jwtAuthentication.Jwt.Model.ResponseToken;
import com.jwtAuthentication.Jwt.Services.CustomUserDetails;
import com.jwtAuthentication.Jwt.Services.JwtUtil;

@RestController
public class JwtController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetails customUserDetails;
    @Autowired
    private JwtUtil jwtUtil;

@PostMapping("/token")
public ResponseEntity<?> generateToken(@RequestBody RequestToken request){
    try{

        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails userDetails=this.customUserDetails.loadUserByUsername(request.getUsername());
       String token=jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok().body(new ResponseToken(token));
    }
    catch(BadCredentialsException e){
        e.printStackTrace();
    }

    return ResponseEntity.badRequest().body("Bad Credentials Entered..");
   
}

@GetMapping("/home")
public String homepage(){
    return "This is a protected page";
}
    
}
