package com.wich0D.BibliotecaAPI.repository;

import com.wich0D.BibliotecaAPI.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Repository extends JpaRepository<Book,Long> {
    @Query("SELECT b FROM Book b ORDER BY b.languages ASC")
    List<Book> orderLanguages();
    @Query("SELECT b FROM Book b WHERE  b.languages = :lan")
    List<Book>searchByLanguage(List<String> lan);
}
