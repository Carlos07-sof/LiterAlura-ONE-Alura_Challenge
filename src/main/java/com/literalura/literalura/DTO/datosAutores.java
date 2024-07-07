package com.literalura.literalura.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;


public record datosAutores(        
    @JsonAlias("name")String authorName,

    @JsonAlias("birth_year") int birthYear,

    @JsonAlias("death_year") int deathYear) 
    {
        public String toString() {
            return "----- Autor -----" +
                    "\n Nombre: " + authorName +
                    "\n Fecha de Nacimiento: " + birthYear +
                    "\n Fecha de Fallecimiento: " + deathYear +
                    "\n ---------------\n";
        }
    }
