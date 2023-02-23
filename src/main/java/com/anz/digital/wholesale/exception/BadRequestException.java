package com.anz.digital.wholesale.exception;

import java.util.List;

import lombok.Data;

@Data
public class BadRequestException extends RuntimeException {

  private int statusCode;
  private List<ValidationError> errors;

  public BadRequestException(String message) {
    super(message);
  }

  public BadRequestException(String message, int statusCode) {
    super(message);
    this.statusCode = statusCode;
  }

  public BadRequestException(String message, Throwable cause) {
    super(message, cause);
  }

  public BadRequestException(
      String message,
      int statusCode,
      List<ValidationError> detail) {
    super(message);
    this.statusCode = statusCode;
    this.errors = detail;
  }
}
