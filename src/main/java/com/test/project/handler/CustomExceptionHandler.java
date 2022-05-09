package com.test.project.handler;

import com.test.project.dto.Response;
import com.test.project.exception.AbstractBusinessLogicException;
import com.test.project.exception.BusinessLogicException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

  @ResponseBody
  @ExceptionHandler(AbstractBusinessLogicException.class)
  public ResponseEntity<Response<Object>> handleBusinessException(final BusinessLogicException ex) {
    return new ResponseEntity<>(new Response<>(ex.getStatus().value(), ex.getMessage(), null), ex.getStatus());
  }

  @ResponseBody
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Response<Object>> handleException(final Exception ex) {
    return new ResponseEntity<>(new Response<>(400, ex.getMessage(), null), HttpStatus.BAD_REQUEST);
  }


}
