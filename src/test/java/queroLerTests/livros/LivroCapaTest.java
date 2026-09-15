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

import static org.hamcrest.Matchers.equalTo;
import static utils.UsuarioHelper.logResposta;

@ExtendWith(Setup.class)
public class LivroCapaTest extends BaseTest {

    @Test
    public void buscarLivroPorCapa() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroIsbn10();
        File imagem = new File("src/test/resources/imagens/png.png");

        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro, imagem);
        responseLivro
                .then()
                .log().body()
                .statusCode(201);

        String urlCapa = responseLivro.jsonPath().getString("capaUrl");
        Integer idCapa = Integer.parseInt(urlCapa.split("/")[2]);

        Response responseBuscarCapa = LivroHelper.buscarCapaLivro(token, idCapa);

        responseBuscarCapa
                .then()
                .log().body()
                .statusCode(200);

        logResposta("PUT/livros/" + idCapa + "/capa", responseBuscarCapa);
    }

    @Test
    public void buscarLivroPorCapaNaoCadastrada() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroIsbn10();

        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro);
        responseLivro
                .then()
                .log().body()
                .statusCode(201);

        int id = responseLivro.jsonPath().getInt("id");

        Response responseBuscarCapa = LivroHelper.buscarCapaLivro(token, id);

        responseBuscarCapa
                .then()
                .log().body()
                .body(equalTo("Capa não cadastrada"))
                .statusCode(404);

        logResposta("PUT/livros/" + id + "/capa", responseBuscarCapa);
    }

    @Test
    public void atualizarLivroPorCapa() throws IOException {
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
