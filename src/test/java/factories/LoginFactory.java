package factories;

import clients.UsuarioClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import models.LoginModel;
import models.UsuarioModel;
import utils.DataFakerUtils;
import utlis.ConfigProperties;

public class LoginFactory {

    public static LoginModel usuarioLeitorSucesso() throws JsonProcessingException {
        UsuarioModel usuario = UsuarioFactory.criarUsuario();
        UsuarioClient.criarUsuario(usuario);

        LoginModel usuarioLogin = new LoginModel();
        usuarioLogin.setUser(usuario.getEmail());
        usuarioLogin.setSenha(usuario.getSenha());
        return usuarioLogin;
    }

    public static LoginModel usuarioLeitorSenhaInvalida() throws JsonProcessingException {
        UsuarioModel usuario = UsuarioFactory.criarUsuario();
        UsuarioClient.criarUsuario(usuario);

        LoginModel usuarioLogin = new LoginModel();
        usuarioLogin.setUser(usuario.getEmail());
        usuarioLogin.setSenha(DataFakerUtils.senhaAleatoria());
        return usuarioLogin;
    }

    public static LoginModel usuarioLeitorEmailInexistente() {
        LoginModel usuarioLogin = new LoginModel();
        usuarioLogin.setUser(DataFakerUtils.email());
        usuarioLogin.setSenha(DataFakerUtils.senhaAleatoria());
        return usuarioLogin;
    }

    public static LoginModel usuarioAdministradorEmailComSucesso() {
        LoginModel usuarioLogin = new LoginModel();
        usuarioLogin.setUser(ConfigProperties.get("admin.email"));
        usuarioLogin.setSenha(ConfigProperties.get("admin.password"));
        return usuarioLogin;
    }

    public static LoginModel usuarioAdministradorSenhaInvalida() {
        LoginModel usuarioLogin = new LoginModel();
        usuarioLogin.setUser(ConfigProperties.get("admin.email"));
        usuarioLogin.setSenha(DataFakerUtils.senhaAleatoria());
        return usuarioLogin;
    }

    public static LoginModel usuarioModeradorEmailComSucesso() {
        LoginModel usuarioLogin = new LoginModel();
        usuarioLogin.setUser(ConfigProperties.get("moderador.email"));
        usuarioLogin.setSenha(ConfigProperties.get("moderador.password"));
        return usuarioLogin;
    }

    public static LoginModel usuarioModeradorSenhaInvalida() {
        LoginModel usuarioLogin = new LoginModel();
        usuarioLogin.setUser(ConfigProperties.get("moderador.email"));
        usuarioLogin.setSenha(DataFakerUtils.senhaAleatoria());
        return usuarioLogin;
    }
}
