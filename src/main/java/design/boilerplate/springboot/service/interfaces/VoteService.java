package design.boilerplate.springboot.service.interfaces;


import design.boilerplate.springboot.model.dto.AuthenticatedUserDto;
import design.boilerplate.springboot.model.dto.VoteCountDto;
import design.boilerplate.springboot.model.dto.VoteRequest;
import design.boilerplate.springboot.model.entities.Session;

public interface VoteService {

  void registerVote(VoteRequest req, AuthenticatedUserDto user);

  VoteCountDto countVotes(Session session);

  VoteCountDto countVotes(Long session);
}
