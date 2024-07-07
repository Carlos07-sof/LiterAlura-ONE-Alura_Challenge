package com.literalura.literalura.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record datosJson(
        @JsonAlias("results")
        List<datosLibros> bookResults
) {
}