package com.anz.digital.wholesale.exception;

import com.anz.digital.wholesale.util.AnzLogger;
import com.anz.digital.wholesale.util.LoggerConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestControllerErrorAdvice extends ResponseEntityExceptionHandler {

  private static final AnzLogger logger = AnzLogger.getLogger(RestControllerErrorAdvice.class);

  /**
   * This method will handle the exceptions.
   *
   * @param ex The exception that was caused.
   * @param request The webrequest which can have headers added to it.
   * @return The ResponseEntity containing the {@link Error} and the correct http status code
   *     associated with the call.
   */
  @ExceptionHandler(value = {BadRequestException.class, NotFoundException.class})
  public ResponseEntity<Object> handleConflict(Exception ex, WebRequest request) {
    if (ex instanceof NotFoundException) {
      ErrorResponse errorResponse =
          new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
      return buildErrorResponse(ex, errorResponse, HttpStatus.NOT_FOUND, request);
    } else {
      ErrorResponse errorResponse =
          new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
      errorResponse.setErrors(((BadRequestException) ex).getErrors());
      return buildErrorResponse(ex, errorResponse, HttpStatus.BAD_REQUEST, request);
    }
  }

  /**
   * Handles javax.validation.ConstraintViolationException. Thrown when @Validated fails.
   *
   * @param ex the ConstraintViolationException
   * @return the ApiError object
   */
  @ExceptionHandler(javax.validation.ConstraintViolationException.class)
  protected ResponseEntity<Object> handleConstraintViolation(
      javax.validation.ConstraintViolationException ex, WebRequest request) {
    ErrorResponse errorResponse =
        new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation error");
    errorResponse.addValidationErrors(ex.getConstraintViolations());
    return buildErrorResponse(ex, errorResponse, HttpStatus.BAD_REQUEST, request);
  }

  /**
   * Handle MissingServletRequestParameterException. Triggered when a 'required' request parameter
   * is missing.
   *
   * @param ex MissingServletRequestParameterException
   * @param headers HttpHeaders
   * @param status HttpStatus
   * @param request WebRequest
   * @return the ApiError object
   */
  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    String error = ex.getParameterName() + " parameter is missing";
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), error);
    return ResponseEntity.badRequest().body(errorResponse);
  }

  /**
   * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
   *
   * @param ex the MethodArgumentNotValidException that is thrown when @Valid validation fails
   * @param headers HttpHeaders
   * @param status HttpStatus
   * @param request WebRequest
   * @return the ApiError object
   */
  @Override
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    ErrorResponse errorResponse =
        new ErrorResponse(
            HttpStatus.UNPROCESSABLE_ENTITY.value(),
            "Validation error. Check 'errors' field for details.");
    for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
      errorResponse.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
    }
    return ResponseEntity.unprocessableEntity().body(errorResponse);
  }

  /**
   * Handle HttpMessageNotReadableException. Happens when request JSON is malformed.
   *
   * @param ex HttpMessageNotReadableException
   * @param headers HttpHeaders
   * @param status HttpStatus
   * @param request WebRequest
   * @return the ApiError object
   */
  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    ServletWebRequest servletWebRequest = (ServletWebRequest) request;
    logger.info(
        LoggerConstants.PrexMarker.FLOW,
        "{} to {}",
        servletWebRequest.getHttpMethod(),
        servletWebRequest.getRequest().getServletPath());
    String error = "Malformed JSON request";
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), error);
    errorResponse.setDetail(ex.getLocalizedMessage());
    return buildErrorResponse(ex, errorResponse, HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(NoDataFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<Object> noDataFoundException(Exception ex, WebRequest request) {

    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    return buildErrorResponse(ex, errorResponse, HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<Object> handleAllUncaughtException(
      Exception exception, WebRequest request) {
    logger.error(
        LoggerConstants.PrexError.ERR,
        "Unknown error occurred: => {}",
        exception.getMessage(),
        exception.getLocalizedMessage());
    ErrorResponse errorResponse =
        new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
    return buildErrorResponse(exception, errorResponse, HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

  private ResponseEntity<Object> buildErrorResponse(
      Exception ex, ErrorResponse errorResponse, HttpStatus httpStatus, WebRequest request) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    return handleExceptionInternal(ex, errorResponse, headers, httpStatus, request);
  }
}
