package design.boilerplate.springboot.model.mapper;

import design.boilerplate.springboot.model.dto.VoteRequest;
import design.boilerplate.springboot.model.entities.Session;
import design.boilerplate.springboot.model.entities.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VoteMapper {

  VoteMapper INSTANCE = Mappers.getMapper(VoteMapper.class);

  Vote convertToVote(VoteRequest registrationRequest);

  Session mapSession(Long id);
}