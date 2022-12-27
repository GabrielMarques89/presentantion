package design.boilerplate.springboot.model.dto;

import design.boilerplate.springboot.model.enums.VoteResult;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class VoteCountRequest {
  public VoteCountRequest(Long session, VoteResult voteResult) {
    this.session = session;
  }

  @NotNull(message = "{vote_session_not_empty}")
  @Schema(description = "Session id to identify the session that needs to be analyzed", requiredMode = RequiredMode.REQUIRED)
  private Long session;
}
