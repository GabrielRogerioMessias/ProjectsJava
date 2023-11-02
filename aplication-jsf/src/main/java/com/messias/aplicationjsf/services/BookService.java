package com.messias.aplicationjsf.services;

import com.messias.aplicationjsf.domain.Book;
import com.messias.aplicationjsf.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> findALl() {
        return bookRepository.findAll();
    }

    public void delete(Integer idBook) {
        Book book = bookRepository.findById(idBook).get();
        bookRepository.delete(book);
    }

    public void insert(Book book) {
        bookRepository.save(book);
    }

    public void updateData(Book oldBoook, Book newBook) {
        oldBoook.setTitle(newBook.getTitle());
        oldBoook.setYearPublication(newBook.getYearPublication());
    }

    public void update(Integer idOldBook, Book newBook) {
        Book oldBook = bookRepository.findById(idOldBook).get();
        this.updateData(oldBook, newBook);
        bookRepository.save(oldBook);
    }


}
