package design.boilerplate.springboot.model.dto;

import design.boilerplate.springboot.model.enums.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class AuthenticatedUserDto {

  private String name;

  private String username;

  private String password;

  private UserRole userRole;

}
