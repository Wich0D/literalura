//package com.wich0D.BibliotecaAPI.principal;
//
//import com.wich0D.BibliotecaAPI.model.Datos;
//import com.wich0D.BibliotecaAPI.model.BookData;
//import com.wich0D.BibliotecaAPI.service.ConsumeAPI;
//import com.wich0D.BibliotecaAPI.service.Conversor;
//
//import java.util.Comparator;
//import java.util.DoubleSummaryStatistics;
//import java.util.Optional;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.stream.Collectors;
//
//public class Principal {
//
//    private static final String URL_BASE = "https://gutendex.com/books/";
//    private ConsumeAPI consumoAPI = new ConsumeAPI();
//    private Conversor conversor = new Conversor();
//
//    String json = consumoAPI.obtainJsonFromAPI(URL_BASE);
//    Datos datos = conversor.obtainData(json, Datos.class);
//    //Metodos
//    public void muestraElMenu(){
//        System.out.println(datos);
//    }
//    public void obtenerTop10Descargas(){
//        System.out.println("============Top 10 mas descargados============");
//        AtomicInteger posicion = new AtomicInteger(1);
//        datos.resultados().stream()
//                .sorted(Comparator.comparing(BookData::numeroDeDescargas).reversed())
//                .limit(10)
//                .forEach(l -> {
//                    System.out.println("Top "+posicion+": \n Titulo: "+l.titulo()+" - "+ l.numeroDeDescargas()+" descargas");
//                    posicion.getAndIncrement();
//                });
//    }
//    public void buscarLibro(String nombre){
//        json = consumoAPI.obtainJsonFromAPI(URL_BASE+"?search="+nombre.replace(" ","+"));
//        var datosBusqueda = conversor.obtainData(json,Datos.class);
//        System.out.println(datosBusqueda.resultados());
//        Optional<BookData> libroBuscado = datosBusqueda.resultados().stream()
//                .filter(l -> l.titulo().toUpperCase().contains(nombre.toUpperCase()))
//                .findFirst();
//        if (libroBuscado.isPresent()){
//            System.out.println(libroBuscado.get());
//        }else {
//            System.out.println(nombre+" no se encontró en la API");
//        }
//    }
//    public void obtnenerEstadisticas(){
//        DoubleSummaryStatistics est = datos.resultados().stream()
//                .filter(d -> d.numeroDeDescargas() > 0)
//                .collect(Collectors.summarizingDouble(BookData::numeroDeDescargas));
//        System.out.println("========Estadisticas========");
//        System.out.println("Media de Descargas:" +est.getAverage());
//        System.out.println("Descargas maximas:" +est.getMax());
//        System.out.println("Descargas minimas:" +est.getMin());
//
//    }
//
//}
//
