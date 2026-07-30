package factories;

import enums.LeituraStatus;
import models.LeituraModel;

public class LeituraFactory {

    public static LeituraModel criarLeituraLivroInexistente() {
        LeituraModel leitura = new LeituraModel();

        leitura.setLivroId(-1);
        leitura.setStatus(LeituraStatus.LIVROS_QUE_QUERO_LER);

        return leitura;
    }

    public static LeituraModel criarLeituraLivroStatusQueroLer(int livroId) {
        LeituraModel leitura = new LeituraModel();

        leitura.setLivroId(livroId);
        leitura.setStatus(LeituraStatus.LIVROS_QUE_QUERO_LER);

        return leitura;
    }

    public static LeituraModel criarLeituraLivroStatusEstouLendo(int livroId) {
        LeituraModel leitura = new LeituraModel();

        leitura.setLivroId(livroId);
        leitura.setStatus(LeituraStatus.LIVROS_QUE_ESTOU_LENDO);

        return leitura;
    }

    public static LeituraModel criarLeituraLivroStatusLivroLido(int livroId) {
        LeituraModel leitura = new LeituraModel();

        leitura.setLivroId(livroId);
        leitura.setStatus(LeituraStatus.LIVROS_LIDOS);

        return leitura;
    }

    public static LeituraModel criarLeituraLivroStatusLivroAbandonado(int livroId) {
        LeituraModel leitura = new LeituraModel();

        leitura.setLivroId(livroId);
        leitura.setStatus(LeituraStatus.LIVROS_ABANDONADOS);

        return leitura;
    }
}
