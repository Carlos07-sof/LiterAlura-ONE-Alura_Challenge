package com.literalura.literalura.principal;


import com.literalura.literalura.service.ConsumoApi;
import com.literalura.literalura.service.ConvierteDatos;

import org.springframework.dao.DataIntegrityViolationException;
import com.literalura.literalura.repository.IbookRepository;
import com.literalura.literalura.DTO.datosAutores;
import com.literalura.literalura.DTO.datosJson;
import com.literalura.literalura.model.Autor;
import com.literalura.literalura.model.Libro;
import com.literalura.literalura.repository.IAuthorRepository;

import java.util.*;


public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private final String URL_BASE = "https://gutendex.com/books/";

    private ConvierteDatos conversor = new ConvierteDatos();


    private IbookRepository bookRepository;
    private IAuthorRepository authorRepository;

    public Principal(IbookRepository bookRepository, IAuthorRepository authorRepository){
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ----------- MENU -----------------
                    --------------------------------
                    Elija la opcion a traves de su numero:
                    1 - Buscar libros por titulo 
                    2 - listar libros registrados
                    3 - listar autores registrados
                    4 - listar autores vivos en un determinado año
                    5 - listar libros por idioma
                    6 - Salir
                    ---------------------------------
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibros();
                    break;
                case 2:
                    mostrarLibros();
                    break;
                case 3:
                    mostrarAutores();
                    break;
                case 4:
                    mostrarAutoresporaño();
                    break;
                case 5:
                    mostrarLibrosIdiomas();
                    break;
                case 6:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }
    private void buscarLibros(){
        try {

            System.out.println("Nombre del libro: ");
            String nameBook = teclado.nextLine();
    
            String json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nameBook.replace(" ", "+"));
            
            datosJson datos = conversor.obtenerDatos(json, datosJson.class);
            

            Optional<Libro> libros = datos.bookResults().stream()
                .findFirst()
                .map(b -> new Libro(b));

            if (libros.isPresent()) {
                Libro libro = libros.get();

                if (libro.getAutor()!= null) {
                    Autor autor = authorRepository.findAuthorsByName(libro.getAutor().getName());

                    if (autor == null) {
                        // Crear y guardar un nuevo autor si no existe
                        Autor newAuthor = libro.getAutor();
                        autor = authorRepository.save(newAuthor);
                    }

                    // Asociar el autor existente con el libro
                    libro.setAutor(autor);
                    bookRepository.save(libro);
                    System.out.println(libro);
                } else {
                    System.out.println("El libro no tiene un autor asociado.");
                }
            } else {
                System.out.println("No se encontró el libro: " + nameBook);
            }
        } catch (DataIntegrityViolationException e) {
            System.out.println("El libro ya se encuentra registrado en la base de datos.");
        } catch (Exception e) {
            System.out.println("Ocurrió un error al obtener el libro: " + e.getMessage());
        }

    }
    private void mostrarLibros(){
        
            List<Libro> libros = bookRepository.findAll();
            
            libros.forEach(System.out::println);
            
    }
    private void mostrarAutores(){
        System.out.println("Nombre del Autor: ");
        String authorName = teclado.nextLine();
        try {
            // Verificar si el nombre contiene números
            if (containsNumbers(authorName)) {
                throw new IllegalArgumentException("El nombre del autor no debe contener números.");
            }
            String json = consumoApi.obtenerDatos(URL_BASE + "?search=" + authorName.replace(" ", "+"));
            
            datosJson datos = conversor.obtenerDatos(json, datosJson.class);
    
            Optional<datosAutores> author = datos.bookResults().stream().findFirst().map(a -> new datosAutores(a.authors().get(0).authorName(), a.authors().get(0).birthYear(), a.authors().get(0).deathYear()));
    
            if (author.isPresent()) {
                System.out.println(author.get());
            } else {
                System.out.println("No se encontró autor con el nombre: " + authorName);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
    private void mostrarAutoresporaño(){
        try {
            
            System.out.println("Ingrese el año del autor: ");
            int year = teclado.nextInt();
            if (contains(year)) {
                throw new IllegalArgumentException("El año del autor no debe contener letra.");
            }

            List<Autor> autor = authorRepository.findAuthorBetweenYear(year);
            if(autor.isEmpty()){
                System.out.println("No se encontraron registros de autores vivos durante ese año en la base de datos.");
            }else{
                autor.forEach(System.out::println);
            }
            
        }catch (IllegalArgumentException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
    private void mostrarLibrosIdiomas(){
        System.out.println("Ingrese el idioma que desea buscar: ");
        System.out.println("""
                es -> Español
                en -> Inglés
                fr -> Francés
                pt -> Portugés
                """);

            String language = teclado.nextLine();

            List<Libro> books = bookRepository.findBookByLanguage(language.toUpperCase());
            if(books.isEmpty()){
                System.out.println("No se encontraron libros en ese idioma");
            }else{
                books.forEach(System.out::println);
            }

    }

    private boolean containsNumbers(String input) {
        return input.matches(".*\\d.*");
    }

    private boolean contains(int input) {   
        String inputAsString = String.valueOf(input);
        return inputAsString.toLowerCase().matches(".*[aeiou].*");
    }
 
    
}
