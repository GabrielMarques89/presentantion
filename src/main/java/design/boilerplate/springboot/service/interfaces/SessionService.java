package design.boilerplate.springboot.service.interfaces;


import design.boilerplate.springboot.model.dto.SessionCreationResponse;
import design.boilerplate.springboot.model.dto.SessionDto;
import design.boilerplate.springboot.model.dto.SessionRequest;
import design.boilerplate.springboot.model.entities.Session;

public interface SessionService {

  void createSession(SessionRequest topic);

  SessionCreationResponse createSessionV2(SessionRequest topic);

  void endSessions();
}
