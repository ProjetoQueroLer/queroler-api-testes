package queroLerTests.usuarios;

import baseTest.BaseTest;
import clients.UsuarioClient;

import factories.UsuarioFactory;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import models.UsuarioModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import report.Setup;
import utils.DataFakerUtils;
import utlis.UsuarioHelper;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static utlis.UsuarioHelper.extrairEmail;
import static utlis.UsuarioHelper.logResposta;

@ExtendWith(Setup.class)
public class QueroLerUsuarioTest extends BaseTest {

    @Test
    public void cadastrarUsuario() {
        Response response = UsuarioClient.criarUsuario(UsuarioFactory.criarUsuario());

        int id = response
                .then()
                .log().body()
                .statusCode(201)
                .extract()
                .path("id");

        UsuarioClient.buscarUsuarioId(id)
                .then()
                .statusCode(200)
                .body("email", equalTo(extrairEmail(response)))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/usuario-schema.json"))
                ;

        logResposta("POST/usuarios", response);

    }

    @Test
    public void buscarUsuarioId() {
        Response response = UsuarioClient.criarUsuario(UsuarioFactory.criarUsuario());

        int id = response.jsonPath().getInt("id");

        Response response1 = UsuarioClient.buscarUsuarioId(id);

        response1
                .then()
                .log().body()
                .statusCode(200)
                .body("email", equalTo(extrairEmail(response)))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/usuario-schema.json"))
        ;

        logResposta("GET/usuarios/"+id, response1);

    }

    @Test
    public void buscarUsuarioIdInexistente() {
        int id = 999999;

        Response response = UsuarioClient.buscarUsuarioId(id);

        response
                .then()
                .log().body()
                .statusCode(404)
        ;

        logResposta("GET/usuarios/"+id, response);
    }

    @Test
    public void cadastrarUsuarioEmailDuplicado() {
        Response response1 = UsuarioHelper.novoUsuario();

        response1.then()
                .statusCode(201)
                .log().body()
        ;
        Response response2 = UsuarioHelper.criarUsuarioComEmail(response1.path("email"));

        response2.then()
                .statusCode(500)
                .log().body()
                ;

        logResposta("POST/usuarios/", response2);

    }

    @Test
    public void cadastrarUsuarioCpfDuplicado() {
        Response response1 = UsuarioHelper.novoUsuario();

        response1.then()
                .statusCode(201)
                .log().body()
        ;
        Response response2 = UsuarioHelper.criarUsuarioComCPF(response1.path("cpf"));

        response2.then()
                .statusCode(500)
                .log().body()
                ;

        logResposta("POST/usuarios/", response2);

    }

    @Test
    public void atualizacaoNomeUsuarioId() {
        UsuarioModel usuario = UsuarioFactory.criarUsuario();

        Response response = UsuarioClient.criarUsuario(usuario);

        response
                .then()
                .log().body()
        ;

        int id = response.jsonPath().getInt("id");

        usuario.setNome("Nome Atualizado");

        Response responseAtualizar = UsuarioClient.atualizarUsuario(id, usuario);

        responseAtualizar
                .then()
                .statusCode(204)
                ;

        logResposta("PUT/usuarios/"+id, responseAtualizar);

    }

    @Test
    public void atualizacaoEmailUsuarioId() {
        UsuarioModel usuario = UsuarioFactory.criarUsuario();

        Response response = UsuarioClient.criarUsuario(usuario);

        response
                .then()
                .log().body()
        ;

        int id = response.jsonPath().getInt("id");

        usuario.setEmail("NomeAtualizado@hotmail.com");

        Response responseAtualizar = UsuarioClient.atualizarUsuario(id, usuario);

        responseAtualizar
                .then()
                .statusCode(204)
                ;

        logResposta("PUT/usuarios/"+id, responseAtualizar);

    }

    @Test
    public void atualizacaoCpfUsuarioId() {
        UsuarioModel usuario = UsuarioFactory.criarUsuario();

        Response response = UsuarioClient.criarUsuario(usuario);

        response
                .then()
                .log().body()
        ;

        int id = response.jsonPath().getInt("id");

        usuario.setCpf(DataFakerUtils.cpf());

        Response responseAtualizar = UsuarioClient.atualizarUsuario(id, usuario);

        responseAtualizar
                .then()
                .statusCode(204)
        ;

        logResposta("PUT/usuarios/"+id, responseAtualizar);

    }

    @Test
    public void atualizacaoDadosAdicionaisUsuarioId() {
        UsuarioModel usuario = UsuarioFactory.criarUsuario();

        Response response = UsuarioClient.criarUsuario(usuario);

        response
                .then()
                .log().body()
        ;

        int id = response.jsonPath().getInt("id");

        usuario = UsuarioFactory.dadosAdicionaisUsuario();

        Response responseAtualizar = UsuarioClient.atualizarUsuarioDadoAdicionais(id, usuario);

        responseAtualizar
                .then()
                .statusCode(204)
        ;

        logResposta("PUT/usuarios/"+id+"/dados-adicionais", responseAtualizar);

    }

    @Test
    public void atualizacaoAlterarSenhaUsuarioId() {
        UsuarioModel usuario = UsuarioFactory.criarUsuario();

        Response response = UsuarioClient.criarUsuario(usuario);

        response
                .then()
                .log().body()
        ;

        int id = response.jsonPath().getInt("id");

        Map<String, String> senhaPayload = new HashMap<>();
        senhaPayload.put("senhaAtual", usuario.getSenha());
        senhaPayload.put("senhaNova", "Teste@321");

        Response responseAtualizar = UsuarioClient.atualizarUsuarioAlterarSenha(id, senhaPayload);

        responseAtualizar
                .then()
                .statusCode(204)
        ;

        logResposta("PUT/usuarios/"+id+"/alterar-senha", responseAtualizar);

    }

    @Test
    public void deleteUsuarioId() {
        Response response = UsuarioClient.criarUsuario(UsuarioFactory.criarUsuario());

        int id = response.jsonPath().getInt("id");

        Response response1 = UsuarioClient.deleteUsuarioId(id);

        logResposta("DELETE/usuarios", response1);

    }

}
