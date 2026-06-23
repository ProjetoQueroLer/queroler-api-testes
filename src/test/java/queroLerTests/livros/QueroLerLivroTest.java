package queroLerTests.livros;

import baseTest.BaseTest;
import clients.LivrosClient;
import clients.LoginClient;
import factories.LivroFactory;
import io.restassured.response.Response;
import models.LivroModel;
import models.LoginModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import report.Setup;
import utlis.ConfigProperties;
import utlis.LivroHelper;
import utlis.UsuarioHelper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static utlis.UsuarioHelper.logResposta;

@ExtendWith(Setup.class)
public class QueroLerLivroTest extends BaseTest {

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
    public void cadastrarLivroIsbn13Digitos() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroIsbn13();

        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro);
        responseLivro
                .then()
                .log().body()
                .statusCode(201);

        logResposta("POST/livros", responseLivro);

    }

    @Test
    public void cadastrarLivroIsbn13DigitosComImagem() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroIsbn13();
        File imagem = new File("src/test/resources/imagens/png.png");

        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro, imagem);
        responseLivro
                .then()
                .log().body()
                .statusCode(201);

        logResposta("POST/livros", responseLivro);

    }

    @Test
    public void cadastrarLivroIsbn10Digitos() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroIsbn10();

        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro);
        responseLivro
                .then()
                .log().body()
                .statusCode(201);

        logResposta("POST/livros", responseLivro);

    }

    @Test
    public void cadastrarLivroIsbn10DigitosComImagem() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroIsbn10();
        File imagem = new File("src/test/resources/imagens/png.png");

        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro, imagem);
        responseLivro
                .then()
                .log().body()
                .statusCode(201);

        logResposta("POST/livros", responseLivro);

    }

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

        Response responseBusca = LivrosClient.buscarLivros(token);

        String titulo = livro.getTitulo();

        List<Map<String, Object>> livros =
                responseBusca.jsonPath().getList("content");

        String urlCapa = null;

        for (Map<String, Object> item : livros) {

            if (titulo.trim().equals(item.get("titulo").toString().trim())) {
                urlCapa = (String) item.get("urlCapaDoLivro");
                break;
            }
        }

        if (urlCapa == null) {
            throw new RuntimeException("Livro não encontrado no GET /livros: " + titulo);
        }

        Integer idLivro = Integer.parseInt(urlCapa.split("/")[2]);

        File novaImagem = new File("src/test/resources/imagens/png.png");

        Response responseAtualizacao = LivroHelper.atualizarCapaLivro(token, idLivro, novaImagem);

        responseAtualizacao
                .then()
                .log().body()
                .statusCode(204);

        logResposta("PUT/livros/" + idLivro + "/capa", responseAtualizacao);

    }

