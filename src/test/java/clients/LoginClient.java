package clients;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.EndPoints;

import static io.restassured.RestAssured.given;

public class LoginClient {

    public static Response acessarLogin(Object body) {
        return given()
                .contentType(ContentType.JSON)
                .body(body)
            .when()
                .post(EndPoints.LOGINS);
    }
}
