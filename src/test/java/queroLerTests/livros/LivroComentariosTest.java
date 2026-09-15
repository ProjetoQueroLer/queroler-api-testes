package queroLerTests.livros;

import baseTest.BaseTest;
import clients.LivrosClient;
import factories.LivroFactory;
import io.restassured.response.Response;
import models.LivroModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import report.Setup;
import utils.LivroHelper;
import utils.UsuarioHelper;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;

@ExtendWith(Setup.class)
public class LivroComentariosTest extends BaseTest {

    @Test
    public void listarComentariosLivroSemComentarios() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroIsbn13();

        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro);

        responseLivro
                .then()
                .statusCode(201);

        int livroId = responseLivro.jsonPath().getInt("id");

        Response response = LivrosClient.listarComentariosPorLivro(token, livroId);

        response
                .then()
                .log().body()
                .statusCode(200)
                .body("size()", equalTo(0));
    }

}