//    @Test
    public void cadastrarLivroIsbn10DigitosComImagemMais10MB() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroIsbn10();
        File arquivo = new File("src/test/resources/imagens/foto_png.png");
        System.out.println(arquivo.length());
        System.out.println(arquivo.length() / 1024.0 / 1024.0 + " MB");

        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro, arquivo);
        responseLivro
                .then()
                .log().body()
                .statusCode(413);

        logResposta("POST/livros", responseLivro);

    }

    @Test
    public void cadastrarLivroIsbn10DigitosComArquivoInvalido() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroIsbn10();
        File arquivo = new File("src/test/resources/imagens/pdf.pdf");

        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro, arquivo);
        responseLivro
                .then()
                .log().body()
                .statusCode(415)
                .body(equalTo("Formato inválido. Use JPG ou PNG"));

        logResposta("POST/livros", responseLivro);

    }

    @Test
    public void cadastrarLivroIsbnInvalido() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroIsbnInvalido();

        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro);
        responseLivro
                .then()
                .log().body()
                .statusCode(400)
                .body("[0].mensagem", equalTo("ISBN deve conter apenas números e ter 10 ou 13 dígitos"))
                .body("[0].campo", equalTo("isbn"))
        ;

        logResposta("POST/livros", responseLivro);

    }

    @Test
    public void cadastrarLivroIsbnComLetra() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroIsbnComLetra();

        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro);
        responseLivro
                .then()
                .log().body()
                .statusCode(400)
                .body("[0].mensagem", equalTo("ISBN deve conter apenas números e ter 10 ou 13 dígitos"))
                .body("[0].campo", equalTo("isbn"))
        ;

        logResposta("POST/livros", responseLivro);

    }

    @Test
    public void cadastrarLivroCampoTituloVazio() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroTituloVazio();

        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro);
        responseLivro
                .then()
                .log().body()
                .statusCode(400)
                .body("[0].mensagem", equalTo("O campo não pode estar vazio"))
                .body("[0].campo", equalTo("titulo"))
        ;

        logResposta("POST/livros", responseLivro);

    }

    @Test
    public void cadastrarLivroCampoIsbnVazio() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroIsbnVazio();

        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro);
        responseLivro
                .then()
                .log().body()
                .statusCode(400)
                .body("[0].mensagem", equalTo("O campo não pode estar vazio"))
                .body("[0].campo", equalTo("isbn"))
                .body("[1].mensagem", equalTo("ISBN deve conter apenas números e ter 10 ou 13 dígitos"))
                .body("[1].campo", equalTo("isbn"))
        ;

        logResposta("POST/livros", responseLivro);

    }

    @Test
    public void cadastrarLivroCampoEditoraVazio() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroEditoraVazio();

        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro);
        responseLivro
                .then()
                .log().body()
                .statusCode(400)
                .body("[0].mensagem", equalTo("O campo não pode estar vazio"))
                .body("[0].campo", equalTo("editora"))
        ;

        logResposta("POST/livros", responseLivro);

    }

    @Test
    public void cadastrarLivroCampoAnoPublicacaoVazio() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroAnoPublicacaoVazio();

        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro);
        responseLivro
                .then()
                .log().body()
                .statusCode(400)
                .body("[0].mensagem", equalTo("O ano de publicação é obrigatório"))
                .body("[0].campo", equalTo("anoDePublicacao"))
        ;

        logResposta("POST/livros", responseLivro);

    }

    @Test
    public void cadastrarLivroCampoPaginasVazio() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroPaginasVazio();

        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro);
        responseLivro
                .then()
                .log().body()
                .statusCode(400)
                .body("[0].mensagem", equalTo("O campo não pode ser nulo"))
                .body("[0].campo", equalTo("numeroDePaginas"))
        ;

        logResposta("POST/livros", responseLivro);

    }

    @ParameterizedTest
    @ValueSource(ints = {
            -1,
            0
    })
    public void cadastrarLivroCampoPaginasNumeroNegativoEZero(Integer numero) throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroPaginasNumeroNegativoEZero(numero);

        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro);
        responseLivro
                .then()
                .log().body()
                .statusCode(400)
                .body("[0].mensagem", equalTo("O campo não pode ser numero negativo"))
                .body("[0].campo", equalTo("numeroDePaginas"))
        ;

        logResposta("POST/livros", responseLivro);

    }

    @Test
    public void cadastrarLivroCampoIdiomaVazio() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroIdiomaVazio();

        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro);
        responseLivro
                .then()
                .log().body()
                .statusCode(400)
                .body(equalTo("Valor inválido. Valores permitidos: [PORTUGUES, INGLES, ESPANHOL, FRANCES, ALEMAO, ITALIANO, JAPONES, CHINES, COREANO, ARABE, RUSSO, HINDI, HOLANDES, SUECO, DINAMARQUES, FINLANDES, NORUEGUES, POLONES, TURCO, GREGO, HEBRAICO, TAILANDES, VIETNAMITA, INDONESIO, UCRANIANO, TCHECO, HUNGARO]"))
        ;

        logResposta("POST/livros", responseLivro);

    }

    @Test
    public void cadastrarLivroCampoSinopseVazio() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroSinopseVazio();

        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro);
        responseLivro
                .then()
                .log().body()
                .statusCode(400)
                .body("[1].mensagem", equalTo("O campo não pode estar vazio"))
                .body("[1].campo", equalTo("sinopse"))
                .body("[0].mensagem", equalTo("O campo deve ter no máximo 2147483647 caracteres"))
                .body("[0].campo", equalTo("sinopse"))
        ;

        logResposta("POST/livros", responseLivro);

    }

    @Test
    public void cadastrarLivroCampoAutorVazio() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroAutorVazio();

        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro);
        responseLivro
                .then()
                .log().body()
                .statusCode(400)
                .body("[1].mensagem", equalTo("O campo não pode estar vazio"))
                .body("[1].campo", equalTo("autores"))
        ;

        logResposta("POST/livros", responseLivro);

    }

    @Test
    public void buscarLivroComNumeroIsbn() {
        String token = UsuarioHelper.loginLeitor();

        String isbn = ConfigProperties.get("isbn13");

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
