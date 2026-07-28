package queroLerTests.usuarios;

import baseTest.BaseTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import factories.UsuarioFactory;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import models.UsuarioModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import report.Setup;
import utils.UsuarioHelper;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static utils.UsuarioHelper.logResposta;

@ExtendWith(Setup.class)
public class UsuarioCadastrarTest extends BaseTest {

    @Test
    public void cadastrarUsuarioSemFoto() throws JsonProcessingException {
        UsuarioModel usuario = UsuarioFactory.criarUsuario();
        Response response = UsuarioHelper.criarUsuarioCadastrar(usuario);
        response
                .then()
                .log().body()
                .statusCode(201)
                .body("email", equalTo(usuario.getEmail()))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/usuario-schema.json"))
        ;

        logResposta("POST/usuarios", response);

    }

    @Test
    public void cadastrarUsuarioComFoto() throws IOException {
        File imagem = new File("src/test/resources/imagens/jpg.jpg");
        UsuarioModel usuario = UsuarioFactory.criarUsuario();
        Response response = UsuarioHelper.criarUsuarioCadastrar(usuario, imagem);
        response
                .then()
                .log().body()
                .statusCode(201)
                .body("email", equalTo(usuario.getEmail()))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/usuario-schema.json"))
        ;

        logResposta("POST/usuarios", response);

    }

    @Test
    public void cadastrarUsuarioSenhaMenos8Caracteres() throws JsonProcessingException {
        UsuarioModel usuario = UsuarioFactory.criarUsuarioMenos8caracteres();
        Response response = UsuarioHelper.criarUsuarioCadastrar(usuario);
        response
                .then()
                .log().body()
                .statusCode(400)
                .body(equalTo("A senha deve ter no mínimo 8 caracteres."))
        ;

        logResposta("POST/usuarios", response);

    }

//    @Test
    public void cadastrarUsuarioSenhaMais100Caracteres() throws JsonProcessingException {
        UsuarioModel usuario = UsuarioFactory.criarUsuarioMais100caracteres();
        Response response = UsuarioHelper.criarUsuarioCadastrar(usuario);
        response
                .then()
                .log().body()
                .statusCode(400)
                .body(equalTo("A senha deve ter no máximo 64 caracteres."))
        ;

        logResposta("POST/usuarios", response);

    }

    @Test
    public void cadastrarUsuarioComNomesMais80caracteres() throws JsonProcessingException {
        UsuarioModel usuario = UsuarioFactory.criarUsuarioNomeMais80Caracteres();
        Response response = UsuarioHelper.criarUsuarioCadastrar(usuario);
        response
                .then()
                .log().body()
                .statusCode(400)
                .body("[0].mensagem", equalTo("O campo deve ter no mínimo 3 e no máximo 80 caracteres"))
                .body("[0].campo", equalTo("nome"))
        ;

        logResposta("POST/usuarios", response);

    }

    @ParameterizedTest
    @ValueSource(strings = {
            "247"
    })
    public void cadastrarUsuarioComEmailMais256caracteres(String numero) throws JsonProcessingException {
        UsuarioModel usuario = UsuarioFactory.criarUsuarioEmailMais256Caracteres(numero);
        Response response = UsuarioHelper.criarUsuarioCadastrar(usuario);
        response
                .then()
                .log().body()
                .statusCode(400)
                .body("[0].mensagem", equalTo("O campo deve ter no mínimo 0 e no máximo 256 caracteres"))
                .body("[0].campo", equalTo("email"))
        ;

        logResposta("POST/usuarios", response);

    }

    @ParameterizedTest
    @ValueSource(strings = {
            "@hotmail.com",
            "ana@",
            "ana",
            "ana @hotmail.com",
            "ana@ hotmail.com",
            "anahotmail.com",
            "ana@@hotmail.com",
            "ana@teste@hotmail.com",
            "ana.hotmail.com",
            "ana@.com",
            "ana<>@hotmail.com",
            "ana#hotmail.com"
    })
    public void cadastrarUsuarioComEmailInvalido(String email) throws JsonProcessingException {
        UsuarioModel usuario = UsuarioFactory.criarUsuarioEmailInvalido(email);
        Response response = UsuarioHelper.criarUsuarioCadastrar(usuario);
        response
                .then()
                .log().body()
                .statusCode(400)
                .body("[0].mensagem", equalTo("Informe um endereço de e-mail válido"))
                .body("[0].campo", equalTo("email"))
        ;

        logResposta("POST/usuarios", response);

    }

