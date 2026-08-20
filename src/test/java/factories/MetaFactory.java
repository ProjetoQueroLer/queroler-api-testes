package factories;

import models.MetaModel;
import utils.DataFakerUtils;

public class MetaFactory {

    public static MetaModel adicionarMetaValida() {
        MetaModel meta = new MetaModel();

        meta.setAno(DataFakerUtils.anoAtual());
        meta.setMetaLivrosAno(DataFakerUtils.metaLivrosAno());
        meta.setMetaLivrosMes(DataFakerUtils.metaLivrosMes());
        meta.setMetaPaginasDia(DataFakerUtils.metaPaginasDia());

        return meta;
    }

}
