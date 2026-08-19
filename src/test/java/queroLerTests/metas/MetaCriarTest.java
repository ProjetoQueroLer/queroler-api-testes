package queroLerTests.metas;

import baseTest.BaseTest;
import factories.MetaFactory;
import models.MetaModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import report.Setup;
import utils.MetaHelper;
import utils.UsuarioHelper;

@ExtendWith(Setup.class)
public class MetaCriarTest extends BaseTest {

    @Test
    public void adicionarMetaLeitor() {
        String token = UsuarioHelper.loginLeitor();

        MetaModel metaLeitura = MetaFactory.adicionarMetaValida();

        MetaHelper.adicionarMeta(token, metaLeitura)
                .then()
                .log().body()
                .statusCode(201);

    }

    @Test
    public void adicionarMetaAdministrador() {
        String token = UsuarioHelper.loginAdministrador();

        MetaModel metaLeitura = MetaFactory.adicionarMetaValida();

        MetaHelper.adicionarMeta(token, metaLeitura)
                .then()
                .log().body()
                .statusCode(201);

    }

    @Test
    public void adicionarMetaModerador() {
        String token = UsuarioHelper.loginModerador();

        MetaModel metaLeitura = MetaFactory.adicionarMetaValida();

        MetaHelper.adicionarMeta(token, metaLeitura)
                .then()
                .log().body()
                .statusCode(201);

    }

}
