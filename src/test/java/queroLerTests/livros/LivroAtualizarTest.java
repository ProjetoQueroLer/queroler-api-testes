package queroLerTests.livros;

import baseTest.BaseTest;
import clients.LivrosClient;
import factories.LivroFactory;
import io.restassured.response.Response;
import models.LivroModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import report.Setup;
import utils.LivroHelper;
import utils.UsuarioHelper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static utils.UsuarioHelper.logResposta;

@ExtendWith(Setup.class)
public class LivroAtualizarTest extends BaseTest {

    @Test
    public void atualizacaoLivroComCapaImagem() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroIsbn10();
        File imagem = new File("src/test/resources/imagens/jpg.jpg");

        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro, imagem);
        responseLivro
                .then()
                .log().body()
                .statusCode(201);

        Response responseBusca = LivrosClient.buscarLivros(token);

        String titulo = livro.getTitulo();

        List<Map<String, Object>> livros =
                responseBusca.jsonPath().getList("content");

        String urlCapa = null;

        for (Map<String, Object> item : livros) {

            if (titulo.trim().equals(item.get("titulo").toString().trim())) {
                urlCapa = (String) item.get("urlCapaDoLivro");
                break;
            }
        }

        if (urlCapa == null) {
            throw new RuntimeException("Livro não encontrado no GET /livros: " + titulo);
        }

        Integer idLivro = Integer.parseInt(urlCapa.split("/")[2]);

        File novaImagem = new File("src/test/resources/imagens/png.png");

        Response responseAtualizacao = LivroHelper.atualizarCapaLivro(token, idLivro, novaImagem);

        responseAtualizacao
                .then()
                .log().body()
                .statusCode(204);

        logResposta("PUT/livros/" + idLivro + "/capa", responseAtualizacao);

    }

}
