package com.stage.onlinecourse.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.stage.onlinecourse.security.service.UserDetailServiceImpl;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig {
	private UserDetailServiceImpl userDetailServiceImpl;
	
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //httpSecurity.formLogin(login -> login.loginPage("/login").defaultSuccessUrl("/").permitAll());
        //httpSecurity.rememberMe(withDefaults());
		
		//httpSecurity.authorizeHttpRequests().requestMatchers("/user/**").hasRole("USER");
		//httpSecurity.authorizeHttpRequests().requestMatchers("/admin/**").hasRole("ADMIN");
		httpSecurity.authorizeHttpRequests().anyRequest().authenticated();
		httpSecurity.exceptionHandling(handling -> handling.accessDeniedPage("/notAuthorized"));
        httpSecurity.userDetailsService(userDetailServiceImpl);
		return httpSecurity.build();
	}
}
