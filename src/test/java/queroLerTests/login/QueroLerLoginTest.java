package queroLerTests.login;

import baseTest.BaseTest;
import clients.LoginClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import factories.LoginFactory;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import report.Setup;

import static org.hamcrest.Matchers.equalTo;
import static utils.UsuarioHelper.logResposta;

@ExtendWith(Setup.class)
public class QueroLerLoginTest extends BaseTest {

    @Test
    public void acessarLoginUsuarioLeitorExistenteComSucesso() throws JsonProcessingException {
        Response response = LoginClient.acessarLogin(LoginFactory.usuarioLeitorExistenteComSucesso());

        response
                .then()
                .log().body()
                .statusCode(200)
        ;

        logResposta("POST/logins", response);
    }


    @Test
    public void acessarLoginUsuarioLeitorComSucesso() throws JsonProcessingException {
        Response response = LoginClient.acessarLogin(LoginFactory.usuarioLeitorSucesso());

        response
                .then()
                .log().body()
                .statusCode(200)
                ;

        logResposta("POST/logins", response);
    }

    @Test
    public void acessarLoginUsuarioLeitorSenhaInvalida() throws JsonProcessingException {
        Response response = LoginClient.acessarLogin(LoginFactory.usuarioLeitorSenhaInvalida());

        response
                .then()
                .log().body()
                .statusCode(401)
                .body(equalTo("Senha incorreta."))
                ;

        logResposta("POST/logins", response);
    }

    @Test
    public void acessarLoginUsuarioLeitorEmailInexistente() {
        Response response = LoginClient.acessarLogin(LoginFactory.usuarioLeitorEmailInexistente());

        response
                .then()
                .log().body()
                .statusCode(404)
                .body(equalTo("Usuario não cadastrado"))
                ;

        logResposta("POST/logins", response);
    }

    @Test
    public void acessarLoginUsuarioAdministradorComSucesso() {
        Response response = LoginClient.acessarLogin(LoginFactory.usuarioAdministradorEmailComSucesso());

        response
                .then()
                .log().body()
                .statusCode(200)
                ;

        logResposta("POST/logins", response);
    }

    @Test
    public void acessarLoginUsuarioAdministradorSenhaInvalida() {
        Response response = LoginClient.acessarLogin(LoginFactory.usuarioAdministradorSenhaInvalida());

        response
                .then()
                .log().body()
                .statusCode(401)
                .body(equalTo("Senha incorreta."))
                ;

        logResposta("POST/logins", response);
    }

    @Test
    public void acessarLoginUsuarioModeradorComSucesso() {
        Response response = LoginClient.acessarLogin(LoginFactory.usuarioModeradorEmailComSucesso());

        response
                .then()
                .log().body()
                .statusCode(200)
                ;

        logResposta("POST/logins", response);
    }

    @Test
    public void acessarLoginUsuarioModeradorSenhaInvalida() {
        Response response = LoginClient.acessarLogin(LoginFactory.usuarioModeradorSenhaInvalida());

        response
                .then()
                .log().body()
                .statusCode(401)
                .body(equalTo("Senha incorreta."))
                ;

        logResposta("POST/logins", response);
    }
}
