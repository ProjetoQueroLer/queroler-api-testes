package queroLerTests.usuarios;

import baseTest.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import report.ExtentReportManager;
import report.Setup;

@ExtendWith(Setup.class)
public class QueroLerUsuarioTest extends BaseTest {

    @Test
    public void getUsuarioId() {
        Response response =
        RestAssured.given()
                .spec(requestSpecification)
            .when()
                .get(USUARIOS+2)
                ;

        ExtentReportManager.logInfoDetails("GET /usuarios/{ID}");
        ExtentReportManager.logInfoDetails("Status Code: " + response.getStatusCode());
        ExtentReportManager.logJson(response.getBody().asPrettyString());
        ExtentReportManager.logHeaders(response.getHeaders());

        Assertions.assertEquals(200, response.getStatusCode());
    }
}
