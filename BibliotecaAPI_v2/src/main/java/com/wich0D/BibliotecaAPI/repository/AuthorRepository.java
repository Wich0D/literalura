package com.wich0D.BibliotecaAPI.repository;

import com.wich0D.BibliotecaAPI.model.Author;
import com.wich0D.BibliotecaAPI.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author,Long> {
    @Query("SELECT a FROM Author a WHERE a.birth <= :year AND (a.death IS NULL OR a.death >= :year)")
    List<Author> authorsAlive(@Param("year")Integer year);
}
