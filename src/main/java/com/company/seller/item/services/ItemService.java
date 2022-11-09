package com.company.seller.item.services;

import com.company.seller.item.models.Item;
import com.company.seller.item.repositories.ItemRepository;
import com.company.seller.services.AbstractCrudService;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ItemService extends AbstractCrudService<ItemRepository, Item, UUID> {

  public ItemService(ItemRepository repository) {
    super(repository);
  }
}
