package com.anz.digital.wholesale.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception{

	private static final long serialVersionUID = 1L;
	public NotFoundException(String status,String message){
	    	super(message);
	    	this.status=status;
    }
	public NotFoundException(String message){
    	super(message);
}
	public NotFoundException(){
    	super();
}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	private String status;
}
