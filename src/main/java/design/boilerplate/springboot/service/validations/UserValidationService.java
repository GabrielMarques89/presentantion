package design.boilerplate.springboot.service.validations;

import com.google.gson.Gson;
import design.boilerplate.springboot.client.CpfApiInterface;
import design.boilerplate.springboot.exceptions.RegistrationException;
import design.boilerplate.springboot.model.dto.CpfResponse;
import design.boilerplate.springboot.model.dto.UserRegistrationRequest;
import design.boilerplate.springboot.repository.UserRepository;
import design.boilerplate.springboot.utils.ExceptionMessageAccessor;
import feign.Feign;
import feign.FeignException;
import feign.gson.GsonDecoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserValidationService {

  private static final String EMAIL_ALREADY_EXISTS = "email_already_exists";
  private static final String CPF_ALREADY_EXISTS = "cpf_already_exists";
  private static final String INVALID_CPF = "invalid_cpf";

  private static final String USERNAME_ALREADY_EXISTS = "username_already_exists";
  public static final String INVALID_CPF_CODE = "100";

  private final UserRepository userRepository;

  private final CpfApiInterface cpfApiInterface;

  private final ExceptionMessageAccessor exceptionMessageAccessor;

  public void validateUser(UserRegistrationRequest registrationRequest) {
    checkEmail(registrationRequest.getEmail());
    checkCpf(registrationRequest.getCpf());
    checkUsername(registrationRequest.getUsername());
  }

  private void checkUsername(String username) {

    final boolean existsByUsername = userRepository.existsByUsername(username);

    if (existsByUsername) {
      log.warn("{} is already being used!", username);

      final String existsUsername = exceptionMessageAccessor.getMessage((
          USERNAME_ALREADY_EXISTS));
      throw new RegistrationException(existsUsername);
    }
  }

  private void checkCpf(String cpf) {
    final boolean existsByCpf = userRepository.existsByCpf(cpf);

    if (existsByCpf) {
      log.warn("{} is already being used!", cpf);

      final String existsEmail = exceptionMessageAccessor.getMessage(( CPF_ALREADY_EXISTS));
      throw new RegistrationException(existsEmail);
    }

    validateCpfIntegration(cpf);
  }

  private void validateCpfIntegration(String cpf) {
    try {
      initClient();
      var result = cpfApiInterface.getCpfInfo(cpf);
      if (!result.isValidCpf()) {
        log.warn("Cpf {} is invalid!", cpf);

        final String invalidCpf = exceptionMessageAccessor.getMessage(INVALID_CPF, cpf);
        throw new RegistrationException(invalidCpf);
      }
    } catch (FeignException e) {
      log.error("Cpf {} is invalid!", e);
      if (e.responseBody().isPresent() && e.status() == 400) {
        try {
          readErrorResponse(cpf, e);
        } catch (RegistrationException ex) {
          throw ex;
        } catch (Exception ex) {
          //Tratamento genérico de exceção - por não ter tempo de analisar a documentação da API
          //e entender os possíveis erros e modelos de dados retornados
          log.error("Unexpected error accessing CPF API", ex);
          throw ex;
        }
      }

      final String invalidCpf = exceptionMessageAccessor.getMessage(INVALID_CPF, cpf);
      throw new RegistrationException(invalidCpf);
    }
  }

  private void initClient() {
    if(cpfApiInterface == null){
      Feign.builder().decoder(new GsonDecoder())
          .target(CpfApiInterface.class,
              "https://api.cpfcnpj.com.br/5ae973d7a997af13f0aaf2bf60e65803/1/");
    }
  }

  private void readErrorResponse(String cpf, FeignException e) {
    var errorResult = new Gson().fromJson(
        new String(e.responseBody().get().array(), StandardCharsets.UTF_8),
        CpfResponse.class);
    if (UserValidationService.INVALID_CPF_CODE.equals(errorResult.getErroCodigo())) {
      String invalidCpf = exceptionMessageAccessor.getMessage(INVALID_CPF, cpf);
      throw new RegistrationException(invalidCpf);
    }
  }

  private void checkEmail(String email) {

    final boolean existsByEmail = userRepository.existsByEmail(email);

    if (existsByEmail) {

      log.warn("{} is already being used!", email);

      final String existsEmail = exceptionMessageAccessor.getMessage(EMAIL_ALREADY_EXISTS);
      throw new RegistrationException(existsEmail);
    }
  }

}
