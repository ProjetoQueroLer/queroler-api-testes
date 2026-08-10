package factories;

import models.DiarioModel;

public class DiarioFactory {

    public static DiarioModel criarDiarioLido(Integer livroID) {

        DiarioModel diario = new DiarioModel();

        diario.setLivroId(livroID);
        diario.setInicioDaLeitura("08/03/2026 10:00:00");
        diario.setTerminoDaLeitura("08/03/2026 12:00:00");
        diario.setPaginasLidas(20);
        diario.setNota(3.0);
        diario.setTituloDaResenha("Teste titulo resenha lido");
        diario.setResenha("resenha lido");
        diario.setSpoiler(true);
        return diario;

    }

    public static DiarioModel criarDiarioLendo(Integer livroID) {

        DiarioModel diario = new DiarioModel();

        diario.setLivroId(livroID);
        diario.setInicioDaLeitura("08/03/2026 10:00:00");
        diario.setTerminoDaLeitura(null);
        diario.setPaginasLidas(20);
        diario.setNota(1.0);
        diario.setTituloDaResenha("Teste titulo resenha lendo");
        diario.setResenha("resenha lendo");
        diario.setSpoiler(true);
        return diario;

    }

}
