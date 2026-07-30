package queroLerTests.leituras;

import baseTest.BaseTest;
import clients.LeituraClient;
import factories.LeituraFactory;
import factories.LivroFactory;
import io.restassured.response.Response;
import models.LeituraModel;
import models.LivroModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import report.Setup;
import utils.LivroHelper;
import utils.UsuarioHelper;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;

@ExtendWith(Setup.class)
public class LeituraDeletarStatusTest extends BaseTest {

    @Test
    public void livroDeletarStatusLivroInexistente() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroIsbn13();
        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro);
        responseLivro
                .then()
                .log().body()
                .statusCode(201);

        Response responseLeitura = LeituraClient.deletarLeitura(token, -1);

        responseLeitura
                .then()
                .log().body()
                .statusCode(404)
                .body(equalTo("Leitura não encontrada para este usuário e livro."));
    }

    @Test
    public void livroDeletarStatusLivroExistente() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroIsbn13();
        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro);
        responseLivro
                .then()
                .log().body()
                .statusCode(201);

        LeituraModel leituraModel = LeituraFactory.criarLeituraLivroStatusLivroLido(responseLivro.jsonPath().getInt("id"));
        Response responseLeitura = LeituraClient.criarLeitura(token, leituraModel);
        responseLeitura
                .then()
                .log().body()
                .statusCode(201);

        Response responseLeituraDeletar = LeituraClient.deletarLeitura(token, responseLivro.jsonPath().getInt("id"));

        responseLeituraDeletar
                .then()
                .log().body()
                .statusCode(204);
    }
}
