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

    public static UsuarioModel dadosAdicionaisUsuario() {

        UsuarioModel usuario = new UsuarioModel();

        usuario.setCidade(DataFakerUtils.cidade());
        usuario.setEstado(DataFakerUtils.estado());
        usuario.setPais(DataFakerUtils.pais());
//        usuario.setFoto(DataFakerUtils.foto());

        return usuario;
    }

}
