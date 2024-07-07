package com.literalura.literalura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.literalura.literalura.model.Libro;

import java.util.List;

@Repository
public interface IbookRepository extends JpaRepository<Libro,Long> {
    List<Libro> findBookByLanguage(String language);
}