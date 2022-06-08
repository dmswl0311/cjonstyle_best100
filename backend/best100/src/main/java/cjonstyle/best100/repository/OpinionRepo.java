package cjonstyle.best100.repository;

import cjonstyle.best100.domain.Opinion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpinionRepo extends JpaRepository<Opinion,Long> {
}
