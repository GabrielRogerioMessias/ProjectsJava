package com.example.authenticarionjwt.service;

import com.example.authenticarionjwt.domain.Expense;
import com.example.authenticarionjwt.domain.User;
import com.example.authenticarionjwt.repositories.ExpenseRepository;
import com.example.authenticarionjwt.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class ExpenseService implements Serializable {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    public List<Expense> findAll(Long idUser) {
       return expenseRepository.findEntrancesByUser(idUser);
    }

    public Expense insert(Long idUser, Expense expense) {
        User user = userRepository.findById(idUser).get();
        expense.setUser(user);
        user.getExpenses().add(expense);
        user.setSaldo(user.getSaldo() - expense.getValor());
        return expenseRepository.save(expense);
    }
}
