package design.boilerplate.springboot.model.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SessionDto {

  public SessionDto() {
  }

  public SessionDto(String beginDateTime) {
    this.beginDateTime = beginDateTime;
  }

  public SessionDto(String topic, String beginDateTime) {
    this.topic = topic;
    this.beginDateTime = beginDateTime;
  }

  private Long id;

  private String topic;
  private String beginDateTime;
}