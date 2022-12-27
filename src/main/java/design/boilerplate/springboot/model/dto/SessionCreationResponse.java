package design.boilerplate.springboot.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Session response, returns the created session info")
public class SessionCreationResponse {

  @Schema(description = "Session id of the new session")
  private Long id;
  @Schema(description = "Session begin date")
  private LocalDateTime beginDateTime;
}