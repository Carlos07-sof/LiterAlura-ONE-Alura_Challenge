package com.literalura.literalura.DTO;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonAlias;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record datosLibros( 
        @JsonAlias("title")String title, 
        @JsonAlias("authors")List<datosAutores> authors,
        @JsonAlias("languages")List<String> languages,
        @JsonAlias("download_count")long downloads) {
}