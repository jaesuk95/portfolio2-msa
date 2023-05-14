package com.portfolio.userservice.model.address;

import com.portfolio.userservice.model.user.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private UserEntity userId;
    private String addressName;
    private String addressDetail;
    private String zipcode;
    private String recipientName;
    private String recipientPhone;
    private boolean mainAddress;

}
