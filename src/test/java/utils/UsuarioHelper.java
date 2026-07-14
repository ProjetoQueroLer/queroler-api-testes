package utils;

import clients.LoginClient;
import clients.UsuarioClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import factories.UsuarioFactory;
import io.restassured.response.Response;
import models.LoginModel;
import models.UsuarioModel;
import report.ExtentReportManager;

import java.io.File;
import java.io.IOException;

public class UsuarioHelper {

    public static void logResposta(String endpoint, Response response) {
        ExtentReportManager.logInfoDetails(endpoint);
        ExtentReportManager.logInfoDetails("Status Code: " + response.getStatusCode());
        String contentType = response.getContentType();
        if (contentType != null && contentType.contains("application/json")) {
            ExtentReportManager.logJson(response.getBody().asPrettyString());
        } else {
            ExtentReportManager.logInfoDetails("Body: " + response.getBody().asString());
        }
        ExtentReportManager.logHeaders(response.getHeaders());
    }

    public static void garantirUsuarioExiste() {
        UsuarioModel usuario = UsuarioFactory.criarUsuarioExistenteAntesTeste();

        try {
            Response response = UsuarioClient.criarUsuario(usuario);

            if (response.statusCode() == 201) {

            } else if (response.statusCode() == 409) {

            } else {
                throw new RuntimeException("Erro ao criar usuário: " + response.asPrettyString());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Response criarUsuarioCadastrar(UsuarioModel usuario) throws JsonProcessingException {

        return UsuarioClient.criarUsuario(usuario);

    }

    public static Response criarUsuarioCadastrar(UsuarioModel usuario, File imagem) throws IOException {

        return UsuarioClient.criarUsuario(usuario, imagem);

    }

    public static String acessarLoginGerarToken(UsuarioModel usuario) {

        LoginModel login = new LoginModel();
        login.setUser(usuario.getEmail());
        login.setSenha(usuario.getSenha());

        return LoginClient
                .acessarLogin(login)
                .getCookie("jwt");
    }

    public static String loginLeitor() {
        LoginModel usuarioLogin = new LoginModel();
        usuarioLogin.setUser(ConfigProperties.get("existenteUsuario.email"));
        usuarioLogin.setSenha(ConfigProperties.get("existenteUsuario.password"));
        return LoginClient.acessarLogin(usuarioLogin)
                .then()
                .statusCode(200)
                .extract()
                .cookie("jwt");
    }

}
