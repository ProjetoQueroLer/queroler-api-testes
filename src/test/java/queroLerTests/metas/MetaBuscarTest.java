package queroLerTests.metas;

import baseTest.BaseTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import factories.LivroFactory;
import factories.MetaFactory;
import io.restassured.response.Response;
import models.LivroModel;
import models.MetaModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import queroLerTests.livros.LivroCadastrarTest;
import queroLerTests.usuarios.UsuarioCadastrarTest;
import report.Setup;
import utils.LivroHelper;
import utils.MetaHelper;

import java.io.IOException;

@ExtendWith(Setup.class)
public class MetaBuscarTest extends BaseTest {

    @Test
    public void buscarMetaLeitor() throws IOException {
        String token = UsuarioCadastrarTest.cadastrarUsuarioToken();

        LivroModel livro = LivroFactory.criarLivroIsbn13();
        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro);
        responseLivro
                .then()
                .log().body()
                .statusCode(201);

        int livroId = responseLivro.jsonPath().getInt("id");

        MetaModel metaLeitura = MetaFactory.adicionarMetaValida();

        MetaHelper.adicionarMeta(token, metaLeitura)
                .then()
                .log().body()
                .statusCode(201);

        MetaHelper.adicionarLivroMeta(token, livroId)
                .then()
                .log().body()
                .statusCode(204);

        MetaHelper.buscarMeta(token)
                .then()
                .log().body()
                .statusCode(200);
    }

}
