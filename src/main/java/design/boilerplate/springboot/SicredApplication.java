package design.boilerplate.springboot;

import feign.Contract;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableJpaRepositories
@EnableAspectJAutoProxy
@EnableFeignClients
@EnableJms
public class SicredApplication {

  public static void main(String[] args) {
    var context = SpringApplication.run(SicredApplication.class, args);
  }

  @Bean
  public BCryptPasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public Contract useFeignAnnotations() {
    return new Contract.Default();
  }
}
