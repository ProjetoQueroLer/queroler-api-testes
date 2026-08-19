package factories;

import models.MetaModel;

public class MetaFactory {

    public static MetaModel adicionarMetaValida() {
        MetaModel meta = new MetaModel();

        meta.setAno(2026);
        meta.setMetaLivrosAno(20000);
        meta.setMetaLivrosMes(200);
        meta.setMetaPaginasDia(200);

        return meta;
    }

}
