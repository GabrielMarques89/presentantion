package design.boilerplate.springboot.controller;

import design.boilerplate.springboot.model.dto.LoginRequest;
import design.boilerplate.springboot.model.dto.LoginResponse;
import design.boilerplate.springboot.security.jwt.JwtTokenService;
import io.swagger.v3.oas.annotations.Operation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

  private final JwtTokenService jwtTokenService;

  @PostMapping("/v1")
  @Operation(summary = "Makes a login attempt")
  public ResponseEntity<LoginResponse> loginRequest(@Valid @RequestBody LoginRequest loginRequest) {

    final LoginResponse loginResponse = jwtTokenService.getLoginResponse(loginRequest);

    return ResponseEntity.ok(loginResponse);
  }

}
