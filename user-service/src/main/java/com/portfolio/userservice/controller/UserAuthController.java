package com.portfolio.userservice.controller;

import com.portfolio.userservice.model.user.UserDto;
import com.portfolio.userservice.model.user.UserService;
import com.portfolio.userservice.request.RequestUser;
import com.portfolio.userservice.response.ResponseUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/user-service/auth")
@RequiredArgsConstructor
public class UserAuthController {

    private final Environment env;
    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<ResponseUser> userRegister(
            @RequestBody RequestUser userPayload,
            HttpServletRequest request) {

        ModelMapper mapper = new ModelMapper();
//        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map(userPayload, UserDto.class);
        UserDto userDtoReturn = userService.createUser(userDto);

        ResponseUser responseUser = mapper.map(userDtoReturn, ResponseUser.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<ResponseUser> refreshToken(@RequestBody RequestUser requestUser) {
        ModelMapper mapper = new ModelMapper();
        UserDto userDto = mapper.map(requestUser, UserDto.class);

        UserDto userDtoReturn = userService.refreshToken(userDto);
        ResponseUser responseUser = mapper.map(userDtoReturn, ResponseUser.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }
}
