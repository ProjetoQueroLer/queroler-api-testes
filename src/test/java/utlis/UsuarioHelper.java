package utlis;

import clients.UsuarioClient;
import factories.UsuarioFactory;
import io.restassured.response.Response;
import report.ExtentReportManager;

public class UsuarioHelper {

    private int extrairId(Response response) {
        return response.path("id");
    }

    public static String extrairEmail(Response response) {
        return response.path("email");
    }

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

    public static Response novoUsuario() {
        var usuario = UsuarioFactory.criarUsuario();
        return UsuarioClient.criarUsuario(usuario);
    }

    public static Response criarUsuarioComEmail(String email) {
        var usuario = UsuarioFactory.criarUsuario();
        usuario.setEmail(email);
        usuario.setConfirmarEmail(email);
        return UsuarioClient.criarUsuario(usuario);
    }

    public static Response criarUsuarioComCPF(String cpf) {
        var usuario = UsuarioFactory.criarUsuario();
        usuario.setCpf(cpf);
        return UsuarioClient.criarUsuario(usuario);
    }
}
