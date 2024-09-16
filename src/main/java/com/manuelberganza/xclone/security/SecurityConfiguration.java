package com.manuelberganza.xclone.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests( (authz) -> {
            authz.requestMatchers("/register").permitAll()
            .requestMatchers("/main.css", "/x-logo_dark.svg", "/photos/**", "/logos/**").permitAll().anyRequest().authenticated();
        })
        .formLogin(form -> form.loginPage("/login").permitAll())
        .build();
    }

    @Bean
    public UserDetailsManager usersCustom(DataSource datasource) {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(datasource);

        users.setUsersByUsernameQuery("select username, password, enabled from users u where username=?");
        users.setAuthoritiesByUsernameQuery("select u.username,r.name from users_roles ur " + 
        "inner join users u on u.id = ur.user_id " + 
        "inner join roles r on r.id = ur.role_id " + 
        "where u.username=?");

        return users;
    }

}
