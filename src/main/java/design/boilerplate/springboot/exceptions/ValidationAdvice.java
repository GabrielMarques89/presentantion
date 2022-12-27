package design.boilerplate.springboot.exceptions;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class ValidationAdvice {

  @ExceptionHandler({HttpMessageNotReadableException.class})
  public final ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(
      HttpMessageNotReadableException exception) {

    log.error("Missing request on call", exception);

    //TODO: Tratar melhor, internacionalizar o erro a partir de anotação no requestBody
    var error = "Missing request on call";

    final ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse(
        HttpStatus.BAD_REQUEST, LocalDateTime.now(), List.of(error));

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrorResponse);
  }

  @ExceptionHandler({MethodArgumentNotValidException.class})
  public final ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException exception) {

    final List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
    final List<String> errorList = fieldErrors.stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());

    final ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse(
        HttpStatus.BAD_REQUEST, LocalDateTime.now(), errorList);

    log.warn("Validation errors : {} , Parameters : {}", errorList,
        exception.getBindingResult().getTarget());

    return ResponseEntity.status(validationErrorResponse.getStatus()).body(validationErrorResponse);
  }

}
