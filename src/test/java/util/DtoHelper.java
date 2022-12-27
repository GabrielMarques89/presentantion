package util;

import static util.MockValuesConstants.DEFAULT_CPF;
import static util.MockValuesConstants.DEFAULT_EMAIL;
import static util.MockValuesConstants.DEFAULT_NAME;
import static util.MockValuesConstants.DEFAULT_PASSWORD;
import static util.MockValuesConstants.DEFAULT_SESSION_ID;
import static util.MockValuesConstants.DEFAULT_TOPIC_ID;
import static util.MockValuesConstants.DEFAULT_USERNAME;

import com.google.gson.Gson;
import design.boilerplate.springboot.model.dto.CpfResponse;
import design.boilerplate.springboot.model.dto.SessionOpenningRequest;
import design.boilerplate.springboot.model.dto.SessionRequest;
import design.boilerplate.springboot.model.dto.TopicRequest;
import design.boilerplate.springboot.model.dto.UserRegistrationRequest;

public class DtoHelper {

  public static final String DEFAULT_PACKAGE = "1";
  public static final String DEFAULT_FUNDS = "123";
  public static final String DEFAULT_QUERY_ID = "11bb22cc33dd44ee";
  public static final String DEFAULT_DELAY = "0.3";
  public static final long DEFAULT_DURATION = 15L;
  public static final String INVALID_CPF_ERROR = "100";

  public static SessionRequest mockSessionRequest() {
    var req = new SessionRequest();

    req.setTopic(DEFAULT_TOPIC_ID);
    req.setDuration(DEFAULT_DURATION);

    return req;
  }

  public static TopicRequest mockTopicRequest() {
    var req = new TopicRequest();

    req.setName("Topic 1");
    return req;
  }

  public static String generateUserRegistrationRequestAsString() {
    var generatedCpf = new GeraCpfCnpj().cpf(false);
    return generateUserRegistrationRequestAsString(generatedCpf);
  }

  public static String generateUserRegistrationRequestAsString(String cpf) {
    var user = new UserRegistrationRequest();
    user.setUsername(cpf);
    user.setCpf(cpf);
    user.setName("Name Of The User " + cpf);
    user.setPassword("password");
    user.setEmail(cpf + "@company.com");
    Gson gson = new Gson();

    return gson.toJson(user);
  }

  public static SessionOpenningRequest mockSessionOpenningRequest() {
    var req = new SessionOpenningRequest();

    req.setSession(DEFAULT_SESSION_ID);
    req.setDuration(MockValuesConstants.DEFAULT_DURATION);

    return req;
  }

  public static UserRegistrationRequest generateUserRegistrationRequest() {
    var req = new UserRegistrationRequest();
    var cpf = new GeraCpfCnpj().cpf(false);
    req.setUsername(cpf);
    req.setCpf(cpf);
    req.setName("Name Of The User " + cpf);
    req.setPassword("password");
    req.setEmail(cpf + "@company.com");

    return req;
  }

  public static UserRegistrationRequest mockUserRegistrationRequest() {
    var req = new UserRegistrationRequest();
    req.setCpf(DEFAULT_CPF);
    req.setUsername(DEFAULT_USERNAME);
    req.setPassword(DEFAULT_PASSWORD);
    req.setEmail(DEFAULT_EMAIL);
    req.setName(DEFAULT_NAME);

    return req;
  }

  public static CpfResponse mockFailCpfResponse() {
    CpfResponse response = new CpfResponse();

    response.setStatus(CpfResponse.INVALID_STATUS);
    response.setErroCodigo(INVALID_CPF_ERROR);
    return response;
  }

  public static CpfResponse mockCpfResponse() {
    CpfResponse response = new CpfResponse();

    response.setStatus(CpfResponse.INVALID_STATUS);
    response.setCpf(DEFAULT_CPF);
    response.setNome(DEFAULT_NAME);
    response.setPacoteUsado(DEFAULT_PACKAGE);
    response.setSaldo(DEFAULT_FUNDS);
    response.setConsultaID(DEFAULT_QUERY_ID);
    response.setDelay(DEFAULT_DELAY);

    return response;
  }
}
