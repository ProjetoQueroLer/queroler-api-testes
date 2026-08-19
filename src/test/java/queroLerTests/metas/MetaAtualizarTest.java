package queroLerTests.metas;

import baseTest.BaseTest;
import models.MetaModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import report.Setup;
import utils.MetaHelper;
import utils.UsuarioHelper;

@ExtendWith(Setup.class)
public class MetaAtualizarTest extends BaseTest {

    @Test
    public void atualizarMeta() {
        String token = UsuarioHelper.loginLeitor();

        MetaModel metaLeitura = new MetaModel();

        metaLeitura.setMetaLivrosAno(25);
        metaLeitura.setMetaLivrosMes(5);
        metaLeitura.setMetaPaginasDia(35);

        MetaHelper.atualizarMeta(token, metaLeitura)
                .then()
                .log().body()
                .statusCode(204);

    }

}