    @ParameterizedTest
    @ValueSource(strings = {
            "ana@hotmail..com",
            "ana..carolyny@hotmail.com"
    })
    public void cadastrarUsuarioComEmailInvalidoComPontos(String email) throws JsonProcessingException {
        UsuarioModel usuario = UsuarioFactory.criarUsuarioEmailInvalido(email);
        Response response = UsuarioHelper.criarUsuarioCadastrar(usuario);
        response
                .then()
                .log().body()
                .statusCode(400)
                .body(equalTo("Email inválido."))
        ;

        logResposta("POST/usuarios", response);

    }

    @ParameterizedTest
    @ValueSource(strings = {
            "ana@hotmailcom",
            "ana@gma!l.com"
    })
    public void cadastrarUsuarioComEmailInvalidoEspecial(String email) throws JsonProcessingException {
        UsuarioModel usuario = UsuarioFactory.criarUsuarioEmailInvalido(email);
        Response response = UsuarioHelper.criarUsuarioCadastrar(usuario);
        response
                .then()
                .log().body()
                .statusCode(400)
                .body("[0].mensagem", equalTo("Informe um endereço de e-mail válido"))
                .body("[0].campo", equalTo("email"))
        ;

        logResposta("POST/usuarios", response);

    }

    @Test
    public void cadastrarUsuarioComEmailVazio() throws JsonProcessingException {
        UsuarioModel usuario = UsuarioFactory.criarUsuarioEmailVazio();
        Response response = UsuarioHelper.criarUsuarioCadastrar(usuario);
        response
                .then()
                .log().body()
                .statusCode(400)
                .body("[0].mensagem", equalTo("O campo não pode estar vazio"))
                .body("[0].campo", equalTo("email"))
        ;

        logResposta("POST/usuarios", response);

    }

    @Test
    public void cadastrarUsuarioEmailDuplicado() throws JsonProcessingException {
        UsuarioModel usuario = UsuarioFactory.criarUsuario();
        Response response = UsuarioHelper.criarUsuarioCadastrar(usuario);
        response
                .then()
                .log().body()
                .statusCode(201)
                .body("email", equalTo(usuario.getEmail()))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/usuario-schema.json"))
        ;

        UsuarioModel usuarioDuplicadoEmail = UsuarioFactory.criarUsuario();
        usuarioDuplicadoEmail.setEmail(usuario.getEmail());
        usuarioDuplicadoEmail.setConfirmarEmail(usuario.getEmail());
        Response responseDuplicadoEmail = UsuarioHelper.criarUsuarioCadastrar(usuarioDuplicadoEmail);
        responseDuplicadoEmail
                .then()
                .log().body()
                .statusCode(409)
                .body(equalTo("O email '"+usuario.getEmail()+"' já está cadastrado."))
        ;

        logResposta("POST/usuarios", responseDuplicadoEmail);

    }

    @Test
    public void cadastrarUsuarioCpfDuplicado() throws JsonProcessingException {
        UsuarioModel usuario = UsuarioFactory.criarUsuario();
        Response response = UsuarioHelper.criarUsuarioCadastrar(usuario);
        response
                .then()
                .log().body()
                .statusCode(201)
                .body("email", equalTo(usuario.getEmail()))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/usuario-schema.json"))
        ;

        UsuarioModel usuarioDuplicadoCpf = UsuarioFactory.criarUsuario();
        usuarioDuplicadoCpf.setCpf(usuario.getCpf());
        Response responseDuplicadoCpf = UsuarioHelper.criarUsuarioCadastrar(usuarioDuplicadoCpf);
        responseDuplicadoCpf
                .then()
                .log().body()
                .statusCode(409)
                .body(equalTo("CPF já cadastrado."))
        ;

        logResposta("POST/usuarios", responseDuplicadoCpf);

    }

}
