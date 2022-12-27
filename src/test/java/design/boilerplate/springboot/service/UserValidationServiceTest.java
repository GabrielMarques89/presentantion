package design.boilerplate.springboot.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import design.boilerplate.springboot.client.CpfApiInterface;
import design.boilerplate.springboot.exceptions.RegistrationException;
import design.boilerplate.springboot.repository.UserRepository;
import design.boilerplate.springboot.service.validations.UserValidationService;
import design.boilerplate.springboot.utils.ExceptionMessageAccessor;
import feign.FeignException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import util.BaseSicredTest;
import util.DtoHelper;

public class UserValidationServiceTest extends BaseSicredTest {

  @InjectMocks
  private UserValidationService service;

  @Mock
  private UserRepository userRepository;

  @Mock
  private CpfApiInterface cpfApiInterface;

  @Mock
  private ExceptionMessageAccessor exceptionMessageAccessor;

  @BeforeMethod()
  public void resetCount() {
    clearInvocations(userRepository);
  }

  @Test(expectedExceptions = RegistrationException.class)
  public void testValidateUserFailInvalidCpf() {
    var req = DtoHelper.mockUserRegistrationRequest();

    when(userRepository.existsByCpf(any())).thenReturn(false);
    when(userRepository.existsByEmail(any())).thenReturn(false);

    var bodyError = new Gson().toJson(DtoHelper.mockFailCpfResponse());

    var ex = mock(FeignException.class);
    Mockito.when(ex.status()).thenReturn(400);
    when(ex.responseBody()).thenReturn(
        Optional.of(ByteBuffer.wrap(bodyError.getBytes(StandardCharsets.UTF_8))));
    when(cpfApiInterface.getCpfInfo(any())).thenThrow(ex);

    service.validateUser(req);

    verify(userRepository, times(1)).existsByCpf(any());
    verify(userRepository, times(1)).existsByEmail(any());
    verify(cpfApiInterface, times(1)).getCpfInfo(any());
  }

  @Test(expectedExceptions = RegistrationException.class)
  public void testValidateUserFailCpfInUse() {
    var req = DtoHelper.mockUserRegistrationRequest();

    when(userRepository.existsByCpf(any())).thenReturn(true);

    service.validateUser(req);

    verify(userRepository, times(1)).existsByCpf(any());
    verify(userRepository, times(0)).existsByEmail(any());
    verify(cpfApiInterface, times(0)).getCpfInfo(any());
  }

  @Test(expectedExceptions = RegistrationException.class)
  public void testValidateUserFailEmailInUse() {
    var req = DtoHelper.mockUserRegistrationRequest();

    when(userRepository.existsByEmail(any())).thenReturn(true);

    service.validateUser(req);

    verify(userRepository, times(1)).existsByEmail(any());
  }
}