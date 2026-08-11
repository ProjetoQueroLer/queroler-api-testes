package factories;

import models.LeituraComentarioModel;
import utils.DataFakerUtils;

public class LeituraComentarioFactory {

    public static LeituraComentarioModel criarLeituraComentario() {
        LeituraComentarioModel leituraComentarioModel = new LeituraComentarioModel();

        int paginaInicial = DataFakerUtils.paginaInicial();
        int paginaFinal = DataFakerUtils.paginaFinal(paginaInicial);

        leituraComentarioModel.setPaginaInicial(paginaInicial);
        leituraComentarioModel.setPaginaFinal(paginaFinal);
        leituraComentarioModel.setComentario(DataFakerUtils.comentario());

        return leituraComentarioModel;
    }
}
