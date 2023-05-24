package com.portfolio.userservice.controller;

import com.portfolio.userservice.model.company.CompanyDto;
import com.portfolio.userservice.model.user.UserDto;
import com.portfolio.userservice.model.user.UserService;
import com.portfolio.userservice.request.RequestCompany;
import com.portfolio.userservice.response.ResponseCompany;
import com.portfolio.userservice.response.ResponseUser;
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
@RequestMapping("/api/user-service/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final Environment env;
    private final UserService userService;

    @GetMapping(value = "/users/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId) {
        UserDto userDto = userService.getUserByUserId(userId);
        ResponseUser returnValue = new ModelMapper().map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
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
