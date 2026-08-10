package models;

import lombok.Data;

@Data
public class DiarioModel {

    private Integer livroId;
    private String inicioDaLeitura;
    private String terminoDaLeitura;
    private Integer paginasLidas;
    private Double nota;
    private String tituloDaResenha;
    private String resenha;
    private boolean spoiler;

}
