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
public class LeituraDeletarStatusTest extends BaseTest {

    @Test
    public void livroDeletarStatusLivroInexistente() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        criarLivroId(token);

        Response responseLeitura = LeituraClient.deletarLeitura(token, -1);

        responseLeitura
                .then()
                .log().body()
                .statusCode(404)
                .body(equalTo("Leitura não encontrada para este usuário e livro."));
    }

    @Test
    public void livroDeletarStatusLivroExistente() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        int livroId = criarLivroId(token);

        LeituraStatusModel leituraStatusModel = LeituraStatusFactory.criarLeituraLivroStatusLivroLido(livroId);
        Response responseLeitura = LeituraClient.criarLeituraStatus(token, leituraStatusModel);
        responseLeitura
                .then()
                .log().body()
                .statusCode(201);

        Response responseLeituraDeletar = LeituraClient.deletarLeitura(token, livroId);

        responseLeituraDeletar
                .then()
                .log().body()
                .statusCode(204);
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
