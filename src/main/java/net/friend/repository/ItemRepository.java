package net.friend.repository;

import java.util.Optional;
import net.friend.model.Item;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

  @EntityGraph(attributePaths = { "characteristics" })
  Item findByName(String name);

  Optional<Item> findById(Long id);
}
