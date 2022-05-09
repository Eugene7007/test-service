package com.test.project.exception;

import org.springframework.http.HttpStatus;

public class BusinessObjectNotFoundException extends AbstractBusinessLogicException {
  private final String message;

  public BusinessObjectNotFoundException(String msg) {
    this.message = msg;
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.NOT_FOUND;
  }
}
