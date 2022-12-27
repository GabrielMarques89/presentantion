package design.boilerplate.springboot.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Login response")
public class LoginResponse {
  @Schema(description = "Token of the logged user - to be fed on authorization to allow logged services to be authorized", requiredMode = RequiredMode.REQUIRED)
  private String token;
}
