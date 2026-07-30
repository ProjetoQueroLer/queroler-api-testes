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
import utils.LeituraHelper;
import utils.LivroHelper;
import utils.UsuarioHelper;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;

@ExtendWith(Setup.class)
public class LeituraCriarStatusTest extends BaseTest {

    @Test
    public void livroInexistenteStatus() {
        String token = UsuarioHelper.loginLeitor();
        LeituraModel leituraModel = LeituraFactory.criarLeituraLivroInexistente();

        Response responseLeitura = LeituraClient.criarLeitura(token, leituraModel);

        responseLeitura
                .then()
                .log().body()
                .statusCode(404)
                .body(equalTo("Livro não cadastrado."));

    }

    @Test
    public void livroAdicionarStatusQueroLer() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroIsbn13();
        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro);
        responseLivro
                .then()
                .log().body()
                .statusCode(201);

        LeituraModel leituraModel = LeituraFactory.criarLeituraLivroStatusQueroLer(responseLivro.jsonPath().getInt("id"));

        Response responseLeitura = LeituraClient.criarLeitura(token, leituraModel);

        responseLeitura
                .then()
                .log().body()
                .statusCode(201);

    }

    @Test
    public void livroAdicionarStatusEstouLendo() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroIsbn13();
        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro);
        responseLivro
                .then()
                .log().body()
                .statusCode(201);

        LeituraModel leituraModel = LeituraFactory.criarLeituraLivroStatusEstouLendo(responseLivro.jsonPath().getInt("id"));

        Response responseLeitura = LeituraClient.criarLeitura(token, leituraModel);

        responseLeitura
                .then()
                .log().body()
                .statusCode(201);
    }

    @Test
    public void livroAdicionarStatusLivroLido() throws IOException {
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
    }

    @Test
    public void livroAdicionarStatusLivroAbandonado() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroIsbn13();
        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro);
        responseLivro
                .then()
                .log().body()
                .statusCode(201);

        LeituraModel leituraModel = LeituraFactory.criarLeituraLivroStatusLivroAbandonado(responseLivro.jsonPath().getInt("id"));

        Response responseLeitura = LeituraClient.criarLeitura(token, leituraModel);

        responseLeitura
                .then()
                .log().body()
                .statusCode(409)
                .body(equalTo("Transição inválida, para o estado atual somente as transições Quero ler, lendo e lidos podem ser realizadas"));
    }

}
