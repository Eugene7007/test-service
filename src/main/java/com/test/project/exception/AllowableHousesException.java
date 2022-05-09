package com.test.project.exception;

import org.springframework.http.HttpStatus;

public class AllowableHousesException extends AbstractBusinessLogicException {

  private final String message;

  public AllowableHousesException(String msg) {
    this.message = msg;
  }

  @Override
  public String getMessage() {
    return message;
  }
  @Override
  public HttpStatus getStatus() {
    return null;
  }
}
