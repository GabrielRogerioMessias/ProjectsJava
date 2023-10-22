package com.example.authenticarionjwt.repositories;

import com.example.authenticarionjwt.domain.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("SELECT e FROM Expense e WHERE e.user.id = :idUser")
    List<Expense> findEntrancesByUser(@Param("idUser") Long idUser);
}
