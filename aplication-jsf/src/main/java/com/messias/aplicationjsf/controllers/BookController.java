package com.messias.aplicationjsf.controllers;

import com.messias.aplicationjsf.domain.Autor;
import com.messias.aplicationjsf.domain.Book;
import com.messias.aplicationjsf.domain.Editora;
import com.messias.aplicationjsf.repositories.AutorRepository;
import com.messias.aplicationjsf.repositories.EditoraRepository;
import com.messias.aplicationjsf.services.BookService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import org.omnifaces.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ViewScoped
@Data
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private EditoraRepository editoraRepository;
    private List<Book> bookList;
    private Book book;
    private Integer idAutor;
    private Integer idEditora;

    @PostConstruct
    public void novo() {
        book = new Book();
    }

    @PostConstruct
    public List<Book> listAll() {
        return bookList = bookService.findALl();
    }

    public void excluir(Book book) {
        bookService.delete(book);
    }

    public void insert() {
        Book newBook = book;
        Autor autor = autorRepository.findById(idAutor).get();
        Editora editora = editoraRepository.findById(idEditora).get();
        autor.getBookList().add(newBook);
        editora.getBookList().add(newBook);
        newBook.setEditora(editora);
        newBook.setAutor(autor);
        bookService.insert(newBook);
        Messages.addFlashGlobalInfo("Produto cadastrado com sucesso!");
        book = new Book();
    }

    public void update(){}

}
