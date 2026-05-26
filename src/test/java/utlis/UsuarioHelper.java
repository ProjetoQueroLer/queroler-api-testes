package utlis;

import clients.LoginClient;
import clients.UsuarioClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import factories.UsuarioFactory;
import io.restassured.response.Response;
import models.LoginModel;
import models.UsuarioModel;
import report.ExtentReportManager;

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

    public static Response criarUsuarioCadastrar(UsuarioModel usuario) throws JsonProcessingException {

        return UsuarioClient.criarUsuario(usuario);

    }

    public static String gerarToken(UsuarioModel usuario) {

        LoginModel login = new LoginModel();
        login.setUser(usuario.getEmail());
        login.setSenha(usuario.getSenha());

        return LoginClient
                .acessarLogin(login)
                .getCookie("jwt");
    }

}
