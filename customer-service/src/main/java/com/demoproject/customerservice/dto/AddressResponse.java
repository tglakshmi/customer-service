package com.demoproject.customerservice.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {
       private Long id;
       private String addr1;
       private String addr2;
       private String city;
       private String state;
       private String country;
}
