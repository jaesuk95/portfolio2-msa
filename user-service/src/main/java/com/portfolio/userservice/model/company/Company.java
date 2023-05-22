package com.portfolio.userservice.model.company;

import com.portfolio.userservice.model.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "company")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyId;
    private LocalDateTime registerDate;
    private String companyName;
    private String brandName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ENTITY_ID")
    private UserEntity userEntity;

    public Company(String companyName, String brandName, UserEntity userEntity) {
        this.companyName = companyName;
        this.brandName = brandName;
        this.userEntity = userEntity;
        this.companyId = UUID.randomUUID().toString();
        this.registerDate = LocalDateTime.now();
    }

}
