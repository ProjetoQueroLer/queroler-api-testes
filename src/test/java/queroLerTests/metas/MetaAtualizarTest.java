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
import queroLerTests.usuarios.UsuarioCadastrarTest;
import report.Setup;
import utils.LivroHelper;
import utils.MetaHelper;
import utils.UsuarioHelper;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;

@ExtendWith(Setup.class)
public class MetaAtualizarTest extends BaseTest {

    @Test
    public void atualizarMeta() {
        String token = UsuarioHelper.loginLeitor();

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

    @Test
    public void atualizarMetaAdicionarLivroId() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroIsbn13();
        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro);
        responseLivro
                .then()
                .statusCode(201);

        int livroId = responseLivro.jsonPath().getInt("id");

        MetaHelper.atualizarMetaAdicionarLivro(token, livroId)
                .then()
                .log().body()
                .statusCode(204);

    }

    @Test
    public void atualizarMetaAdicionarLivroIdJaExistente() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroIsbn13();
        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro);
        responseLivro
                .then()
                .statusCode(201);

        int livroId = responseLivro.jsonPath().getInt("id");

        MetaHelper.atualizarMetaAdicionarLivro(token, livroId)
                .then()
                .log().body()
                .statusCode(204);

        MetaHelper.atualizarMetaAdicionarLivro(token, livroId)
                .then()
                .log().body()
                .statusCode(409)
                .body(equalTo("Livro já adicionado na meta deste ano!"));

    }

    @Test
    public void atualizarMetaAdicionarLivroIdInexistente() {
        String token = UsuarioHelper.loginLeitor();

        int livroId = 99999;

        MetaHelper.atualizarMetaAdicionarLivro(token, livroId)
                .then()
                .log().body()
                .statusCode(404)
                .body(equalTo("Livro não cadastrado."));
    }

}
