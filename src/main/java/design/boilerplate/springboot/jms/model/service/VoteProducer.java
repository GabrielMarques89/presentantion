package design.boilerplate.springboot.jms.model.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import design.boilerplate.springboot.model.dto.VoteCountDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.jms.core.JmsTemplate;

@Service
public class VoteProducer {

  private final JmsTemplate jmsTemplate;
  private final ObjectMapper mapper;

  @Value("${spring.activemq.queue}")
  private String queueDestination;

  public VoteProducer(JmsTemplate jmsTemplate, ObjectMapper mapper) {
    this.jmsTemplate = jmsTemplate;
    this.mapper = mapper;
  }

  public void send(VoteCountDto message) throws JsonProcessingException {
    jmsTemplate.convertAndSend(queueDestination, mapper.writeValueAsString(message));
  }
}
