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
import utils.DataFakerUtils;
import utils.UsuarioHelper;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static utils.UsuarioHelper.logResposta;

@ExtendWith(Setup.class)
public class UsuarioAtualizarTest extends BaseTest {

    @Test
    public void atualizarNome() throws JsonProcessingException {
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
    public void atualizarEmail() throws JsonProcessingException {
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
    public void atualizacaoEmailParaEmailExistente() throws JsonProcessingException {
        UsuarioModel usuario = UsuarioFactory.criarUsuario();
        String usuarioEmail = usuario.getEmail();
        Response response = UsuarioHelper.criarUsuarioCadastrar(usuario);
        response
                .then()
                .log().body()
                .statusCode(201);

        UsuarioModel usuario1 = UsuarioFactory.criarUsuario();
        Response response1 = UsuarioHelper.criarUsuarioCadastrar(usuario1);
        response1
                .then()
                .log().body()
                .statusCode(201);

        String loginToken = UsuarioHelper.acessarLoginGerarToken(usuario1);
        usuario1.setEmail(usuarioEmail);

        Response responseAtualizar = UsuarioClient.atualizarUsuario(loginToken, usuario1);

        responseAtualizar
                .then()
                .log().body()
                .statusCode(409)
                .body(equalTo("O email '"+usuarioEmail+"' já está cadastrado."))
        ;

        logResposta("PUT/usuarios", responseAtualizar);
    }

    @Test
    public void atualizarCpf() throws JsonProcessingException {
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
    public void atualizarDadosAdicionais() throws IOException {

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
    public void atualizarSenha() throws JsonProcessingException {

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
    public void atualizarSenhaSemLetraMaiuscula() throws JsonProcessingException {

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
    public void atualizarSenhaSemLetraMinuscula() throws JsonProcessingException {

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
    public void atualizarSenhaComMenos8Caracteres() throws JsonProcessingException {

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
    public void atualizarSenhaSemCaracterEspecial() throws JsonProcessingException {

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
    public void atualizarSenhaSemNumero() throws JsonProcessingException {

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
    public void atualizarSenhaIncorretaSenhaAtual() throws JsonProcessingException {

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

}
