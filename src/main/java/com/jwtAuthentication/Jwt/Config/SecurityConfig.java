package com.jwtAuthentication.Jwt.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jwtAuthentication.Jwt.Filters.JwtAuthenticationFilter;
import com.jwtAuthentication.Jwt.Services.CustomUserDetails;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetails customUserDetails;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    SecurityConfig(CustomUserDetails customUserDetails){
        this.customUserDetails=customUserDetails;
    }
  
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().requestMatchers("/token").permitAll().anyRequest().authenticated().and().addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
    @Autowired
    public AuthenticationManagerBuilder configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(customUserDetails);
        return auth;

    }
    @Bean
    public AuthenticationManager authenticationManager( AuthenticationConfiguration conf)throws Exception{
        return conf.getAuthenticationManager();
    }




}
