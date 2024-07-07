package com.jwtAuthentication.Jwt.Filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jwtAuthentication.Jwt.Services.CustomUserDetails;
import com.jwtAuthentication.Jwt.Services.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomUserDetails customUserDetails;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        String requestToken=request.getHeader("Authorization");
        String username=null;
        String jwtToken=null;
        if(requestToken!=null && requestToken.startsWith("Bearer ")){
            jwtToken=requestToken.substring(7);
            try{
                username=jwtUtil.extractUsername(jwtToken);
                System.out.println(jwtToken);
                if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                   UserDetails userDetails= customUserDetails.loadUserByUsername(username);
                   System.out.println(username);
                   System.out.println(userDetails);
                   UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                   usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                   SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);


                }
                else{
                    System.out.println("Token is not valid");
                }

            }
            catch(Exception e){
                System.out.println("Something went wrong...Nitesh");

                e.printStackTrace();
            }
            
        }

        filterChain.doFilter(request, response);
    }

    
}
