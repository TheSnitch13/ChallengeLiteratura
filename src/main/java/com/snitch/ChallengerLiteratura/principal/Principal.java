package com.snitch.ChallengerLiteratura.principal;

import com.snitch.ChallengerLiteratura.model.Autor;
import com.snitch.ChallengerLiteratura.model.Datos;
import com.snitch.ChallengerLiteratura.model.DatosLibros;
import com.snitch.ChallengerLiteratura.model.Libro;
import com.snitch.ChallengerLiteratura.repository.AutorRepository;
import com.snitch.ChallengerLiteratura.repository.LibrosRepository;
import com.snitch.ChallengerLiteratura.service.ConsumoAPI;
import com.snitch.ChallengerLiteratura.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);

    private LibrosRepository librosRepository;
    private AutorRepository autorRepository;

    public Principal(LibrosRepository librosRepository, AutorRepository autorRepository) {
        this.librosRepository = librosRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu(){
                int opcion;
        boolean continuar = true;

        do{
            System.out.println("""
                    -----------------
                    Bienvenido, elija el número de opción que quiera realizar
                    1- Buscar libro por título
                    2- Listar libros registrados
                    3- Listar autores registrados
                    4- Listar autores vivos en un determinado año
                    5- Listar libros por idioma
                    0- Salir
                    Tu opción: """);
            if (teclado.hasNextInt()) {
                opcion = teclado.nextInt();
                teclado.nextLine();
                switch(opcion){
                    case 1:
                        buscarLibro();
                        break;
                    case 2:
                        listarLibros();
                        break;
                    case 3:
                        listarAutores();
                        break;
                    case 4:
                        listarAutoresVivosEnAnio();
                        break;
                    case 5:
                        listarLibrosPorIdioma();
                        break;
                    case 0:
                        continuar = false;
                        break;
                    default:
                        System.out.println("Ingresaste una opción incorrecta");
                        System.out.println("Vuelve a intentarlo");
                        break;
                }
            } else {
                System.out.println("Debes ingresar un número.");
                teclado.nextLine();
                opcion = -1;
            }
        } while(continuar);
    }


    private void buscarLibro() {
        //Busqueda de libros por nombre
        var json = consumoAPI.obtenerDatos(URL_BASE);
        // System.out.println(json);
        var datos = conversor.obtenerDatos(json, Datos.class);
        // System.out.println(datos);

        System.out.println("Ingrese el nombre del libro que desea buscar: ");
        var tituloLibro = teclado.nextLine();

        json = consumoAPI.obtenerDatos(URL_BASE+"?search=" + tituloLibro.replace(" ","+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);

        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
        if(libroBuscado.isPresent()){
            DatosLibros datosLibros = libroBuscado.get();

            Optional<Libro> libroExistente = librosRepository
                    .findByTituloContainsIgnoreCase(datosLibros.titulo());

            if(libroExistente.isPresent()){
                System.out.println("¡El libro ya estaba registrado en la base de datos!");
            } else{
                Libro libro= new Libro(datosLibros);

                List<Autor> autores = datosLibros.autores().stream()
                        .map(datosAutor -> autorRepository.findByNombreContainsIgnoreCase(datosAutor.nombre())
                                .orElseGet(() -> autorRepository.save(new Autor(datosAutor))))
                        .collect(Collectors.toList());

                libro.setAutores(autores);
                librosRepository.save(libro);

                System.out.println("Libro guardado");
                System.out.println(libro);
            }
        }else {
            System.out.println("Libro no encontrado");
        }
    }

    private void listarLibros() {
        List <Libro> libros = librosRepository.findAll();

        if(libros.isEmpty()){
            System.out.println("No ha registrado ningún libro");
        } else {
            System.out.println("Listado de libros: ");
            libros.forEach(libro -> System.out.println(libro));
        }
    }

    private void listarAutores() {
        List<Autor> autores = autorRepository.listarAutoresConLibros();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados");
        } else {
            System.out.println("Listado de autores registrados:");

            autores.forEach(autor -> {
                System.out.println("---------------------");
                System.out.println("Autor: " + autor.getNombre());
                System.out.println("Fecha de nacimiento: " + autor.getFechaDeNacimiento());
                System.out.println("Fecha de fallecimiento: " + autor.getFechaDeFallecimiento());

                String librosTexto = autor.getLibros().isEmpty()
                        ? "Sin libros registrados"
                        : autor.getLibros().stream()
                        .map(Libro::getTitulo)
                        .collect(Collectors.joining(", "));

                System.out.println("Libros: " + librosTexto);
            });
        }
    }

    private void listarAutoresVivosEnAnio() {
        System.out.println("Ingresa el año por le que deseas buscar: ");
        String inputAnio = teclado.nextLine();

        try{
            Integer anio = Integer.parseInt(inputAnio);

            List<Autor> autoresVivos = autorRepository.autoresVivosConLibrosEnDeterminadoAnio(anio);

            if(autoresVivos.isEmpty()){
                System.out.println("No se encontraron autores vivos en el año "+anio);
            } else {
                System.out.println("Listado de autores vivos en el año "+anio);
                autoresVivos.forEach(autor -> System.out.println(autor));
            }
        } catch (NumberFormatException e) {
            System.out.println("Debes ingresar un año valido");
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("""
            Ingresa el idioma para buscar libros:
            es - Español
            en - Inglés
            fr - Francés
            pt - Portugués
            """);

        String idioma = teclado.nextLine().toLowerCase();

        List<Libro> librosPorIdioma = librosRepository.buscarLibrosPorIdioma(idioma);

        if (librosPorIdioma.isEmpty()) {
            System.out.println("No se encontraron libros en ese idioma.");
        } else {
            System.out.println("Libros encontrados:");
            librosPorIdioma.forEach(System.out::println);
        }
    }
}



