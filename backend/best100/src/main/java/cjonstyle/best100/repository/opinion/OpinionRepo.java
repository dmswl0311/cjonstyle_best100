package cjonstyle.best100.repository.opinion;

import cjonstyle.best100.domain.entity.opinion.Opinion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OpinionRepo extends JpaRepository<Opinion, Long> {
    List<Opinion> findAllByItemIdOrderByDateDescIdDesc(String itemId);
    List<Opinion> findAllByItemIdOrderByLikeDescIdDesc(String itemId);
    Optional<Opinion> findById(Long opinionId);


}
