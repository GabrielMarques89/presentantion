package design.boilerplate.springboot.routines;

import design.boilerplate.springboot.service.interfaces.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@Slf4j
@EnableScheduling()
public class FinishSession {
  private final SessionService sessionService;

  public FinishSession(SessionService service) {
    sessionService = service;
  }

  @Scheduled(fixedDelay = 60000)
  public void finishEndedSessions() {
    sessionService.endSessions();
  }
}