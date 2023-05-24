package com.portfolio.userservice.model.user;

import com.portfolio.userservice.model.company.CompanyDto;
import com.portfolio.userservice.response.ResponseCompany;
import com.portfolio.userservice.response.ResponseUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDto getUserDetailsByEmail(String username);

    UserDto createUser(UserDto userDto);

    UserDto getUserByUserId(String userId);

    List<ResponseCompany> validateCompanyOwner(String userId);

    ResponseCompany registerCompany(CompanyDto companyDto);

    UserDto refreshToken(UserDto userDto);
}
