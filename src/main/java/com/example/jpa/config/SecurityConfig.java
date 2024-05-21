package com.example.jpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. 비밀번호 암호화 빈 설정
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. SecurityFilterChain -> 인증 매니저(관리)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // HttpSecurity -> 설정
        // 1. 인증, 권한 설정
        http.authorizeHttpRequests(authorize -> {
            authorize
                    .requestMatchers("/api/**").authenticated()
                    .requestMatchers("/book/**").authenticated()
//            authorize.requestMatchers("/ui/**").permitAll()
//                    .requestMatchers("/admin/**").hasRole("ADMIN")
//                    .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                    .anyRequest().permitAll();
        }).formLogin(form -> {      // 2. 로그인 폼 @Controller
            form.loginPage("/ui/list")      // <form action="/login">
                    .loginProcessingUrl("/login")       // 스프링 시큐리티 동작(username, password)-> DB연동
                    .defaultSuccessUrl("/ui/list", true);
        }).logout(logout -> {       // 3. 로그아웃 폼
            logout.logoutUrl("/logout")     // <form action="/logout" method="post">, <a href="/logout">logout</a>
                    .logoutSuccessUrl("/ui/list")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                    .clearAuthentication(true)      // SecurityContextHolder(세션)
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true);
        });

        return http.build();
    }
}
