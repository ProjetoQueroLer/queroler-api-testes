package clients;

import baseTest.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.EndPoints;


import static io.restassured.RestAssured.given;

public class DiarioClient {

    public static Response criarDiario(String token, Object body) {

        return given(BaseTest.requestSpecification)
                .cookie("jwt", token)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(EndPoints.DIARIO);
    }

    public static Response buscarDiarioLivroId(String token, int id) {

        return given(BaseTest.requestSpecification)
                .cookie("jwt", token)
                .contentType(ContentType.JSON)
                .queryParam("livroId", id)
                .when()
                .get(EndPoints.DIARIO);
    }

    public static Response buscarDiarioPorLivro(String token, int id) {

        return given(BaseTest.requestSpecification)
                .cookie("jwt", token)
                .contentType(ContentType.JSON)
                .queryParam("livroId", id)
                .when()
                .get(EndPoints.DIARIO);
    }

}
