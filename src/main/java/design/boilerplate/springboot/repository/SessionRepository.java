package design.boilerplate.springboot.repository;

import design.boilerplate.springboot.model.entities.Session;
import design.boilerplate.springboot.model.entities.Topic;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SessionRepository extends JpaRepository<Session, Long> {

  List<Session> findByEndDateTimeOrderByBeginDateTimeAsc(LocalDateTime endDateTime,
      Pageable pageable);

  @EntityGraph(attributePaths = {"topic"})
  List<Session> findAllByEndDateTimeBeforeAndEndedIsFalse(LocalDateTime endDateTime);

  List<Session> findByTopic(Topic topic);

  boolean existsByTopic(Topic topic);


}
