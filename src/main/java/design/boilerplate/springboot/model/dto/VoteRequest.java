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
@Schema(description = "Vote request")
public class VoteRequest {

  public VoteRequest(Long session, VoteResult voteResult) {
    this.session = session;
    this.voteResult = voteResult;
  }

  @NotNull(message = "{vote_session_not_empty}")
  @Schema(description = "Session id to be voted", requiredMode = RequiredMode.REQUIRED)
  private Long session;

  @NotNull(message = "{vote_result_not_empty}")
  @Schema(description = "Voting choice", requiredMode = RequiredMode.REQUIRED)
  private VoteResult voteResult;
}
