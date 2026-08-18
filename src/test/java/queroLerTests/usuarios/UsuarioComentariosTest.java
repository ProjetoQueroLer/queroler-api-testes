package queroLerTests.usuarios;

import baseTest.BaseTest;
import clients.UsuarioClient;
import factories.LivroFactory;
import factories.UsuarioFactory;
import io.restassured.response.Response;
import models.LivroModel;
import models.UsuarioModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import queroLerTests.livros.LivroCadastrarTest;
import report.Setup;
import utils.LivroHelper;
import utils.UsuarioHelper;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static utils.UsuarioHelper.logResposta;

@ExtendWith(Setup.class)
public class UsuarioComentariosTest extends BaseTest {

    @Test
    public void UsuarioIdComentario() throws IOException {
        String token = UsuarioHelper.loginLeitor();
        LivroModel livro = LivroFactory.criarLivroIsbn13();

        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro);
        responseLivro
                .then()
                .log().body()
                .statusCode(201);



        UsuarioModel usuario = UsuarioFactory.criarUsuario();
        UsuarioHelper.criarUsuarioCadastrar(usuario);
        String loginToken = UsuarioHelper.acessarLoginGerarToken(usuario);
        Response response = UsuarioClient.buscarUsuarioFoto(loginToken);
        response
                .then()
                .log().body()
                .statusCode(404)
                .body(equalTo("Foto não cadastrada"))
        ;

        logResposta("GET/usuarios/foto", response);

    }

    @Test
    public void UsuarioIdComentario1() throws IOException {
        LivroCadastrarTest livroCadastrarTest = new LivroCadastrarTest();

        UsuarioModel usuario = UsuarioFactory.criarUsuario();
        UsuarioHelper.criarUsuarioCadastrar(usuario);
        String loginToken = UsuarioHelper.acessarLoginGerarToken(usuario);
        Response response = UsuarioClient.buscarUsuarioFoto(loginToken);
        response
                .then()
                .log().body()
                .statusCode(404)
                .body(equalTo("Foto não cadastrada"))
        ;

        logResposta("GET/usuarios/foto", response);

    }

}
