package com.test.project.exception;

import org.springframework.http.HttpStatus;

public class BusinessObjectDuplicateException extends AbstractBusinessLogicException {

  private final String message;

  public BusinessObjectDuplicateException(String msg) {
    this.message = msg;
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.BAD_REQUEST;
  }
}
