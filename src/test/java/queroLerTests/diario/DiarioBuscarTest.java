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

@ExtendWith(Setup.class)
public class DiarioBuscarTest extends BaseTest {

    @Test
    public void buscarDiarioLivroId() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroCriado livroCriado = criarLivro(token);

        DiarioModel diarioModel = DiarioFactory.criarDiarioLido(livroCriado.livroId(), livroCriado.numeroDePaginas());
        Response responseDiario = DiarioClient.criarDiario(token, diarioModel);
        responseDiario
                .then()
                .log().body()
                .statusCode(201);

        Response responseBuscarDiario = DiarioClient.buscarDiarioLivroId(token, livroCriado.livroId());
        responseBuscarDiario
                .then()
                .log().body()
                .statusCode(200);
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
