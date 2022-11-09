package com.company.seller.item.repositories;

import com.company.seller.item.models.Item;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, UUID> { }
