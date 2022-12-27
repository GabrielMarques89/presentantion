package design.boilerplate.springboot.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static util.DtoHelper.mockTopicRequest;

import design.boilerplate.springboot.repository.TopicRepository;
import design.boilerplate.springboot.service.implementations.TopicServiceImpl;
import design.boilerplate.springboot.service.validations.TopicValidationService;
import design.boilerplate.springboot.utils.ExceptionMessageAccessor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import util.BaseSicredTest;

public class TopicServiceImplTest extends BaseSicredTest {

  @InjectMocks
  private TopicServiceImpl service;

  @Mock
  private TopicRepository repository;
  @Mock
  private TopicValidationService sessionValidationService;

  @Mock
  private ExceptionMessageAccessor exceptionMessageAccessor;

  @BeforeMethod()
  public void resetCount() {
    clearInvocations(repository);
  }

  @Test
  public void testCreateTopicSuccess() {
    var req = mockTopicRequest();
    service.createTopic(req);

    verify(repository, times(1)).save(any());
  }
}