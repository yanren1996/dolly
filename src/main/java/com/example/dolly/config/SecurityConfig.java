package com.example.dolly.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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

    /**
     * 這個咚咚spring security會拿走，
     * 在UserDetailsService呼叫loadUserByUsername取得回傳值以後用它來比對密碼正確
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
