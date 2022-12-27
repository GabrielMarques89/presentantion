package design.boilerplate.springboot.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Topic response, returns the created topic info")
public class TopicResponse {

  @Schema(description = "Topic Name")
  private String name;
  @Schema(description = "Topic Id")
  private String id;
}