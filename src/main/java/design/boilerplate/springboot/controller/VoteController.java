package design.boilerplate.springboot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import design.boilerplate.springboot.jms.model.service.VoteProducer;
import design.boilerplate.springboot.model.dto.AuthenticatedUserDto;
import design.boilerplate.springboot.model.dto.RegistrationResponse;
import design.boilerplate.springboot.model.dto.VoteCountDto;
import design.boilerplate.springboot.model.dto.VoteCountRequest;
import design.boilerplate.springboot.model.dto.VoteRequest;
import design.boilerplate.springboot.service.interfaces.UserService;
import design.boilerplate.springboot.service.interfaces.VoteService;
import io.swagger.v3.oas.annotations.Operation;
import javax.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.RequestBody;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/vote")
public class VoteController {

  public static final String V1_PREFIX = "/v1";
  public static final String COUNT_URL = "/countBySession";
  private final VoteService voteService;
  private final UserService userService;
  private final VoteProducer voteProducer;

  @PostMapping(V1_PREFIX)
  @Operation(summary = "Generates the user authentication token to feed logeed calls with authorizations")
  public ResponseEntity<RegistrationResponse> registerVote(
      @Valid @NotNull(message = "{vote_session_not_empty}") @RequestBody VoteRequest req) {
    voteService.registerVote(req, getLoggedUser());

    var result = voteService.countVotes(req.getSession());
    return ResponseEntity.status(HttpStatus.OK).body(new RegistrationResponse("Voto registrado"));
  }

  @GetMapping(V1_PREFIX + COUNT_URL)
  @Operation(summary = "Returns the number of votes for a given session")
  public ResponseEntity<VoteCountDto> countBySession(@Valid VoteCountRequest request) {
    var result = voteService.countVotes(request.getSession());

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  private AuthenticatedUserDto getLoggedUser() {
    return userService.findByUsername(((User) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal()).getUsername());
  }
}
