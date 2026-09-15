package clients;

import baseTest.BaseTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.EndPoints;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static io.restassured.RestAssured.given;

public class LivrosClient {

    public static Response criarLivro(String token, Object livro) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        RequestSpecification request = given(BaseTest.requestSpecification)
                .cookie("jwt", token)
                .contentType(ContentType.MULTIPART)
                .multiPart("dados", mapper.writeValueAsString(livro), "application/json");
        return request
                .when()
                .post(EndPoints.LIVROS);
    }

    public static Response criarLivro(String token, Object livro, File imagem) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String contentType = Files.probeContentType(imagem.toPath());

        RequestSpecification request = given(BaseTest.requestSpecification)
                .cookie("jwt", token)
                .contentType(ContentType.MULTIPART)
                .multiPart("dados", objectMapper.writeValueAsString(livro), "application/json");
        if (imagem != null) {
            request.multiPart("imagem", imagem, contentType);
        }
        return request
                .when()
                .post(EndPoints.LIVROS);
    }

    public static Response atualizarCapaLivro(String token, Integer idLivro, File imagem) {

        return given(BaseTest.requestSpecification)
                .cookie("jwt", token)
                .contentType(ContentType.MULTIPART)
                .multiPart("imagem", imagem, "image/jpeg")
                .pathParam("id", idLivro)
            .when()
                .put(EndPoints.LIVROS_ID_CAPA);
    }

    public static Response buscarLivros(String token) {
        return given(BaseTest.requestSpecification)
                .cookie("jwt", token)
                .queryParam("page", 0)
                .queryParam("size", 100)
            .when()
                .get(EndPoints.LIVROS);
    }

    public static Response buscarLivroId(String token, int id) {
        return given(BaseTest.requestSpecification)
                .cookie("jwt", token)
                .pathParam("id", id)
                .when()
                .get(EndPoints.LIVROS_ID);
    }

    public static Response buscarLivroIsbn(String token, String isbn) {
        return given(BaseTest.requestSpecification)
                .cookie("jwt", token)
                .pathParam("isbn", isbn)
                .when()
                .get(EndPoints.LIVROS_ISBN);
    }

    public static Response buscarLivroIdCapa(String token, int id) {
        return given(BaseTest.requestSpecification)
                .cookie("jwt", token)
                .pathParam("id", id)
                .when()
                .get(EndPoints.LIVROS_ID_CAPA);
    }

    public static Response pesquisarLivroTitulo(String token, String titulo) {
        return given(BaseTest.requestSpecification)
                .cookie("jwt", token)
                .queryParam("titulo", titulo)
                .when()
                .get(EndPoints.LIVROS);
    }

    public static Response pesquisarLivroAutor(String token, String autor) {
        return given(BaseTest.requestSpecification)
                .cookie("jwt", token)
                .queryParam("autor", autor)
                .when()
                .get(EndPoints.LIVROS);
    }

    public static Response pesquisarLivroEditora(String token, String editora) {
        return given(BaseTest.requestSpecification)
                .cookie("jwt", token)
                .queryParam("editora", editora)
                .when()
                .get(EndPoints.LIVROS);
    }

    public static Response pesquisarLivro(String token, String livro) {
        return given(BaseTest.requestSpecification)
                .cookie("jwt", token)
                .queryParam("isbn", livro)
                .when()
                .get(EndPoints.LIVROS);
    }

    public static Response pesquisarLivroOrdenadoPorDataCadastroDesc(String token) {
        return given(BaseTest.requestSpecification)
                .cookie("jwt", token)
                .queryParam("sort", "dataDeCadastro,desc")
                .when()
                .get(EndPoints.LIVROS);
    }

    public static Response listarComentariosPorLivro(String token, int idLivro) {
        return given(BaseTest.requestSpecification)
                .cookie("jwt", token)
                .pathParam("id", idLivro)
                .when()
                .get(EndPoints.LIVROS_ID_COMENTARIOS);
    }

    public static Response buscarLivroTelaLeitura(String token) {
        return given(BaseTest.requestSpecification)
                .cookie("jwt", token)
                .when()
                .get(EndPoints.LIVROS_TELA_DE_LEITURA);
    }

    public static Response buscarLivroPopulares(String token) {
        return given(BaseTest.requestSpecification)
                .cookie("jwt", token)
                .when()
                .get(EndPoints.LIVROS_POPULARES);
    }

    public static Response buscarLivroDetalhado(String token) {
        return given(BaseTest.requestSpecification)
                .cookie("jwt", token)
                .when()
                .get(EndPoints.LIVROS_DETALHADOS);
    }
}
