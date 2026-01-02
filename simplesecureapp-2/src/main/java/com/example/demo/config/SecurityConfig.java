package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
	 // In-memory users
	@Bean
	public InMemoryUserDetailsManager userDetails() {

	    UserDetails user = User.withUsername("user")
	            .password("{noop}user123")
	            .roles("USER")     
	            .build();

	    UserDetails admin = User.withUsername("admin")
	            .password("{noop}admin123")
	            .roles("ADMIN")    
	            .build();

	    return new InMemoryUserDetailsManager(user, admin);
	}
    
    // Authorization rules
    @Bean
    public SecurityFilterChain FillChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable()); // disable for simplicity
        
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/api/show").hasRole("USER")
                .requestMatchers("/api/dontshow").hasRole("ADMIN")
                .anyRequest().authenticated();
        });
        
        http.formLogin(Customizer.withDefaults());

        return http.build();
    }

}

