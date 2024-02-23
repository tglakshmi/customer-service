package com.demoproject.customerservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="Addr1")
    private String addr1;
    @Column(name="Addr2")
    private String addr2;
    @Column(name="City")
    private String city;
    @Column(name="State")
    private String state;
    @Column(name="Country")
    private String country;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="customer_id")
    private Customer customer;
}
