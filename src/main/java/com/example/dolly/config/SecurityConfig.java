package com.example.dolly.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 所有請求需驗證，另外列白名單
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers("/public/**").permitAll()
                            .requestMatchers(PathRequest.toH2Console()).permitAll()
                            .anyRequest().authenticated()
                    ;
                })
                .formLogin(Customizer.withDefaults())
                // 以下為h2-console配置 1.允許iframe 2.無須csrf_token
                .headers(headers -> headers.frameOptions(FrameOptionsConfig::sameOrigin))
//                .csrf(csrf -> csrf
//                        .ignoringRequestMatchers(PathRequest.toH2Console())
//                )
                .csrf(AbstractHttpConfigurer::disable)
        ;
        return http.build();
    }

    // TODO:暫時簡化用spring提供的資料結構
    @Bean
    UserDetailsManager userDetailsService(DataSource dataSource) {
        UserDetails user1 = User.builder()
                .username("yanren")
                .password("{noop}yanren")
                .roles("USER")
                .build();
        UserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        if (!userDetailsManager.userExists(user1.getUsername())) {
            userDetailsManager.createUser(user1);
        }
        return userDetailsManager;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
