package net.friend.controller;

import lombok.extern.slf4j.Slf4j;
import net.friend.model.Item;
import net.friend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/item")
public class ItemController {

  @Autowired
  ItemRepository itemRepository;

  @GetMapping()
  public ResponseEntity getItem(@RequestParam("name") String name) {

    Item item = itemRepository.findByName(name);
//    log.info("item: {}", item);

    return new ResponseEntity(HttpStatus.OK);
  }

}
