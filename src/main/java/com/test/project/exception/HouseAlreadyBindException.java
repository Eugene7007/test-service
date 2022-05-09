package com.test.project.exception;

import org.springframework.http.HttpStatus;

public class HouseAlreadyBindException extends AbstractBusinessLogicException {

  private final String message;

  public HouseAlreadyBindException(String msg) {
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
