package net.friend.repository;

import java.util.Optional;
import net.friend.model.Characteristic;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacteristicRepository extends JpaRepository<Characteristic, Long> {

  Characteristic findByType(String type);

  @EntityGraph(attributePaths = { "item" })
  Optional<Characteristic> findById(Long id);
}
