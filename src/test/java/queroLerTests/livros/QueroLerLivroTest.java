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
import report.Setup;
import utlis.ConfigProperties;
import utlis.LivroHelper;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static utlis.UsuarioHelper.logResposta;

@ExtendWith(Setup.class)
public class QueroLerLivroTest extends BaseTest {

    @Test
    public void buscarLivros() {
        LoginModel usuarioLogin = new LoginModel();
        usuarioLogin.setUser(ConfigProperties.get("leitor.email"));
        usuarioLogin.setSenha(ConfigProperties.get("leitor.password"));
        Response response = LoginClient.acessarLogin(usuarioLogin);
        response
                .then()
                .statusCode(200)
        ;
        Response responseLivro = LivrosClient.buscarLivros(response.cookie("jwt"));

        responseLivro
                .then()
                        .log().body()
                        .statusCode(200)
                ;


        logResposta("GET/livros", responseLivro);
    }

    @Test
    public void cadastrarLivroIsbn13Digitos() throws IOException {

        LoginModel usuarioLogin = new LoginModel();
        usuarioLogin.setUser(ConfigProperties.get("leitor.email"));
        usuarioLogin.setSenha(ConfigProperties.get("leitor.password"));
        Response response = LoginClient.acessarLogin(usuarioLogin);
        response
                .then()
                .statusCode(200)
        ;

        LivroModel livro = LivroFactory.criarLivroIsbn13();
        Response responseLivro = LivroHelper.criarLivroCadastrar(response.cookie("jwt"), livro);
        responseLivro
                .then()
                .log().body()
                .statusCode(201);

        logResposta("POST/livros", responseLivro);

    }

    @Test
    public void cadastrarLivroIsbn13DigitosComImagem() throws IOException {

        LoginModel usuarioLogin = new LoginModel();
        usuarioLogin.setUser(ConfigProperties.get("leitor.email"));
        usuarioLogin.setSenha(ConfigProperties.get("leitor.password"));
        Response response = LoginClient.acessarLogin(usuarioLogin);
        response
                .then()
                .statusCode(200)
        ;

        LivroModel livro = LivroFactory.criarLivroIsbn13();
        File imagem = new File("src/test/resources/imagens/png.png");
        Response responseLivro = LivroHelper.criarLivroCadastrar(response.cookie("jwt"), livro, imagem);
        responseLivro
                .then()
                .log().body()
                .statusCode(201);

        logResposta("POST/livros", responseLivro);

    }

    @Test
    public void cadastrarLivroIsbn10Digitos() throws IOException {

        LoginModel usuarioLogin = new LoginModel();
        usuarioLogin.setUser(ConfigProperties.get("leitor.email"));
        usuarioLogin.setSenha(ConfigProperties.get("leitor.password"));
        Response response = LoginClient.acessarLogin(usuarioLogin);
        response
                .then()
                .statusCode(200)
        ;

        LivroModel livro = LivroFactory.criarLivroIsbn10();
        Response responseLivro = LivroHelper.criarLivroCadastrar(response.cookie("jwt"), livro);
        responseLivro
                .then()
                .log().body()
                .statusCode(201);

        logResposta("POST/livros", responseLivro);

    }

    @Test
    public void cadastrarLivroIsbn10DigitosComImagem() throws IOException {

        LoginModel usuarioLogin = new LoginModel();
        usuarioLogin.setUser(ConfigProperties.get("leitor.email"));
        usuarioLogin.setSenha(ConfigProperties.get("leitor.password"));
        Response response = LoginClient.acessarLogin(usuarioLogin);
        response
                .then()
                .statusCode(200)
        ;

        LivroModel livro = LivroFactory.criarLivroIsbn10();
        File imagem = new File("src/test/resources/imagens/png.png");
        Response responseLivro = LivroHelper.criarLivroCadastrar(response.cookie("jwt"), livro, imagem);
        responseLivro
                .then()
                .log().body()
                .statusCode(201);

        logResposta("POST/livros", responseLivro);

    }

    @Test
    public void cadastrarLivroIsbn10DigitosComImagemMais10MB() throws IOException {

        LoginModel usuarioLogin = new LoginModel();
        usuarioLogin.setUser(ConfigProperties.get("leitor.email"));
        usuarioLogin.setSenha(ConfigProperties.get("leitor.password"));
        Response response = LoginClient.acessarLogin(usuarioLogin);
        response
                .then()
                .statusCode(200)
        ;

        LivroModel livro = LivroFactory.criarLivroIsbn10();
        File arquivo = new File("src/test/resources/imagens/foto_png.png");
        System.out.println(arquivo.length());
        System.out.println(arquivo.length() / 1024.0 / 1024.0 + " MB");
        Response responseLivro = LivroHelper.criarLivroCadastrar(response.cookie("jwt"), livro, arquivo);
        responseLivro
                .then()
                .log().body()
                .statusCode(413);

        logResposta("POST/livros", responseLivro);

    }

