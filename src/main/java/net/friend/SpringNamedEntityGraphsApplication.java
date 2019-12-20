package net.friend;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.friend.model.Characteristic;
import net.friend.model.Item;
import net.friend.repository.CharacteristicRepository;
import net.friend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class SpringNamedEntityGraphsApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(SpringNamedEntityGraphsApplication.class, args);
  }

  @Autowired
  ItemRepository itemRepository;

  @Autowired
  CharacteristicRepository characteristicRepository;

  @Override
  public void run(String... args) throws Exception {

    Item item1 = itemRepository.findByName("pepsi");
    log.info("itemName: {}", item1.getName());
    log.info("size of characteristics: {}", item1.getCharacteristics().size());

    Item item2 = itemRepository.findById(2L).get();
    log.info("itemID: {}", item2.getName());

    Characteristic characteristic1 = characteristicRepository.findByType("red");
    log.info("characteristicType: {}", characteristic1.getType());

    Characteristic characteristic2 = characteristicRepository.findById(2L).get();
    log.info("characteristicID: {}", characteristic2.getType());
  }
}
