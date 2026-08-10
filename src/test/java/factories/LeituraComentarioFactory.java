package factories;

import models.LeituraComentarioModel;

public class LeituraComentarioFactory {

    public static LeituraComentarioModel criarLeituraComentario() {
        LeituraComentarioModel leituraComentarioModel = new LeituraComentarioModel();

        leituraComentarioModel.setPaginaInicial(1);
        leituraComentarioModel.setPaginaFinal(60);
        leituraComentarioModel.setComentario("Comecei a leitura, otimo!");

        return leituraComentarioModel;
    }
}
