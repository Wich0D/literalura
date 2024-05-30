package com.wich0D.BibliotecaAPI.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookData(
        @JsonAlias("title") String title,
        @JsonAlias("authors") List<AuthorData> author,
        @JsonAlias("languages") List<String> languages,
        @JsonAlias("download_count") Double downloads
){
    @Override
    public String toString() {
        return "Titulo: "+title+" - Autores: "+author+" - Lenguajes Disp: "+ languages +" - Descargas: "+ downloads;
    }

    @Override
    public List<AuthorData> author() {
        return author;
    }
}
