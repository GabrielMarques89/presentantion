package design.boilerplate.springboot.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class SessionOpenningResponse {

  public SessionOpenningResponse(Long session, String topicName, String message) {
    this.session = session;
    this.topicName = topicName;
    this.message = message;
  }

  private Long session;

  private String topicName;

  private String message;
}