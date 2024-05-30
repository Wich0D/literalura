package com.wich0D.BibliotecaAPI.model;

public enum Language {
    ENGLISH("en", "Ingles"),
    ESPANISH("es", "Español"),
    FRENCH("fr", "Frances"),
    PORTUGUESE("pt", "Portugues");
    private String languageAPI;
    private String languageSpanish;
    Language(String languageAPI, String languageSpanish){
        this.languageAPI = languageAPI;
        this.languageSpanish = languageSpanish;
    }
    public static  Language fromString(String text){
        for (Language language : Language.values()){
            if (language.languageAPI.equalsIgnoreCase(text)){
                return language;
            }
        }
        throw new IllegalArgumentException("Ningun idioma encontrado: "+ text);
    }
}
