package design.boilerplate.springboot.model.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PendingVotingUsers {

  List<String> users;
  private Long TotalTopicPendingVotes;
}
