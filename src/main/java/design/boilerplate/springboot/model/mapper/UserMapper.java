package design.boilerplate.springboot.model.mapper;

import design.boilerplate.springboot.model.dto.AuthenticatedUserDto;
import design.boilerplate.springboot.model.dto.UserRegistrationRequest;
import design.boilerplate.springboot.model.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  User map(UserRegistrationRequest registrationRequest);

  AuthenticatedUserDto convertToAuthenticatedUserDto(User user);

  User map(AuthenticatedUserDto authenticatedUserDto);

}
