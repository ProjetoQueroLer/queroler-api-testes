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
    public void buscarLivroPorId() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroIsbn10();

        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro);
        int idLivro = responseLivro.jsonPath().getInt("id");
        responseLivro
                .then()
                .log().body()
                .statusCode(201);

        Response responseLivroId = LivrosClient.buscarLivroId(token, idLivro);
        responseLivroId
                .then()
                .log().body()
                .statusCode(200)
        ;

        logResposta("GET/livros/buscar/" + idLivro, responseLivroId);
    }

    @Test
    public void buscarLivroPorIdInexistente() {
        String token = UsuarioHelper.loginLeitor();

        int idLivro = -1;

        Response responseLivroId = LivrosClient.buscarLivroId(token, idLivro);
        responseLivroId
                .then()
                .log().body()
                .body(equalTo("Livro não encontrado."))
                .statusCode(404)
        ;

        logResposta("GET/livros/buscar/" + idLivro, responseLivroId);
    }

    @Test
    public void buscarLivroPorIsbn() throws IOException {
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

        logResposta("GET/livros/buscar/"+isbn, responseLivroIsbn);
    }

    @Test
    public void buscarLivroPorIsbnInexistente() {
        String token = UsuarioHelper.loginLeitor();

        String isbn = "9999999999";

        Response responseLivroIsbn = LivrosClient.buscarLivroIsbn(token, isbn);
        responseLivroIsbn
                .then()
                .log().body()
                .statusCode(404)
                .body(equalTo("Não há nenhum livro cadastrado com o código ISBN informado"))
        ;

        logResposta("GET/livros/buscar/"+isbn, responseLivroIsbn);
    }

}
