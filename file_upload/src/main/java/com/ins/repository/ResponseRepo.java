package com.ins.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ins.model.Customer;
import com.ins.model.FileResponseEntity;

@Repository
public interface ResponseRepo extends JpaRepository<FileResponseEntity, Long> {
	List<FileResponseEntity> findAll();
	List<FileResponseEntity> findByUsername(String username);
}
