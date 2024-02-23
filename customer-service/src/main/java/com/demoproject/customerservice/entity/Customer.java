package com.demoproject.customerservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private Long id;
    @Column(name="First_Name")
    private String firstName;
    @Column(name="Last_Name")
    private String lastName;
    @Column(name="Date_of_Birth")
    private LocalDate dob;
    @Column(name="Phone_No")
    private String phoneNo;
    @OneToMany(cascade =CascadeType.ALL,mappedBy = "customer")
    private List<Address> address=new ArrayList<>();

}
