package com.company.seller.item.services;

import com.company.seller.item.models.Item;
import com.company.seller.item.repositories.ItemRepository;
import com.company.seller.services.AbstractCrudService;
import java.time.ZonedDateTime;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemService extends AbstractCrudService<ItemRepository, Item, UUID> {

  public ItemService(ItemRepository repository) {
    super(repository);
  }

  @Override
  @Transactional
  public Item create(Item item) {
    item.setCreated(ZonedDateTime.now());
    return super.create(item);
  }

  @Override
  @Transactional
  public Item update(UUID id, Item item) {
    var existing = getById(id);
    item.setCreated(existing.getCreated());
    return repository.save(item);
  }
}
