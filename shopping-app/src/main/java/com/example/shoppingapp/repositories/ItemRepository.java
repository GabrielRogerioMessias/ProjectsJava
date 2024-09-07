package com.example.shoppingapp.repositories;

import com.example.shoppingapp.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}
