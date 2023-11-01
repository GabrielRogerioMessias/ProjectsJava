package com.messias.aplicationjsf.repositories;

import com.messias.aplicationjsf.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
