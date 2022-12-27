package design.boilerplate.springboot.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class SessionOpenningDto {

  public SessionOpenningDto(String session, int duration) {
    this.session = session;
    this.duration = duration;
  }

  private String session;

  private int duration;
}