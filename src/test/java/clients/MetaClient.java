package clients;

import baseTest.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.MetaModel;
import utils.EndPoints;

import static io.restassured.RestAssured.given;

public class MetaClient {

    public static Response adicionarMeta(String token, MetaModel meta) {
        return given(BaseTest.requestSpecification)
                .cookie("jwt", token)
                .contentType(ContentType.JSON)
                .body(meta)
                .when()
                .post(EndPoints.METAS);
    }

    public static Response adicionarLivroMeta(String token, int livroId) {
        return given(BaseTest.requestSpecification)
                .cookie("jwt", token)
                .when()
                .put(EndPoints.METAS + "/adicionar-livro/" + livroId);
    }

    public static Response editarMeta(String token, MetaModel meta) {
        return given(BaseTest.requestSpecification)
                .cookie("jwt", token)
                .contentType(ContentType.JSON)
                .body(meta)
                .when()
                .put(EndPoints.METAS);
    }

    public static Response deletarMeta(String token) {
        return given(BaseTest.requestSpecification)
                .cookie("jwt", token)
                .when()
                .delete(EndPoints.METAS);
    }

    public static Response buscarMeta(String token) {
        return given(BaseTest.requestSpecification)
                .cookie("jwt", token)
                .when()
                .get(EndPoints.METAS);
    }

}