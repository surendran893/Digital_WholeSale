package com.anz.digital.wholesale.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.StreamSupport;

@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

  private final int status;
  private final String message;
  private String detail;
  private List<ValidationError> errors = new ArrayList<>();

  public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
    constraintViolations.forEach(this::addValidationError);
  }

  private void addValidationError(ConstraintViolation<?> cv) {
    this.addValidationError(
        StreamSupport.stream(cv.getPropertyPath().spliterator(), false)
            .reduce((first, second) -> second)
            .orElse(null)
            .toString(),
        cv.getMessage());
  }

  public void addValidationError(String field, String message) {
    errors.add(new ValidationError(field, message));
  }
}
