package util;

import static util.MockValuesConstants.DEFAULT_CPF;
import static util.MockValuesConstants.DEFAULT_EMAIL;
import static util.MockValuesConstants.DEFAULT_ENCRYPTED_PASSWORD;
import static util.MockValuesConstants.DEFAULT_SESSION_ID;
import static util.MockValuesConstants.DEFAULT_TOPIC_ID;
import static util.MockValuesConstants.DEFAULT_TOPIC_NAME;
import static util.MockValuesConstants.DEFAULT_USERNAME;
import static util.MockValuesConstants.DEFAULT_USER_ID;
import static util.MockValuesConstants.NOW;

import design.boilerplate.springboot.model.entities.Session;
import design.boilerplate.springboot.model.entities.Topic;
import design.boilerplate.springboot.model.entities.User;
import design.boilerplate.springboot.model.enums.UserRole;

public class ModelHelper {


  public static User mockAdminUser() {
    var user = mockUser();
    user.setUserRole(UserRole.ADMIN);
    return user;
  }

  public static User mockUser(String username) {
    var user = mockUser();
    user.setUsername(username);
    return user;
  }

  public static User mockUser() {
    var user = new User();
    user.setId(DEFAULT_USER_ID);
    user.setUserRole(UserRole.USER);
    user.setPassword(DEFAULT_ENCRYPTED_PASSWORD);
    user.setName(DEFAULT_USERNAME);
    user.setCpf(DEFAULT_CPF);
    user.setEmail(DEFAULT_EMAIL);
    user.setUsername(DEFAULT_USERNAME);

    return user;
  }

  public static Session mockSession() {
    var model = new Session();

    model.setId(DEFAULT_SESSION_ID);
    model.setTopic(mockTopic());
    model.setBeginDateTime(NOW);

    return model;
  }

  private static Topic mockTopic() {
    var topic = new Topic();
    topic.setId(DEFAULT_TOPIC_ID);
    topic.setName(DEFAULT_TOPIC_NAME);
    return topic;
  }

}
