package design.boilerplate.springboot.service.implementations;

import design.boilerplate.springboot.model.dto.AuthenticatedUserDto;
import design.boilerplate.springboot.model.dto.VoteCountDto;
import design.boilerplate.springboot.model.dto.VoteRequest;
import design.boilerplate.springboot.model.entities.Session;
import design.boilerplate.springboot.model.enums.VoteResult;
import design.boilerplate.springboot.model.mapper.UserMapper;
import design.boilerplate.springboot.model.mapper.VoteMapper;
import design.boilerplate.springboot.repository.SessionRepository;
import design.boilerplate.springboot.repository.TopicRepository;
import design.boilerplate.springboot.repository.UserRepository;
import design.boilerplate.springboot.repository.VoteRepository;
import design.boilerplate.springboot.service.interfaces.UserService;
import design.boilerplate.springboot.service.interfaces.VoteService;
import design.boilerplate.springboot.service.validations.VoteValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

  private final VoteRepository voteRepository;
  private final UserService userService;
  private final SessionRepository sessionRepository;
  private final TopicRepository topicRepository;
  private final UserRepository userRepository;
  private final VoteValidationService voteValidationService;

  @Override
  public void registerVote(VoteRequest req, AuthenticatedUserDto user) {
    var vote = VoteMapper.INSTANCE.convertToVote(req);
    var modelUser = userRepository.findByUsername(user.getUsername());
    vote.setUser(modelUser);
    vote = voteValidationService.validateVote(vote);

    voteRepository.save(vote);
    log.info("Vote registered");
  }

  @Override
  public VoteCountDto countVotes(Long sessionId) {
    var session = sessionRepository.findById(sessionId).orElseThrow();
    return countVotes(session);
  }

  @Override
  public VoteCountDto countVotes(Session session) {
    log.info("Counting votes");

    var votes = new VoteCountDto();

    var totalVotes = voteRepository.countBySession(session);
    votes.setYesVotes(voteRepository.countByTopicAndVoteResult(session.getTopic(), VoteResult.YES));
    votes.setNoVotes(totalVotes - votes.getYesVotes());
    votes.setCountBySession(totalVotes);
    votes.setPending(userService.countUsers() - totalVotes);
    votes.setTopic(topicRepository.findById(session.getTopic().getId()).orElseThrow().getName());

    return votes;
  }
}
