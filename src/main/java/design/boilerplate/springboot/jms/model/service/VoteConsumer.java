package design.boilerplate.springboot.jms.model.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class VoteConsumer {
  @JmsListener(destination = "${spring.activemq.queue}")
  public void messageListener(String message) {
    //TODO: Só ilustrando o consumo bem sucedido da fila JMS.
    //Poderia ser usado pra levar os dados pra outro sistema, ou ativar algum tipo de notificação
    log.info("---------------------------------------------");
    log.info("Resultado atual da votação, {}", message);
    log.info("Resultado processado pelo consumidor JMS com sucesso");
    log.info("---------------------------------------------");
  }
}