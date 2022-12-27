package design.boilerplate.springboot.service.interfaces;

import design.boilerplate.springboot.model.dto.TopicRequest;
import design.boilerplate.springboot.model.dto.TopicResponse;
import design.boilerplate.springboot.model.entities.Topic;


public interface TopicService {

  TopicResponse createTopic(TopicRequest topic);

  TopicResponse createTopicV2(TopicRequest topic);

  TopicResponse getTopic(Long id);
}
