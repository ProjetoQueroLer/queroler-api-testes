package queroLerTests.livros;

import baseTest.BaseTest;
import clients.LivrosClient;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import report.Setup;
import utils.ConfigProperties;
import utils.DataFakerUtils;
import utils.UsuarioHelper;

import static org.hamcrest.Matchers.equalTo;
import static utils.UsuarioHelper.logResposta;

@ExtendWith(Setup.class)
public class LivroPesquisarTest extends BaseTest {

    @Test
    public void pesquisarLivroTituloMais256Caracteres() {
        String token = UsuarioHelper.loginLeitor();
        String titulo = DataFakerUtils.caracteresAleatorios("260");

        Response responseLivroTitulo = LivrosClient.pesquisarLivroTitulo(token, titulo);
        responseLivroTitulo
                .then()
                .log().body()
                .statusCode(404)
                .body(equalTo("Nenhum livro encontrado para essa busca!"))
        ;

        logResposta("GET/livros", responseLivroTitulo);
    }

    @Test
    public void pesquisarLivroTitulo() {
        String token = UsuarioHelper.loginLeitor();
        String titulo = ConfigProperties.get("titulo");

        Response responseLivroTitulo = LivrosClient.pesquisarLivroTitulo(token, titulo);
        responseLivroTitulo
                .then()
                .log().body()
                .statusCode(200)
//                .body(equalTo("Não há nenhum livro cadastrado com o código ISBN informado"))
        ;

        logResposta("GET/livros", responseLivroTitulo);
    }

    @Test
    public void pesquisarLivroTituloInexistente() {
        String token = UsuarioHelper.loginLeitor();
        String titulo = DataFakerUtils.nomeAleatorio();

        Response responseLivroTitulo = LivrosClient.pesquisarLivroTitulo(token, titulo);
        responseLivroTitulo
                .then()
                .log().body()
                .statusCode(404)
                .body(equalTo("Nenhum livro encontrado para essa busca!"))
        ;

        logResposta("GET/livros", responseLivroTitulo);
    }

    @Test
    public void pesquisarLivroAutor() {
        String token = UsuarioHelper.loginLeitor();
        String autores = ConfigProperties.get("autores");

        Response responseLivroAutor = LivrosClient.pesquisarLivroAutor(token, autores);
        responseLivroAutor
                .then()
                .log().body()
                .statusCode(200)
//                .body(equalTo("Não há nenhum livro cadastrado com o código ISBN informado"))
        ;

        logResposta("GET/livros", responseLivroAutor);
    }

    @Test
    public void pesquisarLivroAutorInexistente() {
        String token = UsuarioHelper.loginLeitor();
        String autores = DataFakerUtils.nomeAleatorio();

        Response responseLivroAutor = LivrosClient.pesquisarLivroAutor(token, autores);
        responseLivroAutor
                .then()
                .log().body()
                .statusCode(404)
                .body(equalTo("Nenhum livro encontrado para essa busca!"))
        ;

        logResposta("GET/livros", responseLivroAutor);
    }

    @Test
    public void pesquisarLivroEditora() {
        String token = UsuarioHelper.loginLeitor();
        String editora = ConfigProperties.get("editora");
        System.out.println(editora);
        Response responseLivroEditora = LivrosClient.pesquisarLivroEditora(token, editora);
        responseLivroEditora
                .then()
                .log().body()
                .statusCode(200)
//                .body(equalTo("Não há nenhum livro cadastrado com o código ISBN informado"))
        ;

        logResposta("GET/livros", responseLivroEditora);
    }

    @Test
    public void pesquisarLivroEditoraInexistente() {
        String token = UsuarioHelper.loginLeitor();
        String editora = DataFakerUtils.nomeAleatorio();

        Response responseLivroEditora = LivrosClient.pesquisarLivroEditora(token, editora);
        responseLivroEditora
                .then()
                .log().body()
                .statusCode(404)
                .body(equalTo("Nenhum livro encontrado para essa busca!"))
        ;

        logResposta("GET/livros", responseLivroEditora);
    }

}
