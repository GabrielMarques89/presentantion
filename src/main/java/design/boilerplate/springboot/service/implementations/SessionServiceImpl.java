package design.boilerplate.springboot.service.implementations;

import design.boilerplate.springboot.jms.model.service.VoteProducer;
import design.boilerplate.springboot.model.dto.SessionCreationResponse;
import design.boilerplate.springboot.model.dto.SessionDto;
import design.boilerplate.springboot.model.dto.SessionRequest;
import design.boilerplate.springboot.model.entities.Session;
import design.boilerplate.springboot.model.mapper.SessionMapper;
import design.boilerplate.springboot.repository.SessionRepository;
import design.boilerplate.springboot.service.interfaces.SessionService;
import design.boilerplate.springboot.service.interfaces.VoteService;
import design.boilerplate.springboot.service.validations.SessionValidationService;
import design.boilerplate.springboot.utils.ExceptionMessageAccessor;
import design.boilerplate.springboot.utils.GeneralMessageAccessor;
import java.time.LocalDateTime;
import javax.persistence.Column;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

  public static final String SESSION_OPENED = "session_opened";
  private final SessionRepository sessionRepository;
  private final VoteService voteService;
  private final VoteProducer voteProducer;
  private final SessionValidationService sessionValidationService;
  private final ExceptionMessageAccessor exceptionMessageAccessor;
  private final GeneralMessageAccessor generalMessageAccessor;
  private final String INVALID_SESSION = "invalid_session";
  private final String INVALID_DURATION = "invalid_duration";

  public void endSessions(){
    var sessions = sessionRepository.findAllByEndDateTimeBeforeAndEndedIsFalse(LocalDateTime.now());
    if(sessions.isEmpty()){
      return;
    }

    sessions.forEach(session -> {
      endSession(session);
      sendToQueue(session);
    });
  }

  private void sendToQueue(Session session) {
    var votes = voteService.countVotes(session.getId());
    try{
      voteProducer.send(votes);
      log.info("Voting session \"" + session.getTopic().getName() + "\" sent to queue");
    }catch (Exception e){
      log.error("Error sending votes to queue", e);
    }
  }

  private void endSession(Session session) {
    session.setEnded(true);
    sessionRepository.save(session);
    log.info("Voting session \"" + session.getTopic().getName() + "\" ended");
  }

  public void createSession(SessionRequest request) {
    createSessionV2(request);
  }

  public SessionCreationResponse createSessionV2(SessionRequest sessionRequest) {
    log.info("Creating session");
    var session = SessionMapper.INSTANCE.convert(sessionRequest);
    session.setBeginDateTime(LocalDateTime.now());
    var duration = sessionRequest.getDuration();
    if(duration == null || duration < 0){
      throw new RuntimeException(exceptionMessageAccessor.getMessage((INVALID_DURATION)));
    } else {
      session.setEndDateTime(session.getBeginDateTime().plusMinutes(sessionRequest.getDuration()));
    }

    sessionValidationService.validateSession(session);
    sessionRepository.save(session);

    return SessionMapper.INSTANCE.map(session);
  }
}
