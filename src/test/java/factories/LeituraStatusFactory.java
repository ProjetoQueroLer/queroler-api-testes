package factories;

import enums.LeituraStatus;
import models.LeituraStatusModel;

public class LeituraStatusFactory {

    public static LeituraStatusModel criarLeituraLivroInexistente() {
        LeituraStatusModel leitura = new LeituraStatusModel();

        leitura.setLivroId(-1);
        leitura.setStatus(LeituraStatus.LIVROS_QUE_QUERO_LER);

        return leitura;
    }

    public static LeituraStatusModel criarLeituraLivroStatusQueroLer(int livroId) {
        LeituraStatusModel leitura = new LeituraStatusModel();

        leitura.setLivroId(livroId);
        leitura.setStatus(LeituraStatus.LIVROS_QUE_QUERO_LER);

        return leitura;
    }

    public static LeituraStatusModel criarLeituraLivroStatusEstouLendo(int livroId) {
        LeituraStatusModel leitura = new LeituraStatusModel();

        leitura.setLivroId(livroId);
        leitura.setStatus(LeituraStatus.LIVROS_QUE_ESTOU_LENDO);

        return leitura;
    }

    public static LeituraStatusModel criarLeituraLivroStatusLivroLido(int livroId) {
        LeituraStatusModel leitura = new LeituraStatusModel();

        leitura.setLivroId(livroId);
        leitura.setStatus(LeituraStatus.LIVROS_LIDOS);

        return leitura;
    }

    public static LeituraStatusModel criarLeituraLivroStatusLivroAbandonado(int livroId) {
        LeituraStatusModel leitura = new LeituraStatusModel();

        leitura.setLivroId(livroId);
        leitura.setStatus(LeituraStatus.LIVROS_ABANDONADOS);

        return leitura;
    }
}
