package design.boilerplate.springboot.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static util.DtoHelper.mockSessionRequest;

import design.boilerplate.springboot.exceptions.RegistrationException;
import design.boilerplate.springboot.model.entities.Session;
import design.boilerplate.springboot.repository.SessionRepository;
import design.boilerplate.springboot.service.implementations.SessionServiceImpl;
import design.boilerplate.springboot.service.validations.SessionValidationService;
import design.boilerplate.springboot.utils.GeneralMessageAccessor;
import java.util.Optional;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import util.BaseSicredTest;
import util.ModelHelper;

public class SessionServiceImplTest extends BaseSicredTest {

  @InjectMocks
  private SessionServiceImpl service;
  @Mock
  private SessionRepository sessionRepository;
  @Mock
  private SessionValidationService sessionValidationService;
  @Mock
  private GeneralMessageAccessor generalMessageAccessor;

  @Test
  public void testCreateSessionSuccess() {
    var req = mockSessionRequest();
    service.createSession(req);
  }

  @BeforeMethod()
  public void resetCount() {
    clearInvocations(sessionRepository);
  }

  @Test(expectedExceptions = RegistrationException.class)
  public void testCreateSessionValidationFail() {
    doThrow(RegistrationException.class).when(sessionValidationService).validateSession(any());
    var req = mockSessionRequest();
    service.createSession(req);

    verify(sessionRepository, times(0)).save(any());
  }

  private void mockFindSessionOnRepo(Session model) {
    when(sessionRepository.findById(any())).thenReturn(Optional.ofNullable(model));
  }

  private Session mockValidSession() {
    var session = ModelHelper.mockSession();
    mockFindSessionOnRepo(session);

    return session;
  }
}