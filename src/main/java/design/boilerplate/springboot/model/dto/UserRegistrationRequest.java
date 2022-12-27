package design.boilerplate.springboot.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Schema(description = "User registration request")
public class UserRegistrationRequest {

  @NotEmpty(message = "{registration_name_not_empty}")
  @Schema(description = "User name", requiredMode = RequiredMode.REQUIRED)
  private String name;

  @Email(message = "{registration_email_is_not_valid}")
  @NotEmpty(message = "{registration_email_not_empty}")
  @Schema(description = "User name", requiredMode = RequiredMode.REQUIRED)
  private String email;

  @NotEmpty(message = "{registration_username_not_empty}")
  @Schema(description = "User Username - used as input on login", requiredMode = RequiredMode.REQUIRED)
  private String username;

  @NotEmpty(message = "{registration_username_not_empty}")
  @Schema(description = "User CPF", requiredMode = RequiredMode.REQUIRED)
  private String cpf;

  @NotEmpty(message = "{registration_password_not_empty}")
  @Schema(description = "User password", requiredMode = RequiredMode.REQUIRED)
  private String password;

}
