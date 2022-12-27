package design.boilerplate.springboot.service.validations;

import static java.time.ZoneOffset.UTC;

import design.boilerplate.springboot.exceptions.RegistrationException;
import design.boilerplate.springboot.model.entities.Session;
import design.boilerplate.springboot.model.entities.Topic;
import design.boilerplate.springboot.model.entities.User;
import design.boilerplate.springboot.model.entities.Vote;
import design.boilerplate.springboot.repository.SessionRepository;
import design.boilerplate.springboot.repository.VoteRepository;
import design.boilerplate.springboot.utils.ExceptionMessageAccessor;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class VoteValidationService {

  public static final String VOTE_ALREADY_EXISTS_FOR_TOPIC_AND_USER = "Vote already exists for topic {} and user {}";
  private static final String SESSION_NOT_FOUND = "session_not_found";
  private static final String SESSION_NOT_STARTED = "session_not_open_to_vote";
  private static final String SESSION_ALREADY_ENDED = "session_already_ended";
  private static final String USER_ALREADY_VOTED = "user_already_voted";

  private final VoteRepository voteRepository;
  private final SessionRepository sessionRepository;

  private final ExceptionMessageAccessor exceptionMessageAccessor;

  public Vote validateVote(Vote vote) {
    var session = validateSession(vote.getSession().getId());
    vote.setTopic(session.getTopic());
    checkIfUserAlreadyVoted(session.getTopic(), vote.getUser());
    return vote;
  }

  public Session validateSession(Long id) {
    var sessionResult = sessionRepository.findById(id);
    var session = checkIfExists(id, sessionResult);

    checkIfSessionIsOpen(session);
    return session;
  }

  private void checkIfSessionIsOpen(Session session) {
    var now = LocalDateTime.now().toEpochSecond(UTC);
    checkIfStarted(session, now);
    checkIfEnded(session, now);
  }

  private Session checkIfExists(Long id, Optional<Session> sessionResult) {
    if (sessionResult.isEmpty()) {
      throw new RegistrationException(
          exceptionMessageAccessor.getMessage(SESSION_NOT_FOUND, String.valueOf(id)));
    }
    return sessionResult.get();
  }

  private void checkIfEnded(Session session, long now) {
    if (sessionAlreadyEnded(session, now)) {
      throw new RegistrationException(
          exceptionMessageAccessor.getMessage(SESSION_ALREADY_ENDED));
    }
  }

  private void checkIfStarted(Session session, long now) {
    if (sessionNotStarted(session, now)) {
      throw new RegistrationException(
          exceptionMessageAccessor.getMessage(SESSION_NOT_STARTED));
    }
  }

  private static boolean sessionAlreadyEnded(Session session, long now) {
    var end = session.getEndDateTime().toEpochSecond(UTC);
    return now > end;
  }

  private boolean sessionNotStarted(Session session, long now) {
    if (session.getEndDateTime() == null) {
      return true;
    }

    var begin = session.getBeginDateTime().toEpochSecond(UTC);
    return now < begin;
  }


  private void checkIfUserAlreadyVoted(Topic topic, User user) {
    if (voteRepository.existsByUserAndTopic(user, topic)) {
      var message = String.format(VOTE_ALREADY_EXISTS_FOR_TOPIC_AND_USER, topic.getId(),user.getId());
      log.warn(message);

      throw new RegistrationException(
          exceptionMessageAccessor.getMessage(( USER_ALREADY_VOTED)));
    }
  }
}
