package com.demoproject.customerservice.dto;

import com.demoproject.customerservice.entity.Address;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String phoneNo;
    private List<AddressRequest> address;
}
