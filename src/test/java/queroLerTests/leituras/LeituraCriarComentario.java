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
import utils.DataFakerUtils;
import utils.LivroHelper;
import utils.UsuarioHelper;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;

@ExtendWith(Setup.class)
public class LeituraCriarComentario extends BaseTest {

    @Test
    public void criarComentarioDiarioDoLivroComSucesso() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        int diarioId = criarDiarioId(token);

        LeituraComentarioModel leituraComentarioModel = LeituraComentarioFactory.criarLeituraComentario();
        Response responseLeituraComentario = LeituraClient.criarLeituraComentario(token, diarioId, leituraComentarioModel);
        responseLeituraComentario
                .then()
                .log().body()
                .statusCode(201);
    }

    @Test
    public void paginaInicialMaiorQuePaginaFinal() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        int diarioId = criarDiarioId(token);

        LeituraComentarioModel leituraComentarioModel = LeituraComentarioFactory.criarLeituraComentario();
        leituraComentarioModel.setPaginaInicial(leituraComentarioModel.getPaginaFinal()+1);
        Response responseLeituraComentario = LeituraClient.criarLeituraComentario(token, diarioId, leituraComentarioModel);
        responseLeituraComentario
                .then()
                .log().body()
                .statusCode(400)
                .body(equalTo("A página inicial deve ser menor que a página final."));
    }

    @Test
    public void paginaInicialEPaginaFinalEComentarioNulos() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        int diarioId = criarDiarioId(token);

        LeituraComentarioModel leituraComentarioModel = LeituraComentarioFactory.leituraTodosNulos();
        Response responseLeituraComentario = LeituraClient.criarLeituraComentario(token, diarioId, leituraComentarioModel);
        responseLeituraComentario
                .then()
                .log().body()
                .statusCode(500);
    }

    @Test
    public void criarComentarioComComentarioNulo() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        int diarioId = criarDiarioId(token);

        LeituraComentarioModel leituraComentarioModel = LeituraComentarioFactory.criarLeituraComentario();
        leituraComentarioModel.setComentario(null);
        Response responseLeituraComentario = LeituraClient.criarLeituraComentario(token, diarioId, leituraComentarioModel);
        responseLeituraComentario
                .then()
                .log().body()
                .statusCode(201);
    }

    @Test
    public void paginaFinalEComentarioNulos() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        int diarioId = criarDiarioId(token);

        LeituraComentarioModel leituraComentarioModel = LeituraComentarioFactory.criarLeituraComentario();
        leituraComentarioModel.setPaginaFinal(null);
        leituraComentarioModel.setComentario(null);
        Response responseLeituraComentario = LeituraClient.criarLeituraComentario(token, diarioId, leituraComentarioModel);
        responseLeituraComentario
                .then()
                .log().body()
                .statusCode(400);
    }

    @Test
    public void paginaInicialEComentarioNulo() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        int diarioId = criarDiarioId(token);

        LeituraComentarioModel leituraComentarioModel = LeituraComentarioFactory.criarLeituraComentario();
        leituraComentarioModel.setPaginaInicial(null);
        leituraComentarioModel.setComentario(null);
        Response responseLeituraComentario = LeituraClient.criarLeituraComentario(token, diarioId, leituraComentarioModel);
        responseLeituraComentario
                .then()
                .log().body()
                .statusCode(500);
    }

    @Test
    public void paginaInicialEPaginaFinalNulos() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        int diarioId = criarDiarioId(token);

        LeituraComentarioModel leituraComentarioModel = LeituraComentarioFactory.criarLeituraComentario();
        leituraComentarioModel.setPaginaInicial(null);
        leituraComentarioModel.setPaginaFinal(null);
        Response responseLeituraComentario = LeituraClient.criarLeituraComentario(token, diarioId, leituraComentarioModel);
        responseLeituraComentario
                .then()
                .log().body()
                .statusCode(500);
    }

    @Test
    public void paginaInicialValorNegativo() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        int diarioId = criarDiarioId(token);

        LeituraComentarioModel leituraComentarioModel = LeituraComentarioFactory.criarLeituraComentario();
        leituraComentarioModel.setPaginaInicial(-1);
        Response responseLeituraComentario = LeituraClient.criarLeituraComentario(token, diarioId, leituraComentarioModel);
        responseLeituraComentario
                .then()
                .log().body()
                .statusCode(400);
    }

    @Test
    public void paginaFinalValorNegativo() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        int diarioId = criarDiarioId(token);

        LeituraComentarioModel leituraComentarioModel = LeituraComentarioFactory.criarLeituraComentario();
        leituraComentarioModel.setPaginaFinal(-1);
        Response responseLeituraComentario = LeituraClient.criarLeituraComentario(token, diarioId, leituraComentarioModel);
        responseLeituraComentario
                .then()
                .log().body()
                .statusCode(400);
    }

    @Test
    public void paginaInicialEPaginaFinalZero() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        int diarioId = criarDiarioId(token);

        LeituraComentarioModel leituraComentarioModel = LeituraComentarioFactory.criarLeituraComentario();
        leituraComentarioModel.setPaginaInicial(0);
        leituraComentarioModel.setPaginaFinal(0);

        Response responseLeituraComentario = LeituraClient.criarLeituraComentario(token, diarioId, leituraComentarioModel);
        responseLeituraComentario
                .then()
                .log().body()
                .statusCode(400)
                .body(equalTo("A página inicial deve ser menor que a página final."));
    }

    private int criarDiarioId(String token) throws IOException {

        LivroModel livro = LivroFactory.criarLivroIsbn13();

        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro);

        responseLivro
                .then()
                .log().body()
                .statusCode(201);

        int livroId = responseLivro
                .jsonPath()
                .getInt("id");

        LeituraStatusModel leituraStatusModel = LeituraStatusFactory.criarLeituraLivroStatusQueroLer(livroId);

        Response responseLeitura = LeituraClient.criarLeituraStatus(token,leituraStatusModel);

        responseLeitura
                .then()
                .statusCode(201);

        DiarioModel diarioModel = DiarioFactory.criarDiarioLido(livroId);

        Response responseDiario = DiarioClient.criarDiario(token, diarioModel);

        responseDiario
                .then()
                .statusCode(201);

        Response responseDiarios = DiarioClient.buscarDiarioPorLivro(token, livroId);

        responseDiarios
                .then()
                .log().body()
                .statusCode(200);

        return responseDiarios
                .jsonPath()
                .getInt("id");
    }

}