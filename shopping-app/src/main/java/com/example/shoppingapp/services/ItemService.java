package com.example.shoppingapp.services;

import com.example.shoppingapp.domain.Item;
import com.example.shoppingapp.repositories.ItemRepository;
import com.example.shoppingapp.services.exceptions.NullEntityFieldException;
import jakarta.validation.ConstraintViolation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import jakarta.validation.Validator;

import java.util.List;
import java.util.Set;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final Validator validator;

    public ItemService(ItemRepository itemRepository, Validator validator) {
        this.itemRepository = itemRepository;
        this.validator = validator;
    }

    public List<Item> getAllItems() {
        return this.itemRepository.findAll();
    }

    private List<String> validateItems(Item item) {
        Set<ConstraintViolation<Item>> violations = validator.validate(item);
        return violations.stream().map((v) -> v.getPropertyPath().toString()).toList();
    }

    public Item insertItem(Item item) {
        try {
            return this.itemRepository.save(item);
        } catch (TransactionSystemException exception) {
            List<String> errors;
            errors = this.validateItems(item);
            throw new NullEntityFieldException(errors);
        }

    }
}
