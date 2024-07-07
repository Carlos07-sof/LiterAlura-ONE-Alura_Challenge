package com.literalura.literalura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.literalura.literalura.model.Autor;
import java.util.List;

@Repository
public interface IAuthorRepository extends JpaRepository<Autor,Long> {
    Autor findAuthorsByName(String name);
    @Query(value = "SELECT * FROM autores WHERE :year >= birth_year AND :year <= death_year", nativeQuery = true)
    List<Autor> findAuthorBetweenYear(int year);
}