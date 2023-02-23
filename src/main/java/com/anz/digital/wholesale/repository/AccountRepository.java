package com.anz.digital.wholesale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anz.digital.wholesale.entity.Accounts;

@Repository
public interface AccountRepository extends JpaRepository<Accounts,Long>{
	

}
