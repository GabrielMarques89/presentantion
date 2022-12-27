package design.boilerplate.springboot.controller;

import design.boilerplate.springboot.model.dto.RegistrationResponse;
import design.boilerplate.springboot.model.dto.SessionCreationResponse;
import design.boilerplate.springboot.model.dto.SessionRequest;
import design.boilerplate.springboot.service.interfaces.SessionService;
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
@RequestMapping("/session")
public class SessionController {

  private final SessionService sessionService;

  @PostMapping("/v1")
  @Operation(summary = "Creates a new session")
  public ResponseEntity<RegistrationResponse> createSession(
      @Valid @RequestBody SessionRequest sessionRequest) {
    sessionService.createSession(sessionRequest);

    return ResponseEntity.status(HttpStatus.CREATED).body(null);
  }

  @PostMapping("/v2")
  @Operation(summary = "Creates a new session - v2 version")
  public ResponseEntity<SessionCreationResponse> createSessionV2(
      @Valid @RequestBody SessionRequest sessionRequest) {
    var result = sessionService.createSessionV2(sessionRequest);

    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }
}
