package design.boilerplate.springboot.model.mapper;

import design.boilerplate.springboot.model.dto.TopicRequest;
import design.boilerplate.springboot.model.dto.TopicResponse;
import design.boilerplate.springboot.model.entities.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TopicMapper {

  TopicMapper INSTANCE = Mappers.getMapper(TopicMapper.class);

  Topic convertToTopic(TopicRequest request);

  TopicResponse map(Topic model);
}
