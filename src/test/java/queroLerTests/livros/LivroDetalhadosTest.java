package queroLerTests.livros;

import baseTest.BaseTest;
import clients.LivrosClient;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import report.Setup;
import utils.UsuarioHelper;

import static utils.UsuarioHelper.logResposta;

@ExtendWith(Setup.class)
public class LivroDetalhadosTest extends BaseTest {

    @Test
    public void buscarLivrosDetalhados() {
        String token = UsuarioHelper.loginLeitor();

        Response responseLivro = LivrosClient.buscarLivroDetalhado(token);
        responseLivro
                .then()
                .log().body()
                .statusCode(200)
        ;

        logResposta("GET/livros/detalhados", responseLivro);
    }
}
