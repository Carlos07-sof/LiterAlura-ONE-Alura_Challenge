package com.literalura.literalura.model;


import jakarta.persistence.*;

import com.literalura.literalura.DTO.datosLibros;

@Entity
@Table(name = "libros")

public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_book")
    private Long id;
    @Column(unique = true)
    private String title;

    private String language;

    @ManyToOne
    @JoinColumn(name = "id_author")
    private Autor autor;

    private Long downloads_count;

    

    public Libro() {
    }



    public Libro(datosLibros datosLibros) {
        this.title = datosLibros.title();
        this.language = datosLibros.languages().get(0).toUpperCase();
        this.autor = new Autor(datosLibros.authors().get(0));
        this.downloads_count = datosLibros.downloads();
    }

    

    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }



    public String getTitle() {
        return title;
    }



    public void setTitle(String title) {
        this.title = title;
    }



    public String getLanguage() {
        return language;
    }



    public void setLanguage(String language) {
        this.language = language;
    }



    public Autor getAutor() {
        return autor;
    }



    public void setAutor(Autor autor) {
        this.autor = autor;
    }



    public Long getDownloads_count() {
        return downloads_count;
    }



    public void setDownloads_count(Long downloads_count) {
        this.downloads_count = downloads_count;
    }


    @Override
    public String toString() {
        return "----- Libro -----" +
                "\n Titulo: " + title +
                "\n Autor: " + autor.getName() +
                "\n Idioma: " + language +
                "\n Número de descargas: " + downloads_count +
                "\n-----------------\n";
    }
    
}
