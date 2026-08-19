package factories;

import models.UsuarioModel;
import utils.DataFakerUtils;

public class UsuarioFactory {

    public static UsuarioModel criarUsuarioExistenteAntesTeste() {

        UsuarioModel usuario = new UsuarioModel();

        usuario.setNome("Teste");
        usuario.setEmail("testes@testes.com");
        usuario.setConfirmarEmail("testes@testes.com");
        usuario.setSenha("Teste@123");
        usuario.setConfirmarSenha("Teste@123");
        usuario.setCpf("472.590.180-62");
        usuario.setDataDeNascimento("03/11/1992");
        usuario.setCheckTermo(true);

        return usuario;
    }

    public static UsuarioModel criarUsuario() {

        UsuarioModel usuario = new UsuarioModel();

        usuario.setNome(DataFakerUtils.nome());
        String email = DataFakerUtils.email();
        usuario.setEmail(email);
        usuario.setConfirmarEmail(email);
        usuario.setSenha("Teste@123");
        usuario.setConfirmarSenha("Teste@123");
        usuario.setCpf(DataFakerUtils.cpf());
        usuario.setDataDeNascimento(DataFakerUtils.dataNascimento());
        usuario.setCheckTermo(true);

        return usuario;
    }

    public static UsuarioModel criarUsuarioSenhaDiferenteConfirmarSenha() {

        UsuarioModel usuario = new UsuarioModel();

        usuario.setNome(DataFakerUtils.nome());
        String email = DataFakerUtils.email();
        usuario.setEmail(email);
        usuario.setConfirmarEmail(email);
        usuario.setSenha(DataFakerUtils.senhaAleatoria());
        usuario.setConfirmarSenha(DataFakerUtils.senhaAleatoria());
        usuario.setCpf(DataFakerUtils.cpf());
        usuario.setDataDeNascimento(DataFakerUtils.dataNascimento());
        usuario.setCheckTermo(true);

        return usuario;
    }

    public static UsuarioModel criarUsuarioMenos8caracteres() {

        UsuarioModel usuario = new UsuarioModel();

        usuario.setNome(DataFakerUtils.nome());
        String email = DataFakerUtils.email();
        usuario.setEmail(email);
        usuario.setConfirmarEmail(email);
        usuario.setSenha("Te@123");
        usuario.setConfirmarSenha("Te@123");
        usuario.setCpf(DataFakerUtils.cpf());
        usuario.setDataDeNascimento(DataFakerUtils.dataNascimento());
        usuario.setCheckTermo(true);

        return usuario;
    }

    public static UsuarioModel criarUsuarioMais100caracteres() {

        UsuarioModel usuario = new UsuarioModel();

        usuario.setNome(DataFakerUtils.nome());
        String email = DataFakerUtils.email();
        usuario.setEmail(email);
        usuario.setConfirmarEmail(email);
        String caracteres = DataFakerUtils.caracteresComQuantidade(101);
        usuario.setSenha(caracteres);
        usuario.setConfirmarSenha(caracteres);
        usuario.setCpf(DataFakerUtils.cpf());
        usuario.setDataDeNascimento(DataFakerUtils.dataNascimento());
        usuario.setCheckTermo(true);

        return usuario;
    }

    public static UsuarioModel criarUsuarioNomeMais80Caracteres() {

        UsuarioModel usuario = new UsuarioModel();

        usuario.setNome(DataFakerUtils.caracteresComQuantidade(81));
        String email = DataFakerUtils.email();
        usuario.setEmail(email);
        usuario.setConfirmarEmail(email);
        usuario.setSenha("Teste@123");
        usuario.setConfirmarSenha("Teste@123");
        usuario.setCpf(DataFakerUtils.cpf());
        usuario.setDataDeNascimento(DataFakerUtils.dataNascimento());
        usuario.setCheckTermo(true);

        return usuario;
    }

    public static UsuarioModel criarUsuarioEmailMais256Caracteres(String numero) {

        UsuarioModel usuario = new UsuarioModel();

        usuario.setNome(DataFakerUtils.nome());
        String email = DataFakerUtils.emailMais256Caracteres(numero);
        usuario.setEmail(email);
        usuario.setConfirmarEmail(email);
        usuario.setSenha("Teste@123");
        usuario.setConfirmarSenha("Teste@123");
        usuario.setCpf(DataFakerUtils.cpf());
        usuario.setDataDeNascimento(DataFakerUtils.dataNascimento());
        usuario.setCheckTermo(true);

        return usuario;
    }

    public static UsuarioModel criarUsuarioEmailDiferenteEmailConfirmar() {

        UsuarioModel usuario = new UsuarioModel();

        usuario.setNome(DataFakerUtils.nome());
        usuario.setEmail(DataFakerUtils.email());
        usuario.setConfirmarEmail(DataFakerUtils.email());
        usuario.setSenha("Teste@123");
        usuario.setConfirmarSenha("Teste@123");
        usuario.setCpf(DataFakerUtils.cpf());
        usuario.setDataDeNascimento(DataFakerUtils.dataNascimento());
        usuario.setCheckTermo(true);

        return usuario;
    }

    public static UsuarioModel criarUsuarioEmailInvalido(String email) {

        UsuarioModel usuario = new UsuarioModel();

        usuario.setNome(DataFakerUtils.nome());
        usuario.setEmail(email);
        usuario.setConfirmarEmail(email);
        usuario.setSenha("Teste@123");
        usuario.setConfirmarSenha("Teste@123");
        usuario.setCpf(DataFakerUtils.cpf());
        usuario.setDataDeNascimento(DataFakerUtils.dataNascimento());
        usuario.setCheckTermo(true);

        return usuario;
    }

    public static UsuarioModel criarUsuarioEmailVazio() {

        UsuarioModel usuario = new UsuarioModel();

        usuario.setNome(DataFakerUtils.nome());
        usuario.setEmail("");
        usuario.setConfirmarEmail("");
        usuario.setSenha("Teste@123");
        usuario.setConfirmarSenha("Teste@123");
        usuario.setCpf(DataFakerUtils.cpf());
        usuario.setDataDeNascimento(DataFakerUtils.dataNascimento());
        usuario.setCheckTermo(true);

        return usuario;
    }

    public static UsuarioModel dadosAdicionaisUsuario() {

        UsuarioModel usuario = new UsuarioModel();

        usuario.setCidade(DataFakerUtils.cidade());
        usuario.setEstado(DataFakerUtils.estado());
        usuario.setPais(DataFakerUtils.pais());

        return usuario;
    }

}
