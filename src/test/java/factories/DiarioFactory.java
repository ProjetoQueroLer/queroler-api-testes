package factories;

import models.DiarioModel;
import utils.DataFakerUtils;

public class DiarioFactory {

    public static DiarioModel criarDiarioLido(Integer livroID) {

        DiarioModel diario = new DiarioModel();

        diario.setLivroId(livroID);
        diario.setInicioDaLeitura(DataFakerUtils.dataInicio());
        diario.setTerminoDaLeitura(DataFakerUtils.dataTermino());
        diario.setPaginasLidas(DataFakerUtils.paginasLidas());
        diario.setNota(DataFakerUtils.nota());
        diario.setTituloDaResenha(DataFakerUtils.tituloResenha());
        diario.setResenha(DataFakerUtils.resenha());
        diario.setSpoiler(DataFakerUtils.spoiler());
        return diario;

    }

    public static DiarioModel terminoLeituraAnteriorAoInicio(Integer livroID) {

        DiarioModel diario = new DiarioModel();

        diario.setLivroId(livroID);
        diario.setInicioDaLeitura(DataFakerUtils.dataTermino());
        diario.setTerminoDaLeitura(DataFakerUtils.dataInicio());
        diario.setPaginasLidas(DataFakerUtils.paginasLidas());
        diario.setNota(DataFakerUtils.nota());
        diario.setTituloDaResenha(DataFakerUtils.tituloResenha());
        diario.setResenha(DataFakerUtils.resenha());
        diario.setSpoiler(DataFakerUtils.spoiler());
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
