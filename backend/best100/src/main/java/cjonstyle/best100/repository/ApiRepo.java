package cjonstyle.best100.repository;

import cjonstyle.best100.domain.Best;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ApiRepo extends JpaRepository<Best,Long> {
    List<Best> findTopByDate(LocalDate now);
    List<Best> findAllByDateOrderByRank(LocalDate now);
    List<Best> findAllByDateOrderByPriceAsc(LocalDate now);
    List<Best> findAllByDateOrderByPriceDesc(LocalDate now);
    List<Best> findAllByItemIdAndDateBetween(String itemId,LocalDate preview,LocalDate now);
}
