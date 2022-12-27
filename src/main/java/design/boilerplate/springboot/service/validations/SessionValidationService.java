package design.boilerplate.springboot.service.validations;

import design.boilerplate.springboot.exceptions.RegistrationException;
import design.boilerplate.springboot.model.entities.Session;
import design.boilerplate.springboot.repository.SessionRepository;
import design.boilerplate.springboot.service.interfaces.TopicService;
import design.boilerplate.springboot.utils.ExceptionMessageAccessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class SessionValidationService {

  private final SessionRepository sessionRepo;
  private final TopicService topicService;

  private final ExceptionMessageAccessor exceptionMessageAccessor;
  private final String INVALID_TOPIC = "invalid_topic";
  private final String TOPIC_ALREADY_IN_USE = "topic_already_exists";

  public void validateSession(Session session) {
    if (sessionRepo.existsByTopic(session.getTopic())) {
      log.warn("Topic already exists!");
      final String invalidTopic = exceptionMessageAccessor.getMessage(TOPIC_ALREADY_IN_USE);
      throw new RegistrationException(invalidTopic);
    }

    var topic = topicService.getTopic(session.getTopic().getId());
    if (topic == null) {

      final String fail_because_of_topic_id = exceptionMessageAccessor.getMessage(
          INVALID_TOPIC, session.getTopic().getId());
      log.warn("Topic {} not found", session.getTopic().getId());
      throw new RegistrationException(fail_because_of_topic_id);
    }
  }
}
