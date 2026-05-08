package queroLerTests.login;

import baseTest.BaseTest;
import clients.LoginClient;
import factories.LoginFactory;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import report.Setup;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static utlis.UsuarioHelper.logResposta;

@ExtendWith(Setup.class)
public class QueroLerLoginTest extends BaseTest {

    @Test
    public void acessarLoginUsuarioLeitorComSucesso() {
        Response response = LoginClient.acessarLogin(LoginFactory.usuarioLeitorSucesso());

        response
                .then()
                .log().body()
                .statusCode(200)
                ;

        logResposta("POST/logins", response);
    }

    @Test
    public void acessarLoginUsuarioLeitorSenhaInvalida() {
        Response response = LoginClient.acessarLogin(LoginFactory.usuarioLeitorSenhaInvalida());

        response
                .then()
                .log().body()
                .statusCode(401)
                .body(equalTo("E-mail ou senha inválida."))
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
                .body(equalTo("E-mail ou senha inválida."))
                ;

        logResposta("POST/logins", response);
    }

    @Test
    public void acessarLoginUsuarioModeradorComSucesso() throws IOException {
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
                .body(equalTo("E-mail ou senha inválida."))
                ;

        logResposta("POST/logins", response);
    }
}
