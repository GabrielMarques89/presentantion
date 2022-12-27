package design.boilerplate.springboot.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import design.boilerplate.springboot.exceptions.RegistrationException;
import design.boilerplate.springboot.repository.SessionRepository;
import design.boilerplate.springboot.service.interfaces.TopicService;
import design.boilerplate.springboot.service.validations.SessionValidationService;
import design.boilerplate.springboot.utils.ExceptionMessageAccessor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;
import util.BaseSicredTest;
import util.ModelHelper;

public class SessionValidationServiceTest extends BaseSicredTest {

  @Mock
  private SessionRepository sessionRepository;
  @InjectMocks
  private SessionValidationService sessionValidationService;

  @Mock
  private TopicService topicService;

  @Mock
  private ExceptionMessageAccessor exceptionMessageAccessor;

  @Test(expectedExceptions = RegistrationException.class)
  public void testCreateSessionValidationFail() {
    var session = ModelHelper.mockSession();
    when(sessionRepository.existsByTopic(any())).thenReturn(true);

    sessionValidationService.validateSession(session);
  }

  @Test(expectedExceptions = RegistrationException.class)
  public void testCreateSessionValidationNoTopic() {
    var session = ModelHelper.mockSession();
    when(sessionRepository.existsByTopic(any())).thenReturn(false);
    when(topicService.getTopic(any())).thenReturn(null);

    sessionValidationService.validateSession(session);
  }
}