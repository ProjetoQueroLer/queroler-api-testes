package clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utlis.EndPoints;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static io.restassured.RestAssured.given;

public class LivrosClient {

    public static Response criarLivro(String token, Object livro) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String dadosJson = mapper.writeValueAsString(livro);

        RequestSpecification request = given()
                .cookie("jwt", token)
                .contentType(ContentType.MULTIPART)
                .multiPart("dados", dadosJson, "application/json");
        return request
                .when()
                .post(EndPoints.LIVROS);
    }

    public static Response criarLivro(String token, Object livro, File imagem) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
//        String dadosJson = mapper.writeValueAsString(livro);
        String contentType = Files.probeContentType(imagem.toPath());

        RequestSpecification request = given()
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

    public static Response buscarLivros(String token) {
        return given()
                .cookie("jwt", token)
            .when()
                .get(EndPoints.LIVROS);
    }

    public static Response buscarLivroIsbn(String token, String isbn) {
        return given()
                .cookie("jwt", token)
                .pathParam("isbn", isbn)
                .when()
                .get(EndPoints.LIVROS_ISBN);
    }

    public static Response buscarLivroIdCapa(String token, String id) {
        return given()
                .cookie("jwt", token)
                .pathParam("id", id)
                .when()
                .get(EndPoints.LIVROS_ID_CAPA);
    }

}
