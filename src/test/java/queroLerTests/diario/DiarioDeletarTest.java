package queroLerTests.diario;

import baseTest.BaseTest;
import clients.DiarioClient;
import clients.LeituraClient;
import factories.DiarioFactory;
import factories.LeituraStatusFactory;
import factories.LivroFactory;
import io.restassured.response.Response;
import models.DiarioModel;
import models.LeituraStatusModel;
import models.LivroCriado;
import models.LivroModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import report.Setup;
import utils.LivroHelper;
import utils.UsuarioHelper;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;

@ExtendWith(Setup.class)
public class DiarioDeletarTest extends BaseTest {

    @Test
    public void deletarDiarioLivroId() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroCriado livroCriado = criarLivro(token);

        DiarioModel diarioModel = DiarioFactory.criarDiarioLido(livroCriado.livroId(), livroCriado.numeroDePaginas());
        Response responseDiario = DiarioClient.criarDiario(token, diarioModel);
        responseDiario
                .then()
                .log().body()
                .statusCode(201);
        int diarioId = responseDiario.jsonPath().getInt("id");

        Response responseDiarioDeletar = DiarioClient.deletarDiarioPorLivro(token, diarioId);
        responseDiarioDeletar
                .then()
                .log().body()
                .statusCode(204);

    }

    @Test
    public void deletarDiarioLivroIdInexistente() {
        String token = UsuarioHelper.loginLeitor();

        int diarioId = -1;

        Response responseDiario = DiarioClient.deletarDiarioPorLivro(token, diarioId);
        responseDiario
                .then()
                .log().body()
                .statusCode(404)
                .body(equalTo("Diário de leitura não encontrado."));

    }

    private LivroCriado criarLivro(String token) throws IOException {
        LivroModel livroModel = LivroFactory.criarLivroIsbn13();
        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livroModel);
        responseLivro
                .then()
                .statusCode(201);
        int livroId = responseLivro.jsonPath().getInt("id");

        LeituraStatusModel leituraStatusModel = LeituraStatusFactory.criarLeituraLivroStatusQueroLer(livroId);
        Response responseLeitura = LeituraClient.criarLeituraStatus(token, leituraStatusModel);
        responseLeitura
                .then()
                .statusCode(201);

        return new LivroCriado(livroId, livroModel.getNumeroDePaginas());
    }

}