    @Test
    public void cadastrarLivroIsbn10DigitosComArquivoInvalido() throws IOException {

        LoginModel usuarioLogin = new LoginModel();
        usuarioLogin.setUser(ConfigProperties.get("leitor.email"));
        usuarioLogin.setSenha(ConfigProperties.get("leitor.password"));
        Response response = LoginClient.acessarLogin(usuarioLogin);
        response
                .then()
                .statusCode(200)
        ;

        LivroModel livro = LivroFactory.criarLivroIsbn10();
        File arquivo = new File("src/test/resources/imagens/pdf.pdf");
        Response responseLivro = LivroHelper.criarLivroCadastrar(response.cookie("jwt"), livro, arquivo);
        responseLivro
                .then()
                .log().body()
                .statusCode(415)
                .body(equalTo("Formato inválido. Use JPG ou PNG"));

        logResposta("POST/livros", responseLivro);

    }

    @Test
    public void cadastrarLivroIsbnInvalido() throws IOException {

        LoginModel usuarioLogin = new LoginModel();
        usuarioLogin.setUser(ConfigProperties.get("leitor.email"));
        usuarioLogin.setSenha(ConfigProperties.get("leitor.password"));
        Response response = LoginClient.acessarLogin(usuarioLogin);
        response
                .then()
                .statusCode(200)
        ;

        LivroModel livro = LivroFactory.criarLivroIsbnInvalido();
        Response responseLivro = LivroHelper.criarLivroCadastrar(response.cookie("jwt"), livro);
        responseLivro
                .then()
                .log().body()
                .statusCode(400)
                .body(equalTo("ISBN deve conter apenas números e ter 10 ou 13 dígitos"))
        ;

        logResposta("POST/livros", responseLivro);

    }

    @Test
    public void cadastrarLivroIsbnComLetra() throws IOException {

        LoginModel usuarioLogin = new LoginModel();
        usuarioLogin.setUser(ConfigProperties.get("leitor.email"));
        usuarioLogin.setSenha(ConfigProperties.get("leitor.password"));
        Response response = LoginClient.acessarLogin(usuarioLogin);
        response
                .then()
                .statusCode(200)
        ;

        LivroModel livro = LivroFactory.criarLivroIsbnComLetra();
        Response responseLivro = LivroHelper.criarLivroCadastrar(response.cookie("jwt"), livro);
        responseLivro
                .then()
                .log().body()
                .statusCode(400)
                .body(equalTo("ISBN deve conter apenas números e ter 10 ou 13 dígitos"))
        ;

        logResposta("POST/livros", responseLivro);

    }

    @Test
    public void buscarLivroComNumeroIsbn() {
        LoginModel usuarioLogin = new LoginModel();
        usuarioLogin.setUser(ConfigProperties.get("leitor.email"));
        usuarioLogin.setSenha(ConfigProperties.get("leitor.password"));
        Response response = LoginClient.acessarLogin(usuarioLogin);
        response
                .then()
                .statusCode(200)
        ;
        String isbn = ConfigProperties.get("isbn13");
        Response responseLivroIsbn = LivrosClient.buscarLivroIsbn(response.cookie("jwt"), isbn);
        responseLivroIsbn
                .then()
                .log().body()
                .statusCode(200)
                ;
    }

    @Test
    public void buscarLivroIsbnInexistente() {
        LoginModel usuarioLogin = new LoginModel();
        usuarioLogin.setUser(ConfigProperties.get("leitor.email"));
        usuarioLogin.setSenha(ConfigProperties.get("leitor.password"));
        Response response = LoginClient.acessarLogin(usuarioLogin);
        response
                .then()
                .statusCode(200)
        ;
        String isbn = "9999999999";
        Response responseLivroIsbn = LivrosClient.buscarLivroIsbn(response.cookie("jwt"), isbn);
        responseLivroIsbn
                .then()
                .log().body()
                .statusCode(404)
                .body(equalTo("Não há nenhum livro cadastrado com o código ISBN informado"))
        ;
    }

    @Test
    public void buscarLivroIdCapaInexistente() {
        LoginModel usuarioLogin = new LoginModel();
        usuarioLogin.setUser(ConfigProperties.get("leitor.email"));
        usuarioLogin.setSenha(ConfigProperties.get("leitor.password"));
        Response response = LoginClient.acessarLogin(usuarioLogin);
        response
                .then()
                .statusCode(200)
        ;
        String idCapa = "1";
        Response responseLivroIsbn = LivrosClient.buscarLivroIdCapa(response.cookie("jwt"), idCapa);
        responseLivroIsbn
                .then()
                .log().body()
                .statusCode(404)
                .body(equalTo("Capa não cadastrada"))
        ;
    }
}
