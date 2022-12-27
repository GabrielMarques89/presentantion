package design.boilerplate.springboot.exceptions;

import design.boilerplate.springboot.exceptions.response.ApiExceptionResponse;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class RegistrationControllerAdvice {

  @ExceptionHandler(RegistrationException.class)
  ResponseEntity<ApiExceptionResponse> handleRegistrationException(
      RegistrationException exception) {

    final ApiExceptionResponse response = new ApiExceptionResponse(exception.getErrorMessage(),
        HttpStatus.UNPROCESSABLE_ENTITY, LocalDateTime.now());

    return ResponseEntity.status(response.getStatus()).body(response);
  }

}
