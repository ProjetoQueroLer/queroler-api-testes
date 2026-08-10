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
    public void livroDiarioIdComentario() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroIsbn13();
        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro);
        responseLivro
                .then()
                .log().body()
                .statusCode(201);

        LeituraStatusModel leituraStatusModel = LeituraStatusFactory.criarLeituraLivroStatusQueroLer(responseLivro.jsonPath().getInt("id"));
        Response responseLeitura = LeituraClient.criarLeituraStatus(token, leituraStatusModel);
        responseLeitura
                .then()
                .log().body()
                .statusCode(201);

        DiarioModel diarioModel = DiarioFactory.criarDiarioLido(responseLivro.jsonPath().getInt("id"));
//        DiarioModel diarioModel = DiarioFactory.criarDiarioLendo(responseLivro.jsonPath().getInt("id"));
        Response responseDiario = DiarioClient.criarDiario(token, diarioModel);
        responseDiario
                .then()
                .log().body()
                .statusCode(201);

//        System.out.println("Diario do ID:" + responseDiario.jsonPath().getInt("id"));

        LeituraComentarioModel leituraComentarioModel = LeituraComentarioFactory.criarLeituraComentario();
//        Response responseLeituraComentario = LeituraClient.criarLeituraComentario(token, responseDiario.jsonPath().getInt("id"));
        Response responseLeituraComentario = LeituraClient.criarLeituraComentario(token, 6, leituraComentarioModel);
        responseLeituraComentario
                .then()
                .log().body()
                .statusCode(201);
    }

    @Test
    public void livroDiarioIdComentario1() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroIsbn13();
        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro);
        responseLivro
                .then()
                .log().body()
                .statusCode(201);

        LeituraStatusModel leituraStatusModel = LeituraStatusFactory.criarLeituraLivroStatusQueroLer(responseLivro.jsonPath().getInt("id"));
        Response responseLeitura = LeituraClient.criarLeituraStatus(token, leituraStatusModel);
        responseLeitura
                .then()
                .log().body()
                .statusCode(201);

        DiarioModel diarioModel = DiarioFactory.criarDiarioLido(responseLivro.jsonPath().getInt("id"));
//        DiarioModel diarioModel = DiarioFactory.criarDiarioLendo(responseLivro.jsonPath().getInt("id"));
        Response responseDiario = DiarioClient.criarDiario(token, diarioModel);
        responseDiario
                .then()
                .log().body()
                .statusCode(201);

        Response responseDiarios = DiarioClient.buscarDiarioPorLivro(token, responseLivro.jsonPath().getInt("id"));

        responseDiarios
                .then()
                .log().body()
                .statusCode(200);

        int diarioId = responseDiarios.jsonPath().getInt("id");

        LeituraComentarioModel leituraComentarioModel = LeituraComentarioFactory.criarLeituraComentario();
//        Response responseLeituraComentario = LeituraClient.criarLeituraComentario(token, responseDiario.jsonPath().getInt("id"));
        Response responseLeituraComentario = LeituraClient.criarLeituraComentario(token, diarioId, leituraComentarioModel);
        responseLeituraComentario
                .then()
                .log().body()
                .statusCode(201);
    }

}
