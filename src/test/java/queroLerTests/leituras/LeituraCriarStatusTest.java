package queroLerTests.leituras;

import baseTest.BaseTest;
import clients.LeituraClient;
import factories.LeituraStatusFactory;
import factories.LivroFactory;
import io.restassured.response.Response;
import models.LeituraStatusModel;
import models.LivroModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import report.Setup;
import utils.LivroHelper;
import utils.UsuarioHelper;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;

@ExtendWith(Setup.class)
public class LeituraCriarStatusTest extends BaseTest {

    @Test
    public void livroInexistenteStatus() {
        String token = UsuarioHelper.loginLeitor();
        LeituraStatusModel leituraStatusModel = LeituraStatusFactory.criarLeituraLivroInexistente();

        Response responseLeitura = LeituraClient.criarLeituraStatus(token, leituraStatusModel);

        responseLeitura
                .then()
                .log().body()
                .statusCode(404)
                .body(equalTo("Livro não cadastrado."));

    }

    @Test
    public void livroAdicionarStatusQueroLer() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        int livroId = criarLivroId(token);

        LeituraStatusModel leituraStatusModel = LeituraStatusFactory.criarLeituraLivroStatusQueroLer(livroId);

        Response responseLeitura = LeituraClient.criarLeituraStatus(token, leituraStatusModel);

        responseLeitura
                .then()
                .log().body()
                .statusCode(201);

    }

    @Test
    public void livroAdicionarStatusEstouLendo() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        int livroId = criarLivroId(token);

        LeituraStatusModel leituraStatusModel = LeituraStatusFactory.criarLeituraLivroStatusEstouLendo(livroId);

        Response responseLeitura = LeituraClient.criarLeituraStatus(token, leituraStatusModel);

        responseLeitura
                .then()
                .log().body()
                .statusCode(201);
    }

    @Test
    public void livroAdicionarStatusLivroLido() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        int livroId = criarLivroId(token);

        LeituraStatusModel leituraStatusModel = LeituraStatusFactory.criarLeituraLivroStatusLivroLido(livroId);

        Response responseLeitura = LeituraClient.criarLeituraStatus(token, leituraStatusModel);

        responseLeitura
                .then()
                .log().body()
                .statusCode(201);
    }

    @Test
    public void livroAdicionarStatusLivroAbandonado() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        int livroId = criarLivroId(token);

        LeituraStatusModel leituraStatusModel = LeituraStatusFactory.criarLeituraLivroStatusLivroAbandonado(livroId);

        Response responseLeitura = LeituraClient.criarLeituraStatus(token, leituraStatusModel);

        responseLeitura
                .then()
                .log().body()
                .statusCode(409)
                .body(equalTo("Transição inválida, para o estado atual somente as transições Quero ler, lendo e lidos podem ser realizadas"));
    }

    private int criarLivroId(String token) throws IOException {

        LivroModel livro = LivroFactory.criarLivroIsbn13();
        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro);
        responseLivro
                .then()
                .statusCode(201);

        return responseLivro.jsonPath().getInt("id");
    }
}
