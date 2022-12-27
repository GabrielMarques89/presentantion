package design.boilerplate.springboot.service.validations;

import design.boilerplate.springboot.exceptions.RegistrationException;
import design.boilerplate.springboot.model.dto.TopicRequest;
import design.boilerplate.springboot.repository.TopicRepository;
import design.boilerplate.springboot.utils.ExceptionMessageAccessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class TopicValidationService {


  private static final String TOPIC_ALREADY_EXISTS = "topic_already_exists";

  private final TopicRepository topicRepo;

  private final ExceptionMessageAccessor exceptionMessageAccessor;

  public void validateTopic(TopicRequest registrationRequest) {
    checkTopicname(registrationRequest.getName());
  }

  private void checkTopicname(String topicName) {
    if (topicRepo.existsByName(topicName)) {

      log.warn("{} is already being used!", topicName);

      final String existsTopicname = exceptionMessageAccessor.getMessage(
          TOPIC_ALREADY_EXISTS);
      throw new RegistrationException(existsTopicname);
    }
  }
}
