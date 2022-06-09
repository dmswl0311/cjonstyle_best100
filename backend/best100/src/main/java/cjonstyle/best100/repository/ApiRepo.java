package cjonstyle.best100.repository;

import cjonstyle.best100.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiRepo extends JpaRepository<Item,Long> {
}
