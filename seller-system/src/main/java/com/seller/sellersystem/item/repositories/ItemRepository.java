package com.seller.sellersystem.item.repositories;

import com.seller.sellersystem.item.models.Item;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, UUID> { }
