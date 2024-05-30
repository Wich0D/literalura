# Literalura
____________
Segundo desafio de la especialización en Back End del curso ONE de Oracle en colaboración con Alura Latam.

Litaralura es un sistema desarrollado en Java que utiliza la API gutendex que es una API que contiene una gran variedad de libros e información relevante de cada uno.
[Aquí la documentación](https://gutendex.com/)
___

## Extracción de datos de API

Se utiliza la biblioteca **java.net.http** entre otras para poder conectar la API al sistema, la clase encargada de realizar esta conexión es la clase **ConsumeAPI**. 

El método **obtainJsonFromAPI** se utiliza para poder extraer los datos de la API a partir de la URL y retorna los datos en formato **JSON**.
```java
    public class ConsumeAPI {
    public String obtainJsonFromAPI(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        String json = response.body();
        return json;
    }
}
```
Se utiliza la clase **Conversor** para poder convertir los datos en formato **Json** a el modelo que se implementa por medio de la clase **Data**.
```java
    public class Conversor implements IConversor {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtainData(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json,clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
```
 La clase **Data** utiliza la clase **com.fasterxml.jackson.annotation** para poder mapear la API con la clase.
 ```java
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Data(
        @JsonAlias("results") List<BookData> searchResults
        ) {

    }
```


---
## Manejo de Base de Datos
Se utiliza Postgres SQL para poder alojar una base de datos local. Literalura utiliza el modelo de estructura **View-Model** para poder extraer los datos de la API, adaptarlos a un modelo y usarlos en una base de datos.

Los datos se transfieren a la base de datos por medio de un repositorio. Se utilizan las interfaces **Repository** y **AuthorRepository** que heredan de la clase padre **JpaRepository**, por medio de esta clase podemos añadir los datos que obtenemos de nuestro modelo a las tablas de la base de datos, además de poder realizar Query's que nos ayudarán a realizar consultas en nuestra base de datos y manejarlas dentro del sistema usando Java.
