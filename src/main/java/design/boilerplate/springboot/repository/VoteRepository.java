package design.boilerplate.springboot.repository;

import design.boilerplate.springboot.model.entities.Session;
import design.boilerplate.springboot.model.entities.Topic;
import design.boilerplate.springboot.model.entities.User;
import design.boilerplate.springboot.model.entities.Vote;
import design.boilerplate.springboot.model.enums.VoteResult;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VoteRepository extends JpaRepository<Vote, Long> {

  boolean existsByUserAndTopic(User user, Topic topic);

  Long countByTopic(Topic topic);

  Long countBySession(Session topic);

  Long countByTopicAndVoteResult(Topic topic, VoteResult voteResult);
}
