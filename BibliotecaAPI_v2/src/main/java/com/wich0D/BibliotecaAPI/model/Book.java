package com.wich0D.BibliotecaAPI.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    private String author;
    private List<String> languages;
    private Double downloads;

    public Book(){

    }
    public Book(String title, String author, List<String> languages, Double downloads){
        this. title = title;
        this.author = author;
        this.languages = languages;
        this.downloads = downloads;
    }

    public Book(BookData bookData){
        this.title = bookData.title();
        this.author = bookData.author().toString().replace("{","").replace("}","");
        this.languages = bookData.languages();
        this.downloads = bookData.downloads();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return title;
    }

    public void setName(String name) {
        this.title = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public Double getDownloads() {
        return downloads;
    }

    public void setDownloads(Double downloads) {
        this.downloads = downloads;
    }

    @Override
    public String toString() {
        return "Titulo: "+title+" - Autores: "+author+" - Lenguajes Disp: "+ languages +" - Descargas: "+ downloads;
    }
}
