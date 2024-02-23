package com.demoproject.customerservice.repository;

import com.demoproject.customerservice.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
