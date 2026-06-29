package queroLerTests.usuarios;

import baseTest.BaseTest;
import clients.UsuarioClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import factories.UsuarioFactory;
import io.restassured.response.Response;
import models.UsuarioModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import report.Setup;
import utils.UsuarioHelper;

import static utils.UsuarioHelper.logResposta;

@ExtendWith(Setup.class)
public class UsuarioDeletarTest extends BaseTest {

    @Test
    public void deleteUsuarioId() throws JsonProcessingException {
        UsuarioModel usuario = UsuarioFactory.criarUsuario();
        Response response = UsuarioHelper.criarUsuarioCadastrar(usuario);
        response
                .then()
                .log().body()
                .statusCode(201);

        String loginToken = UsuarioHelper.acessarLoginGerarToken(usuario);

        Response response1 = UsuarioClient.deleteUsuarioId(loginToken);
        response1.then().statusCode(204);

        logResposta("DELETE/usuarios", response1);

    }

}
