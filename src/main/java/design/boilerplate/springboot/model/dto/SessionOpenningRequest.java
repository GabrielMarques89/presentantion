package design.boilerplate.springboot.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class SessionOpenningRequest {

  public SessionOpenningRequest(String session, int duration) {
    this.session = Long.parseLong(session);
    this.duration = duration;
  }

  private Long session;

  private int duration;
}