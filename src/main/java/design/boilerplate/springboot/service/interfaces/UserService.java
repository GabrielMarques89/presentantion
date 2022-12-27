package design.boilerplate.springboot.service.interfaces;

import design.boilerplate.springboot.model.dto.AuthenticatedUserDto;
import design.boilerplate.springboot.model.dto.RegistrationResponse;
import design.boilerplate.springboot.model.dto.UserRegistrationRequest;


public interface UserService {

  AuthenticatedUserDto findByUsername(String username);

  RegistrationResponse registration(UserRegistrationRequest registrationRequest);
  Long countUsers();
}
