package com.wich0D.BibliotecaAPI.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthorData(
        @JsonAlias("name") String name,
        @JsonAlias("birth_year") Integer birth,
        @JsonAlias("death_year") Integer death
) {
    @Override
    public String toString() {
        return  name;
    }
}
