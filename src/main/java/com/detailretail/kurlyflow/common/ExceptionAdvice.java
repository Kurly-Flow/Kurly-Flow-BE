package com.detailretail.kurlyflow.common;

import com.detailretail.kurlyflow.common.exception.BadRequestException;
import com.detailretail.kurlyflow.common.exception.ConflictException;
import com.detailretail.kurlyflow.common.exception.NotFoundException;
import com.detailretail.kurlyflow.common.exception.UnAuthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Void> handleNotFoundException(NotFoundException e) {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(UnAuthorizedException.class)
  public ResponseEntity<ErrorResponse> handleUnAuthorizedException(UnAuthorizedException e) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(e.getMessage()));
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResponse> badRequestException(BadRequestException e) {
    return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
  }

  @ExceptionHandler(ConflictException.class)
  public ResponseEntity<ErrorResponse> handleConflictException(ConflictException e) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(e.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleRuntimeException(Exception e) {
    return ResponseEntity.internalServerError().body(new ErrorResponse(e.getMessage()));
  }
}
