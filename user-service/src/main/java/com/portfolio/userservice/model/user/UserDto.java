package com.portfolio.userservice.model.user;

import com.portfolio.userservice.controller.response.ResponseOrder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserDto {
    private String email;
    private String password;
    private String name;
    private String userId;
    private Date createdAt;

    private List<ResponseOrder> orders;

}
