package models;

import lombok.Data;

@Data
public class UsuarioModel {

    private Integer id;
    private String nome;
    private String email;
    private String confirmarEmail;
    private String senha;
    private String confirmarSenha;
    private String cpf;
    private String dataDeNascimento;
    private Boolean checkTermo;
    private String cidade;
    private String estado;
    private String pais;
    private byte[] foto;
    private String senhaAtual;
    private String senhaNova;

}
