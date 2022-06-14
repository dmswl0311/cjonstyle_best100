package cjonstyle.best100.repository.item;

import cjonstyle.best100.domain.Best;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ItemRepo extends JpaRepository<Best,Long> {
    List<Best> findTopByDate(LocalDate now);
    List<Best> findAllByDateOrderByRank(LocalDate now);
    List<Best> findAllByDateOrderByPriceAsc(LocalDate now);
    List<Best> findAllByDateOrderByPriceDesc(LocalDate now);
    List<Best> findAllByItemIdAndDateBetween(String itemId,LocalDate preview,LocalDate now);
}
