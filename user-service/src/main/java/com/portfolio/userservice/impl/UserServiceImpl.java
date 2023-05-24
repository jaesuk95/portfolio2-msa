package com.portfolio.userservice.impl;

import com.portfolio.userservice.feign.OrderServiceClient;
import com.portfolio.userservice.model.company.Company;
import com.portfolio.userservice.model.company.CompanyDto;
import com.portfolio.userservice.model.company.CompanyRepository;
import com.portfolio.userservice.model.user.*;
import com.portfolio.userservice.response.ResponseCompany;
import com.portfolio.userservice.response.ResponseOrder;
import com.portfolio.userservice.response.ResponseUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CircuitBreakerFactory circuitBreakerFactory;
    private final OrderServiceClient orderServiceClient;
    private final CompanyRepository companyRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity;

        if (username.contains("@")) {
            userEntity = userRepository.findByEmail(username).orElse(null);
        } else {
            userEntity = userRepository.findByUsername(username).orElse(null);
        }
        if (userEntity == null) {
            throw new UsernameNotFoundException(username + "의 유저를 찾을 수 없습니다.");
        }
        Collection<? extends GrantedAuthority> authorities = userEntity.getAuthorities();

        return new User(userEntity.getEmail(), userEntity.getPassword(), true, true, true, true,
                authorities);
    }

    @Override
    public UserDto getUserDetailsByEmail(String username) {
        UserEntity userEntity = userRepository.findByEmail(username).orElse(null);

        if (userEntity == null) {
            throw new UsernameNotFoundException(String.format("User not found = %s", username));
        }

        return UserDto.builder()
                .userId(userEntity.getUserId())
                .email(userEntity.getEmail())
                .name(userEntity.getName())
                .role(userEntity.getRole())
                .build();
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        String bCryptPassword = bCryptPasswordEncoder.encode(userDto.getPassword());
        UserEntity userEntity = new UserEntity(
                userDto.getEmail(),
                userDto.getName(),
                bCryptPassword,
                userDto.getUserId(),
                Role.ROLE_USER
        );
//        UserEntity userEntity = mapper.map(userDto, UserEntity.class);

        userRepository.save(userEntity);
        log.info("new user has been saved = {}", userEntity.getId());

        UserDto returnUserDto = mapper.map(userEntity, UserDto.class);
        return returnUserDto;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found");
        }
        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
        log.info("Before call orders microservice");
        CircuitBreaker circuitbreaker = circuitBreakerFactory.create("circuitBreaker");
        List<ResponseOrder> orderList = circuitbreaker.run(() -> orderServiceClient.getOrders(userId),
                throwable -> new ArrayList<>());    // <- throwable -> new ArrayList<>() 이 코드의 뜻은, orderServiceClient.getOrders(id) 에서 오류가 발생하면 비어있는 arrayList[] 으로 반환한다는 뜻이다.
        log.info("After called orders microservice");
        userDto.setOrders(orderList);
        return userDto;
    }

    @Override
    public List<ResponseCompany> validateCompanyOwner(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<Company> companies = userEntity.getCompanies();
        List<ResponseCompany> responseCompanies = new ArrayList<>();
        companies.forEach(v -> {
            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            ResponseCompany responseCompany = mapper.map(v, ResponseCompany.class);
            responseCompanies.add(responseCompany);
        });

//        UserDto userDto = new UserDto();
//        userDto.setCompanies(responseCompanies);
//        userDto.setUserId(userEntity.getUserId());
//        userDto.setName(userEntity.getName());
//
//        ModelMapper mapper = new ModelMapper();
//        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//        return mapper.map(userDto, ResponseUser.class);
        return responseCompanies;
    }

    @Override
    public ResponseCompany registerCompany(CompanyDto companyDto) {
        UserEntity userEntity = userRepository.findByUserId(companyDto.getUserId());
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found");
        }

        Company company = new Company(companyDto.getCompanyName(), companyDto.getBrandName(), userEntity);
        companyRepository.save(company);

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(company, ResponseCompany.class);
    }

    @Override
    public UserDto refreshToken(UserDto userDto) {
//        userDto.
        return userDto;
    }


}


//            ResponseCompany responseCompany = new ResponseCompany();
//            responseCompany.setCompanyId(v.getCompanyId());
//            responseCompany.setRegisterDate(v.getRegisterDate());
//            responseCompany.setCompanyName(v.getCompanyName());
//            responseCompany.setBrandName(v.getBrandName());