package com.demoproject.customerservice.service;

import com.demoproject.customerservice.dto.AddressRequest;
import com.demoproject.customerservice.dto.AddressResponse;
import com.demoproject.customerservice.dto.CustomerRequest;
import com.demoproject.customerservice.dto.CustomerResponse;
import com.demoproject.customerservice.entity.Address;
import com.demoproject.customerservice.entity.Customer;
import com.demoproject.customerservice.repository.AddressRepository;
import com.demoproject.customerservice.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;

    public CustomerRequest createCustomer( CustomerRequest customerRequest) {
        Customer customer=new Customer();
        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());
        customer.setDob(customerRequest.getDob());
        customer.setPhoneNo(customerRequest.getPhoneNo());
        List<Address> addresses=customerRequest.getAddress().stream()
                .map(addressRequest -> {
                    Address address=new Address();
                    address.setAddr1(addressRequest.getAddr1());
                    address.setAddr2(addressRequest.getAddr2());
                    address.setCity(addressRequest.getCity());
                    address.setState(addressRequest.getState());
                    address.setCountry(addressRequest.getCountry());
                    address.setCustomer(customer);
                    return  address;
                }).collect(Collectors.toList());
        customer.setAddress(addresses);
        Customer savedCustomer=customerRepository.save(customer);
        return convertToDTO(savedCustomer);

      /*Customer customer = Customer.builder()
                .firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName())
                .dob(customerRequest.getDob())
                .phoneNo(customerRequest.getPhoneNo())
                .address(Address.builder()
                        .addr1(addressRequest.getAddr1())
                        .addr2(addressRequest.getAddr2())
                        .city(addressRequest.getCity())
                        .state(addressRequest.getState())
                        .country(addressRequest.getCountry()).build())
                .build();
        try {
            Customer savedCustomer = customerRepository.save(customer);
            address.setCustomer(savedCustomer);
            addressRepository.save(address);
            log.info("customer {} is created", customer.getId());
            return savedCustomer;
        }
        catch(Exception e)
        {
           e.printStackTrace();
           return null;
        }
*/
            }
            private CustomerRequest convertToDTO(Customer customer)
            {
                CustomerRequest dto=new CustomerRequest();
                dto.setId(customer.getId());
                dto.setFirstName(customer.getFirstName());
                dto.setLastName(customer.getLastName());
                dto.setDob(customer.getDob());
                dto.setPhoneNo(customer.getPhoneNo());
                dto.setAddress(convertAddressToDTO(customer.getAddress()));
                return dto;

            }
private List<AddressRequest> convertAddressToDTO(List<Address> addresses)
{
    return addresses.stream()
            .map(address->{
                AddressRequest dto=new AddressRequest();
                dto.setId(address.getId());
                dto.setAddr1(address.getAddr1());
                dto.setAddr2(address.getAddr2());
                dto.setCity(address.getCity());
                dto.setState(address.getState());
                dto.setCountry(address.getCountry());
                return dto;
            }).collect(Collectors.toList());
}
    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();

        return customers.stream().map(this::mapToCustomerResponse).toList();
    }

    private CustomerResponse mapToCustomerResponse(Customer customer) {
        List<Address> addresses = addressRepository.findAll();
        return CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .phoneNo(customer.getPhoneNo())
                .dob(customer.getDob())
                .address(mapToAddressResponse(customer.getAddress()))
                .build();

    }

    private List<AddressResponse> mapToAddressResponse(List<Address> addresses) {
        return addresses.stream().map(address -> {
            AddressResponse dto = new AddressResponse();
            dto.setId(address.getId());
            dto.setAddr1(address.getAddr1());
            dto.setAddr2(address.getAddr2());
            dto.setCity(address.getCity());
            dto.setState(address.getState());
            dto.setCountry(address.getCountry());
            return dto;
        }).collect(Collectors.toList());
    }
    public CustomerResponse updateCustomer(CustomerResponse customerResponse)
    {
        Optional<Customer> optionalCustomer=customerRepository.findById(customerResponse.getId());
        if(optionalCustomer.isEmpty())
        {

        }
        Customer customer=optionalCustomer.get();
        customer.setFirstName(customerResponse.getFirstName());
        customer.setLastName(customerResponse.getLastName());
        customer.setDob(customerResponse.getDob());
        customer.setPhoneNo(customerResponse.getPhoneNo());
        List<Address> updatedAddresses=new ArrayList<>();
        for(AddressResponse addressResponse:customerResponse.getAddress())
        {
            Address address=null;
            if (addressResponse.getId() != null)
            {
                Optional<Address> optionalAddress = addressRepository.findById(addressResponse.getId());
                if (optionalAddress.isPresent())
                {

                     address=optionalAddress.get();
                }
                else
                {

                }
            }
            else
            {
                address = new Address();
            }
            address.setAddr1(addressResponse.getAddr1());
            address.setAddr2(addressResponse.getAddr2());
            address.setCity(addressResponse.getCity());
            address.setState(addressResponse.getState());
            address.setCountry(addressResponse.getCountry());
            address.setCustomer(customer);
            updatedAddresses.add(address);
        }
        customer.getAddress().clear();
        customer.getAddress().addAll(updatedAddresses);
        Customer savedCustomer=customerRepository.save(customer);
        return covertToUpdateDTO(savedCustomer);
    }
    private CustomerResponse covertToUpdateDTO(Customer customer) {
        CustomerResponse dto = new CustomerResponse();
        dto.setId(customer.getId());
        dto.setLastName(customer.getLastName());
        dto.setFirstName(customer.getFirstName());
        dto.setDob(customer.getDob());
        dto.setPhoneNo(customer.getPhoneNo());
        dto.setAddress(convertAddressesToDTOs(customer.getAddress()));
        return dto;
    }

    private List<AddressResponse> convertAddressesToDTOs(List<Address> addresses) {
        return addresses.stream()
                .map(address -> {
                    AddressResponse dto = new AddressResponse();
                    dto.setId(address.getId());
                    dto.setAddr1(address.getAddr1());
                    dto.setAddr2(address.getAddr2());
                    dto.setState(address.getState());
                    dto.setCity(address.getCity());
                    dto.setCountry(address.getCountry());
                    return dto;
                })
                .collect(Collectors.toList());
    }
    public void deleteCustomerWithAddresses(CustomerRequest customerRequest) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerRequest.getId());
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customerRepository.delete(customer);
        } else {
            throw new EntityNotFoundException("Customer not found with ID: " + customerRequest.getId());
        }
    }
}