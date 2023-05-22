package com.portfolio.userservice.model.company;

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

    public Company(String companyName, String brandName) {
        this.companyName = companyName;
        this.brandName = brandName;
        this.companyId = UUID.randomUUID().toString();
        this.registerDate = LocalDateTime.now();
    }

}
