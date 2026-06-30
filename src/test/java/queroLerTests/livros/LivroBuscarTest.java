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
public class LivroBuscarTest extends BaseTest {

    @Test
    public void buscarLivros() {
        String token = UsuarioHelper.loginLeitor();

        Response responseLivro = LivrosClient.buscarLivros(token);
        responseLivro
                .then()
                .log().body()
                .statusCode(200)
        ;

        logResposta("GET/livros", responseLivro);
    }

    @Test
    public void buscarLivroComNumeroIsbn() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroIsbn10();
        File imagem = new File("src/test/resources/imagens/png.png");
        String isbn = livro.getIsbn();

        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro, imagem);
        responseLivro
                .then()
                .log().body()
                .statusCode(201);

        Response responseLivroIsbn = LivrosClient.buscarLivroIsbn(token, isbn);
        responseLivroIsbn
                .then()
                .log().body()
                .statusCode(200)
        ;

        logResposta("GET/livros", responseLivroIsbn);
    }

    @Test
    public void buscarLivroIsbnInexistente() {
        String token = UsuarioHelper.loginLeitor();

        String isbn = "9999999999";

        Response responseLivroIsbn = LivrosClient.buscarLivroIsbn(token, isbn);
        responseLivroIsbn
                .then()
                .log().body()
                .statusCode(404)
                .body(equalTo("Não há nenhum livro cadastrado com o código ISBN informado"))
        ;

        logResposta("GET/livros", responseLivroIsbn);
    }

    @Test
    public void buscarLivroIdCapaInexistente() {
        String token = UsuarioHelper.loginLeitor();

        String idCapa = "-1";

        Response responseLivroIsbn = LivrosClient.buscarLivroIdCapa(token, idCapa);
        responseLivroIsbn
                .then()
                .log().body()
                .statusCode(404)
                .body(equalTo("Livro não encontrado"))
        ;

        logResposta("GET/livros", responseLivroIsbn);
    }

}
