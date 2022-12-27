package design.boilerplate.springboot.service.performance;

import static io.gatling.javaapi.core.CoreDsl.AllowList;
import static io.gatling.javaapi.core.CoreDsl.DenyList;
import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.core.CoreDsl.feed;
import static io.gatling.javaapi.core.CoreDsl.jsonPath;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.core.OpenInjectionStep.atOnceUsers;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;
import static util.DtoHelper.generateUserRegistrationRequest;

import com.google.gson.Gson;
import design.boilerplate.springboot.model.dto.LoginRequest;
import design.boilerplate.springboot.model.dto.SessionRequest;
import design.boilerplate.springboot.model.dto.TopicRequest;
import design.boilerplate.springboot.model.dto.UserRegistrationRequest;
import design.boilerplate.springboot.model.enums.VoteResult;
import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.apache.commons.lang3.RandomStringUtils;
import util.GeraCpfCnpj;

public class PerformanceSimulation extends Simulation {

  public static final String HOST = "http://localhost:8080";
  private static final Gson gson = new Gson();
  public static final String GZIP_DEFLATE = "gzip, deflate";
  public static final String ACCEPT_HEADER = "*/*";
  public static final String CONTENT_TYPE = "application/json";
  public static final String AGENT_HEADER = "PostmanRuntime/7.30.0";
  public static final String USER_CREATION_URL = "/user/v1";
  public static final int HTTTP_CREATED = 201;
  public static final int HTTP_OK = 200;
  private static final Random random = new Random();
  public static final String LOGIN_URL = "/login/v1";
  public static final String CREATE_TOPIC_URL = "/topic/v2";
  public static final String CREATE_SESSION_URL = "/session/v2";
  public static final long DEFAULT_SESSION_DURATION = 2L;
  private Long sessionId;
  private Queue<UserRegistrationRequest> feederUsers;
  private final GeraCpfCnpj cpfGenerator = new GeraCpfCnpj();
  private final String sessionDate = LocalDateTime.now().minusMinutes(5)
      .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

  public PerformanceSimulation() {
//    var prepareSessionScenario = postUserAndLogin().injectOpen(rampUsers(100).during(15));
    var prepareSessionScenario = prepareSession().injectOpen(atOnceUsers(2));
    setUp(prepareSessionScenario).protocols(httpProtocol());
  }

  private ScenarioBuilder prepareSession() {
    return scenario("creating_session").exec(createUser(), loginUser(), createTopic(),
        createSession());
  }

  private HttpProtocolBuilder httpProtocol() {
    return http.baseUrl(HOST)
        .inferHtmlResources(AllowList(), DenyList())
        .acceptHeader(ACCEPT_HEADER)
        .acceptEncodingHeader(GZIP_DEFLATE)
        .contentTypeHeader(CONTENT_TYPE)
        .userAgentHeader(AGENT_HEADER);
  }

  private ChainBuilder createUser() {
    return
        feed(userFeed)
            .exec(http("user_creation").post(USER_CREATION_URL).body(
                    StringBody(session -> gson.toJson((UserRegistrationRequest) session.get("user"))))
                .check(status().is(HTTTP_CREATED)))
            .exec(session -> {
              var user = (UserRegistrationRequest) session.get("user");
              var loginRequest = gson.toJson(
                  new LoginRequest(user.getUsername(), user.getPassword()));
              return session.set("loginRequest", loginRequest);
            });
  }

  private ChainBuilder loginUser() {
    return exec(http("login").post(LOGIN_URL)
        .body(StringBody(session -> session.get("loginRequest")))
        .check(status().is(HTTP_OK))
        .check(jsonPath("$.token").saveAs("token")));
  }

  private ChainBuilder createTopic() {
    return
        feed(topicFeeder)
            .exec(http("create_topic").post(CREATE_TOPIC_URL)
                .header("Authorization", "Bearer #{token}")
                .body(StringBody(session -> gson.toJson(new TopicRequest(session.get("topic")))))
                .check(status().is(HTTTP_CREATED))
                .check(jsonPath("$.id").saveAs("topicId")));
  }


  private ChainBuilder createSession() {
    return
        exec(session -> {
          var topic = session.getString("topicId");
          var sessionRequest = gson.toJson(new SessionRequest(Long.parseLong(topic),
              DEFAULT_SESSION_DURATION));
          return session.set("sessionRequest", sessionRequest);
        })
            .exec(http("create_session").post(CREATE_SESSION_URL)
                .header("Authorization", "Bearer #{token}")
                .body(StringBody(session -> session.get("sessionRequest")))
                .asJson()
                .check(status().is(HTTTP_CREATED)).check(jsonPath("$.id").saveAs("sessionId")));
  }

  final Iterator<Map<String, Object>> userFeed = Stream.generate(
      (Supplier<Map<String, Object>>) () -> Collections.singletonMap("user",
          generateUserRegistrationRequest())).iterator();

  final Iterator<Map<String, Object>> topicFeeder =
      Stream.generate((Supplier<Map<String, Object>>) () -> {
        String generatedTopicName = "Pauta Aleatória " + RandomStringUtils.randomAlphanumeric(20);
        return Collections.singletonMap("topic", generatedTopicName);
      }).iterator();

  private VoteResult randomVote() {
    return random.nextInt(2) == 0 ? VoteResult.YES : VoteResult.NO;
  }
}
