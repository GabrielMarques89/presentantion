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
@Schema(description = "Session id to be voted", requiredMode = RequiredMode.REQUIRED)
public class LoginRequest {

  public LoginRequest(String username, String password) {
    this.username = username;
    this.password = password;
  }

  @NotEmpty(message = "{login_username_not_empty}")
  @Schema(description = "Username of the user to be logged on", requiredMode = RequiredMode.REQUIRED)
  private String username;

  @NotEmpty(message = "{login_password_not_empty}")
  @Schema(description = "Password of the user to be logged on", requiredMode = RequiredMode.REQUIRED)
  private String password;

}
