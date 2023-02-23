package com.anz.digital.wholesale.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anz.digital.wholesale.entity.Transactions;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions,Long>{
	
	List<Transactions> findByAccountNumber(String accountNuumber);

	Page<Transactions> findByAccountNumber(Pageable pageable, String accountNumber);
}
