package design.boilerplate.springboot.service.implementations;

import design.boilerplate.springboot.model.dto.AuthenticatedUserDto;
import design.boilerplate.springboot.model.dto.RegistrationResponse;
import design.boilerplate.springboot.model.dto.UserRegistrationRequest;
import design.boilerplate.springboot.model.entities.User;
import design.boilerplate.springboot.model.enums.UserRole;
import design.boilerplate.springboot.model.mapper.UserMapper;
import design.boilerplate.springboot.repository.UserRepository;
import design.boilerplate.springboot.service.interfaces.UserService;
import design.boilerplate.springboot.service.validations.UserValidationService;
import design.boilerplate.springboot.utils.GeneralMessageAccessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private static final String REGISTRATION_SUCCESSFUL = "registration_successful";

  private final UserRepository userRepository;

  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  private final UserValidationService userValidationService;

  private final GeneralMessageAccessor generalMessageAccessor;

  @Override
  public AuthenticatedUserDto findByUsername(String username) {
    return UserMapper.INSTANCE.convertToAuthenticatedUserDto(userRepository.findByUsername(username));
  }

  @Override
  public Long countUsers() {
    return userRepository.count();
  }

  public RegistrationResponse registration(UserRegistrationRequest registrationRequest) {

    userValidationService.validateUser(registrationRequest);

    var user = UserMapper.INSTANCE.map(registrationRequest);
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    user.setUserRole(UserRole.USER);
    userRepository.save(user);

    final String username = registrationRequest.getUsername();
    final String registrationSuccessMessage = generalMessageAccessor.getMessage(REGISTRATION_SUCCESSFUL, username);

    log.info("{} registered successfully!", username);

    return new RegistrationResponse(registrationSuccessMessage);
  }
}
