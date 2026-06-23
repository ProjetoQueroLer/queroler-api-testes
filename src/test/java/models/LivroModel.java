package models;

import lombok.Data;

import java.util.List;

@Data
public class LivroModel {

    private String titulo;
    private String isbn;
    private String editora;
    private String anoDePublicacao;
    private Integer numeroDePaginas;
    private String idioma;
    private String sinopse;
    private List<AutorModel> autores;
    private byte[] foto;

}
