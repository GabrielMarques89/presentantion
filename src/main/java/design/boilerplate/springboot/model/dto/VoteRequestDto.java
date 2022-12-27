package design.boilerplate.springboot.model.dto;

import design.boilerplate.springboot.model.enums.VoteResult;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VoteRequestDto {

  public VoteRequestDto(String session, VoteResult voteResult) {
    this.session = session;
    this.voteResult = voteResult;
  }

  private String session;
  private VoteResult voteResult;
}
