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
import models.LivroModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import report.Setup;
import utils.LivroHelper;
import utils.UsuarioHelper;

import java.io.IOException;

@ExtendWith(Setup.class)
public class DiarioBuscarTest extends BaseTest {

    @Test
    public void buscarDiarioLivroId() throws IOException {
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
        Response responseDiario = DiarioClient.criarDiario(token, diarioModel);
        responseDiario
                .then()
                .log().body()
                .statusCode(201);

        Response responseBuscarDiario = DiarioClient.buscarDiarioLivroId(token, responseLivro.jsonPath().getInt("id"));
        responseBuscarDiario
                .then()
                .log().body()
                .statusCode(200);
    }

}
