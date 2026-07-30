package clients;

import baseTest.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.LeituraModel;
import utils.EndPoints;

import static io.restassured.RestAssured.given;

public class LeituraClient {

    public static Response criarLeitura(String token, LeituraModel body) {

        return given(BaseTest.requestSpecification)
                .cookie("jwt", token)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(EndPoints.LEITURAS);
    }

    public static Response deletarLeitura(String token, int id) {

        return given(BaseTest.requestSpecification)
                .cookie("jwt", token)
                .contentType(ContentType.JSON)
                .pathParam("livroId", id)
                .when()
                .delete(EndPoints.LEITURAS_LIVROID);
    }
}
