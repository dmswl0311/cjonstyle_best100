package cjonstyle.best100.repository;

import cjonstyle.best100.domain.Opinion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpinionRepo extends JpaRepository<Opinion,Long> {
    List<Opinion> findAllByItemId(String itemId);
}
