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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import report.Setup;
import utils.DataFakerUtils;
import utlis.UsuarioHelper;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static utlis.UsuarioHelper.logResposta;

@ExtendWith(Setup.class)
public class QueroLerUsuarioTest extends BaseTest {

    @Test
    public void cadastrarUsuario() throws JsonProcessingException {
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
    public void cadastrarUsuarioComNomesMais80caracteres() throws JsonProcessingException {
        UsuarioModel usuario = UsuarioFactory.criarUsuarioNomeMais80Caracteres();
        Response response = UsuarioHelper.criarUsuarioCadastrar(usuario);
        response
                .then()
                .log().body()
                .statusCode(400)
                .body("[0].mensagem", equalTo("O campo deve ter no máximo 80 caracteres"))
                .body("[0].campo", equalTo("nome"))
        ;

        logResposta("POST/usuarios", response);

    }

    @ParameterizedTest
    @ValueSource(strings = {
            "64",
            "65",
            "247"
    })
    public void cadastrarUsuarioComEmailMais256caracteres(String numero) throws JsonProcessingException {
        UsuarioModel usuario = UsuarioFactory.criarUsuarioEmailMais256Caracteres(numero);
        Response response = UsuarioHelper.criarUsuarioCadastrar(usuario);
        response
                .then()
                .log().body()
                .statusCode(400)
                .body("[0].mensagem", equalTo("E-mail inválido"))
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
            "ana@hotmail..com",
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
                .body("[0].mensagem", equalTo("E-mail inválido"))
                .body("[0].campo", equalTo("email"))
        ;

        logResposta("POST/usuarios", response);

    }

    @ParameterizedTest
    @ValueSource(strings = {
            "ana@hotmailcom",
            "ana@gma!l.com",
            ""
    })
    public void cadastrarUsuarioComEmailInvalidoEspecial(String email) throws JsonProcessingException {
        UsuarioModel usuario = UsuarioFactory.criarUsuarioEmailInvalido(email);
        Response response = UsuarioHelper.criarUsuarioCadastrar(usuario);
        response
                .then()
                .log().body()
                .statusCode(400)
                .body("[0].mensagem", equalTo("E-mail inválido"))
                .body("[0].campo", equalTo("email"))
        ;

        logResposta("POST/usuarios", response);

    }

    @Test
    public void cadastrarUsuarioEmailDiferenteEmailConfirmar() throws JsonProcessingException {
        UsuarioModel usuario = UsuarioFactory.criarUsuarioEmailDiferenteEmailConfirmar();
        Response response = UsuarioHelper.criarUsuarioCadastrar(usuario);
        response
                .then()
                .log().body()
                .statusCode(400)
                .body("[0].campo", equalTo("confirmarEmail"))
                .body("[0].mensagem", equalTo("Email digitada não corresponde email confirmar"));
        ;

        logResposta("POST/usuarios", response);

    }

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

    @Test
    public void atualizacaoNomeUsuario() throws JsonProcessingException {
        UsuarioModel usuario = UsuarioFactory.criarUsuario();
        Response response = UsuarioHelper.criarUsuarioCadastrar(usuario);
        response
                .then()
                .log().body()
                .statusCode(201);

        String loginToken = UsuarioHelper.acessarLoginGerarToken(usuario);

        usuario.setNome("Nome do teste");

        Response responseAtualizar = UsuarioClient.atualizarUsuario(loginToken, usuario);

        responseAtualizar
                .then()
                .log().body()
                .statusCode(204)
        ;

        logResposta("PUT/usuarios", responseAtualizar);

    }

    @Test
    public void atualizacaoEmailUsuario() throws JsonProcessingException {
        UsuarioModel usuario = UsuarioFactory.criarUsuario();
        Response response = UsuarioHelper.criarUsuarioCadastrar(usuario);
        response
                .then()
                .log().body()
                .statusCode(201);

        String loginToken = UsuarioHelper.acessarLoginGerarToken(usuario);
        usuario.setEmail(DataFakerUtils.email());

        Response responseAtualizar = UsuarioClient.atualizarUsuario(loginToken, usuario);

        responseAtualizar
                .then()
                .log().body()
                .statusCode(204)
        ;

        logResposta("PUT/usuarios", responseAtualizar);
    }

    @Test
    public void atualizacaoCpfUsuario() throws JsonProcessingException {
        UsuarioModel usuario = UsuarioFactory.criarUsuario();
        Response response = UsuarioHelper.criarUsuarioCadastrar(usuario);
        response
                .then()
                .log().body()
                .statusCode(201);

        String loginToken = UsuarioHelper.acessarLoginGerarToken(usuario);

        usuario.setCpf(DataFakerUtils.cpf());

        Response responseAtualizar = UsuarioClient.atualizarUsuario(loginToken, usuario);

        responseAtualizar
                .then()
                .log().body()
                .statusCode(204)
        ;

        logResposta("PUT/usuarios", responseAtualizar);
    }

    @Test
    public void atualizacaoDadosAdicionaisUsuario() throws IOException {

        UsuarioModel usuario = UsuarioFactory.criarUsuario();
        Response response = UsuarioHelper.criarUsuarioCadastrar(usuario);
        response
                .then()
                .log().body()
                .statusCode(201);

        String loginToken = UsuarioHelper.acessarLoginGerarToken(usuario);
        usuario = UsuarioFactory.dadosAdicionaisUsuario();

        File imagem = new File("src/test/resources/imagens/png.png");
        Response responseAtualizar = UsuarioClient.atualizarUsuarioDadoAdicionais(loginToken, usuario, imagem);

        responseAtualizar
                .then()
                .log().body()
                .statusCode(204)
        ;

        logResposta("PUT/usuarios/dados-adicionais", responseAtualizar);

    }

    @Test
    public void atualizacaoAlterarSenhaUsuario() throws JsonProcessingException {

        UsuarioModel usuario = UsuarioFactory.criarUsuario();
        Response response = UsuarioHelper.criarUsuarioCadastrar(usuario);
        response
                .then()
                .log().body()
                .statusCode(201);

        String loginToken = UsuarioHelper.acessarLoginGerarToken(usuario);

        Response responseAtualizar = UsuarioClient.atualizarUsuarioAlterarSenha(loginToken, usuario.getSenha(), "Teste@321");

        responseAtualizar
                .then()
                .log().body()
                .statusCode(204)
        ;

        logResposta("PUT/usuarios", responseAtualizar);

    }

    @Test
    public void atualizacaoAlterarSenhaSemLetraMaiusculaUsuario() throws JsonProcessingException {

        UsuarioModel usuario = UsuarioFactory.criarUsuario();
        Response response = UsuarioHelper.criarUsuarioCadastrar(usuario);
        response
                .then()
                .log().body()
                .statusCode(201);

        String loginToken = UsuarioHelper.acessarLoginGerarToken(usuario);

        Response responseAtualizar = UsuarioClient.atualizarUsuarioAlterarSenha(loginToken, usuario.getSenha(), "123456a@");

        responseAtualizar
                .then()
                .statusCode(400)
                .log().body()
                .body(equalTo("A senha deve conter pelo menos uma letra maiúscula."))
        ;

        logResposta("PUT/usuarios/alterar-senha", responseAtualizar);

    }

    @Test
    public void atualizacaoAlterarSenhaSemLetraMinusculaUsuario() throws JsonProcessingException {

        UsuarioModel usuario = UsuarioFactory.criarUsuario();
        Response response = UsuarioHelper.criarUsuarioCadastrar(usuario);
        response
                .then()
                .log().body()
                .statusCode(201);

        String loginToken = UsuarioHelper.acessarLoginGerarToken(usuario);

        Response responseAtualizar = UsuarioClient.atualizarUsuarioAlterarSenha(loginToken,usuario.getSenha(), "123456A@");

        responseAtualizar
                .then()
                .statusCode(400)
                .log().body()
                .body(equalTo("A senha deve conter pelo menos uma letra minúscula."))
        ;

        logResposta("PUT/usuarios/alterar-senha", responseAtualizar);

    }

    @Test
    public void atualizacaoAlterarSenhaMinimo8CaracteresUsuario() throws JsonProcessingException {

        UsuarioModel usuario = UsuarioFactory.criarUsuario();
        Response response = UsuarioHelper.criarUsuarioCadastrar(usuario);
        response
                .then()
                .log().body()
                .statusCode(201);

        String loginToken = UsuarioHelper.acessarLoginGerarToken(usuario);

        Response responseAtualizar = UsuarioClient.atualizarUsuarioAlterarSenha(loginToken, usuario.getSenha(), "123456");

        responseAtualizar
                .then()
                .statusCode(400)
                .log().body()
                .body(equalTo("A senha deve ter no mínimo 8 caracteres."))
        ;

        logResposta("PUT/usuarios/alterar-senha", responseAtualizar);

    }

    @Test
    public void atualizacaoAlterarSenhaSemCaracterEspecialUsuario() throws JsonProcessingException {

        UsuarioModel usuario = UsuarioFactory.criarUsuario();
        Response response = UsuarioHelper.criarUsuarioCadastrar(usuario);
        response
                .then()
                .log().body()
                .statusCode(201);

        String loginToken = UsuarioHelper.acessarLoginGerarToken(usuario);

        Response responseAtualizar = UsuarioClient.atualizarUsuarioAlterarSenha(loginToken, usuario.getSenha(), "123456Aa");

        responseAtualizar
                .then()
                .statusCode(400)
                .log().body()
                .body(equalTo("A senha deve conter pelo menos um caractere especial."))
        ;

        logResposta("PUT/usuarios/alterar-senha", responseAtualizar);

    }

    @Test
    public void atualizacaoAlterarSenhaSemNumeroUsuario() throws JsonProcessingException {

        UsuarioModel usuario = UsuarioFactory.criarUsuario();
        Response response = UsuarioHelper.criarUsuarioCadastrar(usuario);
        response
                .then()
                .log().body()
                .statusCode(201);

        String loginToken = UsuarioHelper.acessarLoginGerarToken(usuario);

        Response responseAtualizar = UsuarioClient.atualizarUsuarioAlterarSenha(loginToken, usuario.getSenha(), "abcdABCD");

        responseAtualizar
                .then()
                .statusCode(400)
                .log().body()
                .body(equalTo("A senha deve conter pelo menos um número."))
        ;

        logResposta("PUT/usuarios/alterar-senha", responseAtualizar);

    }

    @Test
    public void atualizacaoAlterarSenhaIncorretaSenhaAtualUsuario() throws JsonProcessingException {

        UsuarioModel usuario = UsuarioFactory.criarUsuario();
        Response response = UsuarioHelper.criarUsuarioCadastrar(usuario);
        response
                .then()
                .log().body()
                .statusCode(201);

        String loginToken = UsuarioHelper.acessarLoginGerarToken(usuario);

        Response responseAtualizar = UsuarioClient.atualizarUsuarioAlterarSenha(loginToken, "@TestErro1", "TestNew1@");

        responseAtualizar
                .then()
                .log().body()
                .statusCode(401)
                .body(equalTo("A senha digitada não corresponde a atual."))
        ;

        logResposta("PUT/usuarios/alterar-senha", responseAtualizar);

    }

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
