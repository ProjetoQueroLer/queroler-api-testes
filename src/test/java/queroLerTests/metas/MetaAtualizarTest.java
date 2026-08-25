package queroLerTests.metas;

import baseTest.BaseTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import factories.MetaFactory;
import models.MetaModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import queroLerTests.usuarios.UsuarioCadastrarTest;
import report.Setup;
import utils.MetaHelper;
import utils.UsuarioHelper;

@ExtendWith(Setup.class)
public class MetaAtualizarTest extends BaseTest {

    @Test
    public void atualizarMeta() throws JsonProcessingException {
        String token = UsuarioCadastrarTest.cadastrarUsuarioToken();

        MetaModel metaLeitura = MetaFactory.adicionarMetaValida();
        MetaHelper.adicionarMeta(token, metaLeitura)
                .then()
                .log().body()
                .statusCode(201);

        MetaModel metaLeituraAtualizar = MetaFactory.atualizarMeta();
        MetaHelper.atualizarMeta(token, metaLeituraAtualizar)
                .then()
                .log().body()
                .statusCode(204);

    }

}
