package design.boilerplate.springboot.controller;

import design.boilerplate.springboot.model.dto.RegistrationResponse;
import design.boilerplate.springboot.model.dto.UserRegistrationRequest;
import design.boilerplate.springboot.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  @PostMapping("/v1")
  @Operation(summary = "Registers a new user")
  public ResponseEntity<RegistrationResponse> registrationRequest(
      @Valid @RequestBody UserRegistrationRequest registrationRequest) {

    final RegistrationResponse registrationResponse = userService.registration(registrationRequest);

    return ResponseEntity.status(HttpStatus.CREATED).body(registrationResponse);
  }
}
