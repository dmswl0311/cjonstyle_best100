package cjonstyle.best100.repository;

import cjonstyle.best100.domain.Best;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ApiRepo extends JpaRepository<Best,Long> {
    List<Best> findTopByDate(LocalDate now);
}
