package queroLerTests.livros;

import baseTest.BaseTest;
import clients.LivrosClient;
import factories.LivroFactory;
import io.restassured.response.Response;
import models.LivroModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import report.Setup;
import utils.LivroHelper;
import utils.UsuarioHelper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static utils.UsuarioHelper.logResposta;

@ExtendWith(Setup.class)
public class LivroAtualizarTest extends BaseTest {

    @Test
    public void atualizacaoLivroComCapaImagem() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroIsbn10();
        File imagem = new File("src/test/resources/imagens/jpg.jpg");

        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro, imagem);
        responseLivro
                .then()
                .log().body()
                .statusCode(201);

        String isbn = livro.getIsbn();

        Response responseBusca = LivrosClient.buscarLivroIsbn(token, isbn);
        responseBusca
                .then()
                .log().body()
                .statusCode(200);

        String urlCapa = responseBusca.jsonPath().getString("capaUrl");
        Integer idCapa = Integer.parseInt(urlCapa.split("/")[2]);

        File novaImagem = new File("src/test/resources/imagens/png.png");

        Response responseAtualizacao = LivroHelper.atualizarCapaLivro(token, idCapa, novaImagem);

        responseAtualizacao
                .then()
                .log().body()
                .statusCode(204);

        logResposta("PUT/livros/" + idCapa + "/capa", responseAtualizacao);

    }

}
