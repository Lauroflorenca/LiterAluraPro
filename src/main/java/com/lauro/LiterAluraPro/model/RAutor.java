package com.lauro.LiterAluraPro.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lauro.LiterAluraPro.dto.LivroDTO;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public record RAutor(
        @JsonAlias("docs") String results
) { }