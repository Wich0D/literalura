package com.wich0D.BibliotecaAPI.principal;

import com.wich0D.BibliotecaAPI.model.*;
import com.wich0D.BibliotecaAPI.repository.AuthorRepository;
import com.wich0D.BibliotecaAPI.repository.Repository;
import com.wich0D.BibliotecaAPI.service.ConsumeAPI;
import com.wich0D.BibliotecaAPI.service.Conversor;

import java.sql.SQLOutput;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConsumeAPI consumeAPI = new ConsumeAPI();
    private Conversor conversor = new Conversor();

    private String json = consumeAPI.obtainJsonFromAPI(URL_BASE);
    private Data data = conversor.obtainData(json, Data.class);
    private Scanner key = new Scanner(System.in);
    private Repository repository;
    private AuthorRepository authorRepository;


    public Main(Repository repository, AuthorRepository authorRepository) {
        this.repository = repository;
        this.authorRepository = authorRepository;
    }

    public void menu(){
        var option = -1;
        while (option != 0){
            System.out.println("""
                    ================================================
                    1. Buscar libro por titulo
                    2. Libros buscados
                    3. Buscar libros buscados por idioma
                    4. Mostrar Autores
                    5. Mostrar Autores vivos en determinado momento
                    0. Salir
                    """);
            System.out.print("Ingresa una opcion: ");
            option = key.nextInt();
            key.nextLine();
            switch (option){
                case 0:
                    System.out.println("Finalizando programa..."); break;
                case 1: searchTitle(); break;
                case 2: showSearch(); break;
                case 3: searchByLan();break;
                case 4: showAuthors(); break;
                case 5: showAuthorsByYear(); break;
                default:
                    System.out.println("Opcion no disponible"); break;
            }
        }
    }

    private void showAuthorsByYear() {
        try{
            System.out.println("Ingresa el año en el que deseas saber que autores permanecian con vida: ");
            int year = key.nextInt();
            List<Author> authors =authorRepository.authorsAlive(year);
            if(!authors.isEmpty()) {
                authors.stream().forEach(a ->
                        System.out.println("Autor: " + a.getName() + " | Nacimiento: " + (a.getBirth() != null ? a.getBirth() : "Desconocido")
                                + " | Fallecimiento: " + (a.getDeath() != null ? a.getDeath() : "Sigue con vida"))
                );
            }else {
                System.out.println("Ningun autor encontrado vivió en ese año");
            }
        } catch (InputMismatchException e) {
            System.out.println("Opcion no valida");
            key.nextLine();
            menu();
        }
    }

    private void showAuthors() {
        List<Author> authors = authorRepository.findAll();
        authors.stream().forEach(a ->
                System.out.println("Autor: "+a.getName() + " | Nacimiento: "+(a.getBirth() != null ? a.getBirth() : "Desconocido")
                        +" | Fallecimiento: " +(a.getDeath() !=null ? a.getDeath() : "Sigue con vida"))
        );
    }

    private void searchByLan() {
        List<Book> books = new ArrayList<>();
        System.out.println("""
                =============================
                Busqueda de Libros por idioma
                es - español
                en - ingles
                fr - frances
                pt - portugués
                al - todos los idiomas alfabeticamente
                """);
        System.out.println("Elige una Opción");
        var opt = key.nextLine();
        if( opt.equals("es") || opt.equals("en")  || opt.equals("fr") || opt.equals("pt") ){
            List<String> list = new ArrayList<>();
            list.add(opt);
            books = repository.searchByLanguage(list);
            System.out.println("Se encontraron "+books.size()+" libros que coinciden con el idioma "+opt);
            books.stream().forEach(b ->
                    System.out.println("Idiomas: "+b.getLanguages()+" | Titulo: "+b.getName()+" | Autor: "+b.getAuthor()+
                            " | Descargas: "+b.getDownloads())
            );
        }else if(opt.equals("al") ){
            books = repository.orderLanguages();
            books.stream().forEach(b ->
                    System.out.println("Idiomas: "+b.getLanguages()+" | Titulo: "+b.getName()+" | Autor: "+b.getAuthor()+
                            " | Descargas: "+b.getDownloads())
            );
        }else{
            System.out.println("Opcion no disponible");
        }
    }

    private void showSearch() {
        List<Book> booksSearched = repository.findAll();
        booksSearched.stream()
                .forEach(b ->
                                System.out.println("Titulo: "+b.getName()+" | Autor: "+b.getAuthor()+
                                        " | Idiomas: "+b.getLanguages()+" | Descargas: "+b.getDownloads())
                        );
    }

    private BookData getData(String name){
        name.toLowerCase();
        var url = URL_BASE+"?search="+name.replace(" ","+");
        var json = consumeAPI.obtainJsonFromAPI(url);
        Data data = conversor.obtainData(json,Data.class);
        if (data.searchResults() == null || data.searchResults().isEmpty()){
            System.out.println("El libro que buscas no existe o no se encuentra");
            return null;
        }
        Optional<BookData> searchResult = data.searchResults().stream().findFirst();
        if(searchResult.isPresent()){
            var result = searchResult.get();
                return result;
        }
        //System.out.println("El libro que buscas no existe o no se encuentra");
        return null;
    }
    private AuthorData getAuthorData(String name){
        name.toLowerCase();
        var url = URL_BASE+"?search="+name.replace(" ","+");
        var json = consumeAPI.obtainJsonFromAPI(url);
        Data data = conversor.obtainData(json,Data.class);
        Optional<BookData> searchResult = data.searchResults().stream().findFirst();
        if(searchResult.isPresent()){
            var result = searchResult.get();
            Optional<AuthorData> authorData = result.author().stream().findFirst();
            if (authorData.isPresent()){
                var author = authorData.get();
                return author;
            }
        }
        System.out.println("El libro que buscas no existe o no se encuentra");
        return null;
    }
    private void searchTitle() {
        System.out.println(" \n Ingresa el Titulo del libro que estas buscando: ");
        var title = key.nextLine();
        var titleConverted = getData(title);
        //Check if book exists
        if (titleConverted != null){
            List<Book> booksSaved = repository.findAll();
            List<String> titles = booksSaved.stream().map(Book::getName).collect(Collectors.toList());
            if (titles.contains(title)){
                Book newBook = new Book(titleConverted);
                var authorData = getAuthorData(title);
                Author newAuthor = new Author(authorData);
                repository.save(newBook);
                authorRepository.save(newAuthor);
                System.out.println(newBook.toString());
            }else{
                System.out.println("Ya buscaste ese libro");
            }


        }
    }
}
