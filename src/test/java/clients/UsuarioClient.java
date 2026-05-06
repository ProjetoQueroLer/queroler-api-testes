package clients;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.UsuarioModel;
import utlis.EndPoints;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class UsuarioClient {

    public static Response criarUsuario(Object body) {
        return given()
                .contentType(ContentType.JSON)
                .body(body)
            .when()
                .post(EndPoints.USUARIOS);
    }

    public static Response buscarUsuarioId(int id) {
        return given()
                .pathParam("id", id)
            .when()
                .get(EndPoints.USUARIOS_ID);
    }

    public static Response atualizarUsuario(int id, UsuarioModel usuario) {
        return given()
                .pathParam("id", id)
                .contentType(ContentType.JSON)
                .body(usuario)
            .when()
                .put(EndPoints.USUARIOS_ID);
    }

    public static Response atualizarUsuarioDadoAdicionais(int id, UsuarioModel usuario) {
        return given()
                .pathParam("id", id)
                .contentType(ContentType.JSON)
                .body(usuario)
            .when()
                .put(EndPoints.USUARIOS_ID_DADOS_ADICIONAIS);
    }

    public static Response atualizarUsuarioAlterarSenha(int id, String senhaAtual, String senhaNova) {
        Map<String, String> payload = new HashMap<>();
        payload.put("senhaAtual", senhaAtual);
        payload.put("senhaNova", senhaNova);

        return given()
                .pathParam("id", id)
                .contentType(ContentType.JSON)
                .body(payload)
            .when()
                .put(EndPoints.USUARIOS_ID_ALTERAR_SENHA);
    }

    public static Response deleteUsuarioId(int id) {
        return given()
                .pathParam("id", id)
            .when()
                .delete(EndPoints.USUARIOS_ID);
    }

}
