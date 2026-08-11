package models;

import lombok.Data;

@Data
public class LeituraComentarioModel {

    private Integer paginaInicial;
    private Integer paginaFinal;
    private String comentario;

}
