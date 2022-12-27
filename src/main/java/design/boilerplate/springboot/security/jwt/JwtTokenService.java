package design.boilerplate.springboot.security.jwt;

import design.boilerplate.springboot.model.dto.AuthenticatedUserDto;
import design.boilerplate.springboot.model.dto.LoginRequest;
import design.boilerplate.springboot.model.dto.LoginResponse;
import design.boilerplate.springboot.model.entities.User;
import design.boilerplate.springboot.model.mapper.UserMapper;
import design.boilerplate.springboot.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenService {

  private final UserService userService;

  private final JwtTokenManager jwtTokenManager;

  private final AuthenticationManager authenticationManager;

  public LoginResponse getLoginResponse(LoginRequest loginRequest) {

    final String username = loginRequest.getUsername();
    final String password = loginRequest.getPassword();

    final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
        username, password);

    authenticationManager.authenticate(usernamePasswordAuthenticationToken);

    final AuthenticatedUserDto authenticatedUserDto = userService.findByUsername(username);

    final User user = UserMapper.INSTANCE.map(authenticatedUserDto);
    final String token = jwtTokenManager.generateToken(user);

    log.info("{} has successfully logged in!", user.getUsername());

    return new LoginResponse(token);
  }

}
