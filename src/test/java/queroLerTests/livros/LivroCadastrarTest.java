package queroLerTests.livros;

import baseTest.BaseTest;
import factories.LivroFactory;
import io.restassured.response.Response;
import models.LivroModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import report.Setup;
import utils.LivroHelper;
import utils.UsuarioHelper;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasItem;
import static utils.UsuarioHelper.logResposta;

@ExtendWith(Setup.class)
public class LivroCadastrarTest extends BaseTest {

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
    public void cadastrarLivroIsbnJaExistente() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro1 = LivroFactory.criarLivroIsbn();
        LivroHelper.criarLivroCadastrar(token, livro1);
        LivroModel livro2 = LivroFactory.criarLivroIsbn();
        livro2.setIsbn(livro1.getIsbn());

        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro2);
        responseLivro
                .then()
                .log().body()
                .statusCode(409)
                .body(equalTo("ISBN já cadastrado"));

        logResposta("POST/livros", responseLivro);

    }

    @Test
    public void cadastrarLivroIsbn10DigitosComImagemMais10MB() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroIsbn10();
        File arquivo = new File("src/test/resources/imagens/foto_png.png");

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
                .body("mensagem", hasItems("O campo não pode estar vazio", "ISBN deve conter apenas números e ter 10 ou 13 dígitos"))
                .body("campo", hasItems("isbn", "isbn"))
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
                .body("[0].mensagem", equalTo("deve ser maior que 0"))
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
                .body("mensagem", hasItems("O campo não pode estar vazio", "O campo deve ter no mínimo 50 e no máximo 256 caracteres"))
                .body("campo", hasItems("sinopse", "sinopse"))
        ;

        logResposta("POST/livros", responseLivro);

    }

    @ParameterizedTest
    @ValueSource(ints = {
            10,
            25,
            49
    })
    public void cadastrarLivroCampoSinopsePositivoAte49Caracteres(int sinopse) throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroSinopseAleotorio(sinopse);

        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro);
        responseLivro
                .then()
                .log().body()
                .statusCode(400)
                .body("mensagem", hasItem("O campo deve ter no mínimo 50 e no máximo 256 caracteres"))
                .body("campo", hasItem("sinopse"))
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
                .body("[0].mensagem", equalTo("O campo deve ter no mínimo 3 e no máximo 80 caracteres"))
                .body("[0].campo", equalTo("autores[0].nome"))
        ;

        logResposta("POST/livros", responseLivro);

    }

}
