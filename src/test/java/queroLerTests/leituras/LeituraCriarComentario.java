package queroLerTests.leituras;

import baseTest.BaseTest;
import clients.DiarioClient;
import clients.LeituraClient;
import factories.DiarioFactory;
import factories.LeituraComentarioFactory;
import factories.LeituraStatusFactory;
import factories.LivroFactory;
import io.restassured.response.Response;
import models.DiarioModel;
import models.LeituraComentarioModel;
import models.LeituraStatusModel;
import models.LivroModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import report.Setup;
import utils.LivroHelper;
import utils.UsuarioHelper;

import java.io.IOException;

@ExtendWith(Setup.class)
public class LeituraCriarComentario extends BaseTest {

    @Test
    public void criarComentarioDiarioDoLivro() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroIsbn13();
        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro);
        responseLivro
                .then()
                .log().body()
                .statusCode(201);

        int livroId = responseLivro.jsonPath().getInt("id");

        LeituraStatusModel leituraStatusModel = LeituraStatusFactory.criarLeituraLivroStatusQueroLer(livroId);
        Response responseLeitura = LeituraClient.criarLeituraStatus(token, leituraStatusModel);
        responseLeitura
                .then()
                .log().body()
                .statusCode(201);

        DiarioModel diarioModel = DiarioFactory.criarDiarioLido(livroId);
        Response responseDiario = DiarioClient.criarDiario(token, diarioModel);
        responseDiario
                .then()
                .log().body()
                .statusCode(201);

        Response responseDiarios = DiarioClient.buscarDiarioPorLivro(token, livroId);
        responseDiarios
                .then()
                .log().body()
                .statusCode(200);

        int diarioId = responseDiarios.jsonPath().getInt("id");

        LeituraComentarioModel leituraComentarioModel = LeituraComentarioFactory.criarLeituraComentario();
        Response responseLeituraComentario = LeituraClient.criarLeituraComentario(token, diarioId, leituraComentarioModel);
        responseLeituraComentario
                .then()
                .log().body()
                .statusCode(201);
    }

}
