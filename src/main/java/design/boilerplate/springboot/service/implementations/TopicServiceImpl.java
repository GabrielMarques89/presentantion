package design.boilerplate.springboot.service.implementations;

import design.boilerplate.springboot.model.dto.TopicRequest;
import design.boilerplate.springboot.model.dto.TopicResponse;
import design.boilerplate.springboot.model.entities.Topic;
import design.boilerplate.springboot.model.mapper.TopicMapper;
import design.boilerplate.springboot.repository.TopicRepository;
import design.boilerplate.springboot.service.interfaces.TopicService;
import design.boilerplate.springboot.service.validations.TopicValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

  private final TopicRepository topicRepository;
  private final TopicValidationService topicValidationService;

  public TopicResponse createTopicV2(TopicRequest topic) {
    return createTopic(topic);
  }

  @Override
  public TopicResponse createTopic(TopicRequest topicRequest) {
    var topic = TopicMapper.INSTANCE.convertToTopic(topicRequest);
    topicValidationService.validateTopic(topicRequest);
    topicRepository.save(topic);
    log.info("Topic created");
    return TopicMapper.INSTANCE.map(topic);
  }

  public TopicResponse getTopic(Long id) {
    Topic result = topicRepository.findById(id).orElse(null);
    if (result == null) {
      log.warn("Topic with id {} not found", id);
    }

    var convertedResult = TopicMapper.INSTANCE.map(result);

    return convertedResult;
  }
}
