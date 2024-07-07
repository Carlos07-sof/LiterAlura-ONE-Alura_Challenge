package com.literalura.literalura.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

import com.literalura.literalura.DTO.datosAutores;


@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_author")
    private Long id;

    @Column(unique = true)
    private String name;

    private Integer birthYear;

    private Integer deathYear;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    

    public Autor() {
    }



    public Autor(datosAutores datosAutores){
        this.name = datosAutores.authorName();
        this.birthYear = datosAutores.birthYear();
        this.deathYear = datosAutores.deathYear();
    }
    

    
    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }



    public Integer getBirthYear() {
        return birthYear;
    }



    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }



    public Integer getDeathYear() {
        return deathYear;
    }



    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }



    public List<Libro> getLibros() {
        return libros;
    }



    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }


    @Override
    public String toString() {
        return "----- Autor -----" +
                "\n Nombre: " + name +
                "\n Fecha de Nacimiento: " + birthYear +
                "\n Fecha de Fallecimiento: " + deathYear +
                "\n Libros: " + libros.stream().map(b -> b.getTitle()).collect(Collectors.toList()) +
                "\n---------------\n";
    }
}
    

