package io.maddennis.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApiAdvice extends ResponseEntityExceptionHandler {

    //take care of sad paths
}
