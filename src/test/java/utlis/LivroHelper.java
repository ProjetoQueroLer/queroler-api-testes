package utlis;

import clients.LivrosClient;
import io.restassured.response.Response;
import models.LivroModel;

import java.io.File;
import java.io.IOException;

public class LivroHelper {

    public static Response criarLivroCadastrar(String token, LivroModel livro, File imagem) throws IOException {
        return LivrosClient.criarLivro(token, livro, imagem);
    }

    public static Response criarLivroCadastrar(String token, LivroModel livro) throws IOException {
        return LivrosClient.criarLivro(token, livro);
    }
}
