package com.anz.digital.wholesale.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.anz.digital.wholesale.exception.BadRequestException;
import com.anz.digital.wholesale.exception.NoDataFoundException;
import com.anz.digital.wholesale.service_.AccountDigitalService;

@ContextConfiguration(classes = {AnzDigitalController.class})
@WebMvcTest
public class AnzDigitalControllerTest {
	
	@Autowired
	private MockMvc mockmvc;
	
	@MockBean
    private AccountDigitalService accountService;
	
	@Test
	void getAccountDetailsTest_200Resp() throws Exception {
		
		Pageable pageable = PageRequest.of(0,10);
		
		when(accountService.getAccountList(pageable, "dummy-trrace")).thenReturn(new ArrayList<>());
		
		MvcResult result = mockmvc.perform(get("/api/accounts").queryParam("page", "0").queryParam("size", "10")).andExpect(status().isOk()).andReturn();
		
		Assertions.assertEquals(200, result.getResponse().getStatus());
	}
	
	
	@Test
	void getAccountDetailsTestNoRecords_400Resp() throws Exception {
		
		Pageable pageable = PageRequest.of(0,10);
		
		when(accountService.getAccountList(pageable, "dummy-trrace")).thenReturn(new ArrayList<>());
		
		MvcResult result = mockmvc.perform(get("/api/accounts")).andExpect(status().is4xxClientError()).andReturn();
		
		Assertions.assertEquals(400, result.getResponse().getStatus());
	}
	
	@Test
	void getTranactionDetailsTest_200Resp() throws Exception {
		
		Pageable pageable = PageRequest.of(0,10);
		
		when(accountService.getTransactionList(pageable, "123-456" , "dummy-trrace")).thenReturn(new ArrayList<>());
		
		MvcResult result = mockmvc.perform(get("/api/transactions/123-456")
				.queryParam("page", "0").queryParam("size", "10"))
				.andExpect(status().isOk()).andReturn();
		
		Assertions.assertEquals(200, result.getResponse().getStatus());
	}
	
	
	@Test
	void getTranactionDetailsTestNoRecords_400Resp() throws Exception {
		
		Pageable pageable = PageRequest.of(0,10);
		
		when(accountService.getTransactionList(pageable, "123-456" ,  "dummy-trrace")).thenReturn(new ArrayList<>());
		
		MvcResult result = mockmvc.perform(get("/api/transactions/123-456")).andExpect(status().is4xxClientError()).andReturn();
		
		Assertions.assertEquals(400, result.getResponse().getStatus());
	}
	
//	@Test
//	void getAccountDetailsTest_Exception() throws Exception {
//		
//		Pageable pageable = PageRequest.of(0,10);
//		
//		when(accountService.getAccountList(pageable, "dummy-trace")).thenReturn(null);
//		
//		Assertions.assertThrows(NoDataFoundException.class, () -> {
//			mockmvc.perform(get("/api/accounts").queryParam("page", "0").queryParam("size", "10"));
//		});
//	}

}
