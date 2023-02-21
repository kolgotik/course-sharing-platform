package com.example.webcustomertracker3.config;

import com.example.webcustomertracker3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

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
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity.authorizeHttpRequests()
                .requestMatchers("/student/get-course/**")
                .hasRole("STUDENT")
                .anyRequest()
                .permitAll()
                .and()
                .formLogin()
                .loginPage("/course-login")
                .loginProcessingUrl("/auth")
                .defaultSuccessUrl("/user-main")
                .permitAll()
                .and()
                .logout().logoutSuccessUrl("/").permitAll().and().build();

        /*return httpSecurity.authorizeHttpRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/auth")
                .permitAll()
                .and()
                .logout().permitAll()
                .and().build();*/
    }

}
