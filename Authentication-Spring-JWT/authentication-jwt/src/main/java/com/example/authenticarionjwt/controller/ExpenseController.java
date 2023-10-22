package com.example.authenticarionjwt.controller;

import com.example.authenticarionjwt.domain.Expense;
import com.example.authenticarionjwt.repositories.ExpenseRepository;
import com.example.authenticarionjwt.service.ExpenseService;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/expense")
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping(value = "/{idUser}")
    public ResponseEntity<List<Expense>> findAll(@PathVariable Long idUser) {
        List<Expense> list = expenseService.findAll(idUser);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping(value ="/{idUser}")
    public ResponseEntity<Expense> insert(@PathVariable Long idUser, @RequestBody Expense expense) {
        Expense newExpense = expenseService.insert(idUser, expense);
        String uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id").buildAndExpand(expense.getId()).toString();
        return ResponseEntity.created(URI.create(uri)).body(newExpense);
    }
}
