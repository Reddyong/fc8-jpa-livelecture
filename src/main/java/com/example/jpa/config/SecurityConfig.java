package com.example.jpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
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

//        // HttpSecurity -> 설정
//        // 1. 인증, 권한 설정
//        http.authorizeHttpRequests(authorize -> {
//            authorize
//                    .requestMatchers("/api/**").authenticated()
//                    .requestMatchers("/book/**").authenticated()
////            authorize.requestMatchers("/ui/**").permitAll()
////                    .requestMatchers("/admin/**").hasRole("ADMIN")
////                    .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
//                    .anyRequest().permitAll();
//        }).formLogin(form -> {      // 2. 로그인 폼 @Controller
//            form.loginPage("/ui/list")      // <form action="/login">
//                    .loginProcessingUrl("/login")       // 스프링 시큐리티 동작(username, password)-> DB연동
//                    .defaultSuccessUrl("/ui/list", true);
//        }).logout(logout -> {       // 3. 로그아웃 폼
//            logout.logoutUrl("/logout")     // <form action="/logout" method="post">, <a href="/logout">logout</a>
//                    .logoutSuccessUrl("/ui/list")
//                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
//                    .clearAuthentication(true)      // SecurityContextHolder(세션)
//                    .deleteCookies("JSESSIONID")
//                    .invalidateHttpSession(true);
//        });
//
//        return http.build();

        http.formLogin(form -> form.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(hb -> hb.disable())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                                .requestMatchers("/api/v1/user/**").hasAnyRole("USER", "MANAGER", "ADMIN")
                                .requestMatchers("/api/v1/manager/**").hasAnyRole("MANAGER", "ADMIN")
                                .requestMatchers("/api/v1/admin/**").hasAnyRole("ADMIN")
                )
                .apply(new MyCustomDsl());

        return http.build();
    }

    // 인증에 필요한 필터를 동작시키기 위해 사용자 정의 클래스를 등록(DSL)
    // login -> UsernamePasswordAuthenticFilter
    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            // AuthenticationManager 얻어오기
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

            // UsernamePasswordAuthenticFilter 실행 할 수 있음

        }
    }
}
