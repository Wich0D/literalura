package com.wich0D.BibliotecaAPI.service;

public interface IConversor {
    <T> T obtainData(String json, Class<T> clase); //Tipo de dato generico
}
