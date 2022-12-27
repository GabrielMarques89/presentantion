package design.boilerplate.springboot.model.dto;

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
@Schema(description = "Session request for creating a new session")
public class SessionRequest {

  public SessionRequest(Long topic, Long duration) {
    this.topic = topic;
    this.duration = duration;
  }

  @NotNull(message = "{topic_not_empty}")
  @Schema(description = "Topic id to associate with the session", requiredMode = RequiredMode.REQUIRED)
  private Long topic;

  @Schema(description = "Duration of the session in minutes. If nothing is passed, the default duration will assume 1 minute", requiredMode = RequiredMode.NOT_REQUIRED)
  private Long duration;
}