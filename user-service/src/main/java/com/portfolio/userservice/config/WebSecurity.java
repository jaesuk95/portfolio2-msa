package com.portfolio.userservice.config;

import com.portfolio.userservice.model.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity {

    private final Environment env;
    private final UserService userService;
    private final ObjectPostProcessor<Object> objectPostProcessor;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;



//    private static final String[] PUBLIC_LIST = {
//
//    };

    private static final String[] WHITE_LIST = {
            "/actuator/**",
            "/api/user-service/auth/**",
            "/api/user-service/public/**",
            "/api/user-service/profile/**",
//            "/api/user-service/auth/registration",
    };

    @Bean
    protected SecurityFilterChain config(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.authorizeHttpRequests(authorize -> {
                    try {
                        authorize
                                .requestMatchers(PathRequest.toH2Console()).permitAll()
                                .requestMatchers(WHITE_LIST).permitAll()
//                                .requestMatchers(WHITE_LIST).hasAnyRole("ROLE_USER", "ROLE_ADMIN")
                                .anyRequest().authenticated()
                                .and()
                                .addFilter(getAuthenticationFilter());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
        return http.build();
    }
//                                .requestMatchers(new AntPathRequestMatcher("/api/user-service/**")).permitAll()

//                                .requestMatchers(new IpAddressMatcher("127.0.0.1")).permitAll()     // local
//                                .requestMatchers(new IpAddressMatcher("192.168.0.4")).permitAll()   // through api-gateway
//                                .requestMatchers(new IpAddressMatcher("172.18.0.0/16")).permitAll() // through api-gateway docker container
//                                .requestMatchers(new IpAddressMatcher("10.109.113.170")).permitAll() // through api-gateway loadbalancer (minikube)
//                                .requestMatchers(new IpAddressMatcher("10.100.147.106")).permitAll() // through api-gateway docker container

    public AuthenticationManager authenticationManager(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
        return auth.build();
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationManagerBuilder builder = new AuthenticationManagerBuilder(objectPostProcessor);
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(userService, env); // 생성자를 만들 떄 userService 와 env 를 전달해준다
        authenticationFilter.setAuthenticationManager(authenticationManager(builder)); // 주석을 해도 되는 이유: setAuthenticationManager 따로 호출할 필요가 없다. authenticationFilter 에서 이미 생성했기 때문이다
        return authenticationFilter;
    }



}
