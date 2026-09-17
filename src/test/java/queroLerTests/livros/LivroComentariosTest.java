package queroLerTests.livros;

import baseTest.BaseTest;
import clients.DiarioClient;
import clients.LeituraClient;
import clients.LivrosClient;
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

    @Test
    public void buscarLivroComentariosPorId() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        int livroId = criarLivroId(token);

        DiarioModel diarioModel = DiarioFactory.criarDiarioLido(livroId);
        Response responseDiario = DiarioClient.criarDiario(token, diarioModel);
        responseDiario
                .then()
                .statusCode(201);

        int diarioId = responseDiario.jsonPath().getInt("id");

        LeituraComentarioModel leituraComentarioModel = LeituraComentarioFactory.criarLeituraComentario();
        Response responseLeituraComentario = LeituraClient.criarLeituraComentario(token, diarioId, leituraComentarioModel);
        responseLeituraComentario
                .then()
                .statusCode(201);

        Response response = LivrosClient.listarComentariosPorLivro(token, livroId);

        response
                .then()
                .log().body()
                .statusCode(200)
        ;

    }

    private int criarLivroId(String token) throws IOException {

        LivroModel livro = LivroFactory.criarLivroIsbn13();
        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro);
        responseLivro
                .then()
                .statusCode(201);

        int livroId = responseLivro.jsonPath().getInt("id");

        LeituraStatusModel leituraStatusModel = LeituraStatusFactory.criarLeituraLivroStatusQueroLer(livroId);
        Response responseLeitura = LeituraClient.criarLeituraStatus(token, leituraStatusModel);
        responseLeitura
                .then()
                .statusCode(201);

        return livroId;
    }

}
