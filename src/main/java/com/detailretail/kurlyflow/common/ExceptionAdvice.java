package com.detailretail.kurlyflow.common;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {
//
//  @ExceptionHandler(NotFoundException.class)
//  public ResponseEntity<Void> handleNotFoundException(NotFoundException e) {
//    return ResponseEntity.notFound().build();
//  }
//
//  @ExceptionHandler(UnAuthorizedException.class)
//  public ResponseEntity<ErrorResponse> handleUnAuthorizedException(UnAuthorizedException e) {
//    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(e.getMessage()));
//  }
//
//  @ExceptionHandler(BadRequestException.class)
//  public ResponseEntity<ErrorResponse> badRequestException(BadRequestException e) {
//    return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
//  }
//
//  @ExceptionHandler(ConflictException.class)
//  public ResponseEntity<ErrorResponse> handleConflictException(ConflictException e) {
//    return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(e.getMessage()));
//  }
//
//  @ExceptionHandler(FirebaseServerErrorException.class)
//  public ResponseEntity<ErrorResponse> handleFirebaseServerException(
//      FirebaseServerErrorException e) {
//    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//        .body(new ErrorResponse(e.getMessage()));
//  }
}
