package com.anz.digital.wholesale.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;

import com.anz.digital.wholesale.entity.Accounts;
import com.anz.digital.wholesale.entity.Transactions;
import com.anz.digital.wholesale.exception.BadRequestException;
import com.anz.digital.wholesale.repository.AccountRepository;
import com.anz.digital.wholesale.repository.TransactionRepository;
import com.anz.digital.wholesale.serviceImpl.AccountDigitalServiceImpl;

@ContextConfiguration
@SpringBootTest(classes = {AccountDigitalServiceImpl.class})
public class AccountDigitalServiceImplTest {

    @MockBean
    private TransactionRepository transactionRepo;
    
    @MockBean
    private AccountRepository accountRepo;
    
    
    @Mock
    Pageable pageMock;
    
    @Mock
    Page<Accounts> accountsMock;
    
    @Mock
    Page<Transactions> transactionMock;
   
    
    @Test
    void fetchWithZeroRecords() {
    	Pageable pageable = PageRequest.of(0, 10);

    	when(accountRepo.findAll(pageable)).thenReturn(accountsMock);
    	
    	Page<Accounts> accountsList = accountRepo.findAll(pageable);
    	
    	
    	Assertions.assertEquals(0, accountsList.getContent().size());
    	
    }
    
    @Test
    void fetchAccountRecordsWithException() {
    	Pageable pageable = PageRequest.of(0, 10);

    	when(accountRepo.findAll(pageable)).thenThrow(new BadRequestException("bad Request"));
    	
    	Assertions.assertThrows(Exception.class, () -> {
    		accountRepo.findAll(pageable);
    	});    	
    }
    
    @Test
    void fetchMoreThanOneAccontsRecords() {
    	Pageable pageable = PageRequest.of(0, 10);

    	List<Accounts> mockAccList = new ArrayList<>();
    	Accounts obj1 = new Accounts();
    	Accounts obj2 = new Accounts();
    	mockAccList.add(obj1);
    	mockAccList.add(obj2);
    	Page<Accounts> mockData = new PageImpl(mockAccList);
    	when(accountRepo.findAll(pageable)).thenReturn(mockData);
    	
    	Page<Accounts> accountsList = accountRepo.findAll(pageable);
    	
    	Assertions.assertEquals(2, accountsList.getContent().size());
    	
    }
    
    
    @Test
    void fetchWithZeroTransactionRecords() {
    	Pageable pageable = PageRequest.of(0, 10);

    	when(transactionRepo.findByAccountNumber(pageable, "111")).thenReturn(transactionMock);
    	
    	Page<Transactions> accountsList = transactionRepo.findByAccountNumber(pageable, "111");
    	
    	
    	Assertions.assertEquals(0, accountsList.getContent().size());
    	
    }
    
    @Test
    void fetchMoreThanOneTransactionRecords() {
    	Pageable pageable = PageRequest.of(0, 10);

    	List<Transactions> mockAccList = new ArrayList<>();
    	Transactions obj1 = new Transactions();
    	Transactions obj2 = new Transactions();
    	mockAccList.add(obj1);
    	mockAccList.add(obj2);
    	Page<Transactions> mockData = new PageImpl(mockAccList);
    	when(transactionRepo.findByAccountNumber(pageable, "111")).thenReturn(mockData);
    	
    	Page<Transactions> accountsList = transactionRepo.findByAccountNumber(pageable, "111");
    	
    	Assertions.assertEquals(2, accountsList.getContent().size());
    	
    }
    
    @Test
    void fetchMRecordsWithException() {
    	Pageable pageable = PageRequest.of(0, 10);

    	when(transactionRepo.findByAccountNumber(pageable, "")).thenThrow(new BadRequestException("bad Request"));
    	
    	Assertions.assertThrows(Exception.class, () -> {
    		transactionRepo.findByAccountNumber(pageable, "");
    	});    	
    }
}
