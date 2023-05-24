package com.portfolio.userservice.controller;

import com.portfolio.userservice.model.user.UserService;
import io.micrometer.core.annotation.Timed;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/user-service/public")
@RequiredArgsConstructor
public class UserPublicController {

    private final Environment env;
    private final UserService userService;

    @GetMapping("/health_check")
    @Timed(value = "users.status", longTask = true) // prometheus 에 등록
    public String status(HttpServletRequest request) {
        return String.format("It's working in User Service"
                + ", port(local.server.port)=" + env.getProperty("local.server.port")
                + ", port(server.port)=" + env.getProperty("server.port")
                + ", with token secret=" + env.getProperty("token.secret")
                + ", with token time=" + env.getProperty("token.expiration_time"));
    }

    @GetMapping("/check")
    public String check() {
        String order_url = env.getProperty("app.orderurl");
        String user_url = env.getProperty("app.userurl");
        String payment_url = env.getProperty("app.paymenturl");
        return String.format("order url = " + order_url +
                ", user url = " + user_url +
                ", payment url = " + payment_url);
    }
}
