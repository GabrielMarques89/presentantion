package design.boilerplate.springboot.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Topic request for creating a new topic", requiredMode = RequiredMode.REQUIRED)
public class TopicRequest {

  public TopicRequest(String name) {
    this.name = name;
  }

  @NotEmpty(message = "{login_username_not_empty}")
  @Schema(description = "Topic name to be used", requiredMode = RequiredMode.REQUIRED)
  private String name;
}