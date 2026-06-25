package clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.UsuarioModel;
import utils.EndPoints;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class UsuarioClient {

    public static Response criarUsuario(Object usuario) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        String dadosJson = mapper.writeValueAsString(usuario);
        return given()
                .contentType(ContentType.MULTIPART)
                .multiPart("dados", dadosJson)
            .when()
                .post(EndPoints.USUARIOS);
    }

    public static Response criarUsuario(Object usuario, File imagem) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String contentType = Files.probeContentType(imagem.toPath());

        RequestSpecification request = given()
                .contentType(ContentType.MULTIPART)
                .multiPart("dados", objectMapper.writeValueAsString(usuario), "application/json");
        if (imagem != null) {
            request.multiPart("imagem", imagem, contentType);
        }
        return request
                .when()
                .post(EndPoints.USUARIOS);
    }

    public static Response buscarUsuario(String token) {
        return given()
                .cookie("jwt", token)
            .when()
                .get(EndPoints.USUARIOS);
    }

    public static Response buscarUsuarioFoto(String token) {
        return given()
                .cookie("jwt", token)
            .when()
                .get(EndPoints.USUARIOS_FOTO);
    }

    public static Response atualizarUsuario(String token, UsuarioModel usuario) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return given()
                .cookie("jwt", token)
                .contentType(ContentType.MULTIPART)
                .multiPart("dados", objectMapper.writeValueAsString(usuario),"application/json")
            .when()
                .put(EndPoints.USUARIOS);
    }

    public static Response atualizarUsuarioDadoAdicionais(String token, UsuarioModel usuario, File imagem) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String contentType = Files.probeContentType(imagem.toPath());

        RequestSpecification request = given()
                .cookie("jwt", token)
                .contentType(ContentType.MULTIPART)
                .multiPart("dados", objectMapper.writeValueAsString(usuario),"application/json");
        if (imagem != null) {
            request.multiPart("imagem", imagem, contentType);
        }
        return request
                .when()
                .put(EndPoints.USUARIOS_DADOS_ADICIONAIS);
    }

    public static Response atualizarUsuarioAlterarSenha(String token, String senhaAtual, String senhaNova) {
        Map<String, String> payload = new HashMap<>();
        payload.put("senhaAtual", senhaAtual);
        payload.put("senhaNova", senhaNova);

        return given()
                .cookie("jwt", token)
                .contentType(ContentType.JSON)
                .body(payload)
            .when()
                .put(EndPoints.USUARIOS_ALTERAR_SENHA);
    }

    public static Response deleteUsuarioId(String token) {
        return given()
                .cookie("jwt", token)
            .when()
                .delete(EndPoints.USUARIOS);
    }

}
