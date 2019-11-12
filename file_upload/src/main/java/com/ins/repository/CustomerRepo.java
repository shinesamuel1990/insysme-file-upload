package com.ins.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ins.model.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long>{
List<Customer> findByFirstName(String FirstName);
List<Customer> findAll();
}
