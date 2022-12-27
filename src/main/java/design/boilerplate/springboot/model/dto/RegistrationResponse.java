package design.boilerplate.springboot.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Default creation model")
public class RegistrationResponse {

  @Schema(description = "Error message shown if something fails")
  private String message;

}
