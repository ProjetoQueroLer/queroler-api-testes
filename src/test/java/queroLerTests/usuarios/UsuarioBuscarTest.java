package queroLerTests.usuarios;

import baseTest.BaseTest;
import clients.UsuarioClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import factories.UsuarioFactory;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import models.UsuarioModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import report.Setup;
import utils.UsuarioHelper;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static utils.UsuarioHelper.logResposta;

@ExtendWith(Setup.class)
public class UsuarioBuscarTest extends BaseTest {

    @Test
    public void buscarUsuario() throws JsonProcessingException {
        UsuarioModel usuario = UsuarioFactory.criarUsuario();
        UsuarioHelper.criarUsuarioCadastrar(usuario);
        String loginToken = UsuarioHelper.acessarLoginGerarToken(usuario);
        Response response = UsuarioClient.buscarUsuario(loginToken);
        response
                .then()
                .log().body()
                .statusCode(200)
                .body("email", equalTo(usuario.getEmail()))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/usuario-schema.json"))
        ;

        logResposta("GET/usuarios", response);

    }

    @Test
    public void buscarUsuarioComFoto() throws IOException {
        File imagem = new File("src/test/resources/imagens/perfil.png");
        UsuarioModel usuario = UsuarioFactory.criarUsuario();
        UsuarioHelper.criarUsuarioCadastrar(usuario, imagem);
        String loginToken = UsuarioHelper.acessarLoginGerarToken(usuario);
        Response response = UsuarioClient.buscarUsuarioFoto(loginToken);
        response
                .then()
                .log().body()
                .statusCode(200)
        ;

        logResposta("GET/usuarios/foto", response);

    }

    @Test
    public void buscarUsuarioSemFoto() throws IOException {
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
