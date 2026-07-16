package queroLerTests.livros;

import baseTest.BaseTest;
import clients.LivrosClient;
import factories.LivroFactory;
import io.restassured.response.Response;
import models.LivroModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import report.Setup;
import utils.ConfigProperties;
import utils.DataFakerUtils;
import utils.LivroHelper;
import utils.UsuarioHelper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
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
    public void pesquisarLivroTitulo() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroIsbn13();
        LivroHelper.criarLivroCadastrar(token, livro);
        String titulo = livro.getTitulo();

        Response responseLivroTitulo = LivrosClient.pesquisarLivroTitulo(token, titulo);
        responseLivroTitulo
                .then()
                .log().body()
                .statusCode(200)
        ;

        assertEquals(1, responseLivroTitulo.jsonPath().getList("content").size());
        assertEquals(1, responseLivroTitulo.jsonPath().getInt("totalElements"));

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
    public void pesquisarLivroAutor() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroIsbn13();
        LivroHelper.criarLivroCadastrar(token, livro);
        String autores = livro.getAutores().get(0).getNome();

        Response responseLivroAutor = LivrosClient.pesquisarLivroAutor(token, autores);
        responseLivroAutor
                .then()
                .log().body()
                .statusCode(200)
        ;

        assertEquals(1, responseLivroAutor.jsonPath().getList("content").size());
        assertEquals(1, responseLivroAutor.jsonPath().getInt("totalElements"));

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
    public void pesquisarLivroEditora() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroIsbn13();
        LivroHelper.criarLivroCadastrar(token, livro);
        String editora = livro.getEditora();

        Response responseLivroEditora = LivrosClient.pesquisarLivroEditora(token, editora);
        responseLivroEditora
                .then()
                .log().body()
                .statusCode(200)
        ;

        assertEquals(1, responseLivroEditora.jsonPath().getList("content").size());
        assertEquals(1, responseLivroEditora.jsonPath().getInt("totalElements"));

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

    @Test
    public void pesquisarLivroExisteCamposObrigatorio() {
        String token = UsuarioHelper.loginLeitor();

        Response response = LivrosClient.pesquisarLivro(token, "teste");

        response.then()
                .statusCode(200);

        assertFalse(response.jsonPath().getList("content").isEmpty());
        assertTrue(response.jsonPath().getInt("totalElements") > 0);

        assertNotNull(response.jsonPath().getString("content[0].titulo"));
        assertNotNull(response.jsonPath().getString("content[0].urlCapaDoLivro"));
        assertNotNull(response.jsonPath().getList("content[0].autores"));
        assertNotNull(response.jsonPath().getString("content[0].editora"));
        assertTrue(response.jsonPath().getInt("content[0].numeroDePaginas") > 0);
        assertNotNull(response.jsonPath().getString("content[0].anoDePublicacao"));

    }

    @Test
    public void pesquisarLivroOrdenadoPorDataCadastroDecrescente() {
        String token = UsuarioHelper.loginLeitor();

        Response response = LivrosClient.pesquisarLivro(token, "teste");

        response.then()
                .statusCode(200);

        int quantidade = response.jsonPath().getList("content").size();
        System.out.println("quantidade: "+quantidade);
        // O teste só faz sentido se houver pelo menos 2 livros
        assertTrue(quantidade >= 2);

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        for (int i = 0; i < quantidade - 1; i++) {

            String tituloAtual = response.jsonPath().getString("content[" + i + "].titulo");
            String dataAtual = response.jsonPath().getString("content[" + i + "].dataDeCadastro");

            String proximoTitulo = response.jsonPath().getString("content[" + (i + 1) + "].titulo");
            String proximaData = response.jsonPath().getString("content[" + (i + 1) + "].dataDeCadastro");

            LocalDateTime dataAtual1 = LocalDateTime.parse(
                    dataAtual,
                    formatter
            );

            LocalDateTime proximaData1 = LocalDateTime.parse(
                    proximaData,
                    formatter
            );

            System.out.println("Titulo  : " + tituloAtual);
            System.out.println("Atual   : " + dataAtual);
            System.out.println("Titulo  : " + proximoTitulo);
            System.out.println("Próxima : " + proximaData);
            System.out.println("isAfter: " + dataAtual1.isAfter(proximaData1));

            assertTrue(
                    dataAtual1.isAfter(proximaData1)
                            || dataAtual1.isEqual(proximaData1),
                    "Os livros não estão em ordem decrescente de cadastro."
            );
        }
    }

    @Test
    public void pesquisarLivroOrdenadoPorDataCadastroCrescente() {
        String token = UsuarioHelper.loginLeitor();

        Response response = LivrosClient.pesquisarLivro(token, "teste");

        response.then()
                .statusCode(200);

        int quantidade = response.jsonPath().getList("content").size();

        if (quantidade < 2) {
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        for (int i = 0; i < quantidade - 1; i++) {

            System.out.println(
                    "Livro: " +
                            response.jsonPath().getString("content[" + i + "].titulo") +
                            " | Data: " +
                            response.jsonPath().getString("content[" + i + "].dataDeCadastro")
            );

            LocalDateTime dataAtual = LocalDateTime.parse(
                    response.jsonPath().getString("content[" + i + "].dataDeCadastro"),
                    formatter
            );

            LocalDateTime proximaData = LocalDateTime.parse(
                    response.jsonPath().getString("content[" + (i + 1) + "].dataDeCadastro"),
                    formatter
            );

            assertTrue(
                    dataAtual.isBefore(proximaData) || dataAtual.isEqual(proximaData),
                    "Os livros não estão em ordem crescente de cadastro."
            );
        }
    }

}
