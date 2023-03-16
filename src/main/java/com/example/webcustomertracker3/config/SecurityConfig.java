package com.example.webcustomertracker3.config;

import com.example.webcustomertracker3.user.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
public class SecurityConfig {

    @Autowired
    private MyUserDetailsService userDetailsService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;


    protected void authConf(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain configure(HttpSecurity httpSecurity, RememberMeServices rememberMeServices) throws Exception {

        return httpSecurity
                .authorizeHttpRequests()
                .requestMatchers("/user-main/**").hasAnyAuthority("ROLE_STUDENT", "ROLE_INSTRUCTOR")
                .requestMatchers("/user/**").hasAnyAuthority("ROLE_STUDENT", "ROLE_INSTRUCTOR")
                .requestMatchers("/student/get-course/**").hasAnyAuthority("ROLE_STUDENT", "ROLE_INSTRUCTOR")
                .requestMatchers("/instructor/**").hasAnyAuthority("ROLE_STUDENT", "ROLE_INSTRUCTOR")
                .anyRequest()
                .permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/process-login")
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout().logoutUrl("/logout").permitAll()
                .and().rememberMe((remember) -> remember
                        .key("myAppKey")
                        .rememberMeServices(rememberMeServices)).build();
    }

    @Bean
    RememberMeServices rememberMeServices(UserDetailsService userDetailsService){
        TokenBasedRememberMeServices.RememberMeTokenAlgorithm encodingAlgorithm = TokenBasedRememberMeServices.RememberMeTokenAlgorithm.SHA256;
        TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices("myAppKey", userDetailsService,encodingAlgorithm);
        rememberMeServices.setMatchingAlgorithm(TokenBasedRememberMeServices.RememberMeTokenAlgorithm.MD5);
        return rememberMeServices;
    }

}
