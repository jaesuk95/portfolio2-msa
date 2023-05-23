package com.portfolio.userservice.controller;

import com.portfolio.userservice.model.company.CompanyDto;
import com.portfolio.userservice.model.user.UserDto;
import com.portfolio.userservice.model.user.UserService;
import com.portfolio.userservice.request.RequestCompany;
import com.portfolio.userservice.request.RequestUser;
import com.portfolio.userservice.response.ResponseCompany;
import com.portfolio.userservice.response.ResponseUser;
import io.micrometer.core.annotation.Timed;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user-service")
@RequiredArgsConstructor
public class UserController {

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

    @PostMapping("/auth/registration")
    public ResponseEntity<ResponseUser> userRegister(
            @RequestBody RequestUser userPayload,
            HttpServletRequest request) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map(userPayload, UserDto.class);
        UserDto userDtoReturn = userService.createUser(userDto);

        ResponseUser responseUser = mapper.map(userDtoReturn, ResponseUser.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @GetMapping(value = "/users/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId) {
        UserDto userDto = userService.getUserByUserId(userId);
        ResponseUser returnValue = new ModelMapper().map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
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

    @PostMapping("/register/company")
    public ResponseEntity<ResponseCompany> registerCompany(@RequestBody RequestCompany requestCompany) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        CompanyDto companyDto = mapper.map(requestCompany, CompanyDto.class);
        ResponseCompany responseCompany = userService.registerCompany(companyDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseCompany);
    }

    @GetMapping("/{userId}/company")
    public ResponseEntity<List<ResponseCompany>> companyValidation(@PathVariable String userId) {
        log.info("user-service before acquiring data");
        List<ResponseCompany> responseCompanies = userService.validateCompanyOwner(userId);
        log.info("user-service after acquiring data");
        return ResponseEntity.status(HttpStatus.OK).body(responseCompanies);
    }
}
