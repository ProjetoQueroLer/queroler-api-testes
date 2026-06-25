package factories;

import models.UsuarioModel;
import utils.DataFakerUtils;

public class UsuarioFactory {

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
        String caracteres = DataFakerUtils.caracteres(101);
        System.out.println(caracteres);
        usuario.setSenha(caracteres);
        usuario.setConfirmarSenha(caracteres);
        usuario.setCpf(DataFakerUtils.cpf());
        usuario.setDataDeNascimento(DataFakerUtils.dataNascimento());
        usuario.setCheckTermo(true);

        return usuario;
    }

    public static UsuarioModel criarUsuarioNomeMais80Caracteres() {

        UsuarioModel usuario = new UsuarioModel();

        usuario.setNome(DataFakerUtils.nomeMais80Caracteres());
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

    public static UsuarioModel dadosAdicionaisUsuario() {

        UsuarioModel usuario = new UsuarioModel();

        usuario.setCidade(DataFakerUtils.cidade());
        usuario.setEstado(DataFakerUtils.estado());
        usuario.setPais(DataFakerUtils.pais());

        return usuario;
    }

}
