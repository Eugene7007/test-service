package com.test.project.exception;

import org.springframework.http.HttpStatus;

public interface BusinessLogicException {

  String getMessage();

  HttpStatus getStatus();
}
