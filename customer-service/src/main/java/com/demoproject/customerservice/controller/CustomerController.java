package com.demoproject.customerservice.controller;

import com.demoproject.customerservice.dto.CustomerRequest;
import com.demoproject.customerservice.dto.CustomerResponse;
import com.demoproject.customerservice.entity.Address;
import com.demoproject.customerservice.entity.Customer;
import com.demoproject.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
        public class CustomerController {
    private final CustomerService customerService;
   @PostMapping
   @ResponseStatus(HttpStatus.CREATED)

    public ResponseEntity<CustomerRequest> createCustomer(@RequestBody CustomerRequest customerRequest)
    {

        CustomerRequest createdCustomer = customerService.createCustomer(customerRequest);
        return ResponseEntity.ok(createdCustomer);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerResponse> getAllCustomers()
    {
        return customerService.getAllCustomers();
    }
    @PutMapping
    public ResponseEntity<CustomerResponse> updateCustomer(@RequestBody CustomerResponse customerResponse) {
        CustomerResponse updatedCustomer = customerService.updateCustomer(customerResponse);
        return ResponseEntity.ok(updatedCustomer);
    }
    @DeleteMapping

    public ResponseEntity<Void> deleteCustomerWithAddresses(@RequestBody CustomerRequest customerRequest) {
        customerService.deleteCustomerWithAddresses(customerRequest);
        return ResponseEntity.noContent().build();
    }


}
