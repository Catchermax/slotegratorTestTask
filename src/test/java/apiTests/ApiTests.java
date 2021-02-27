package apiTests;

import gherkin.deps.net.iharder.Base64;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class ApiTests {
//    String authCookie = Base64.encode(String.format("%s:%s", "johndoe", "am9obmRvZTEyMw=="));
    String jsonBody = "{\n" +
        "\"grant_type\":\"client_credentials\", \"scope\":\"scope1:read scope1:write scope2:read\"\n" +
        "}";

    @Test
    void registrationPlayerTest() {
        given()
                .auth()
                .form("johndoe", "am9obmRvZTEyMw==")
//                .preemptive()
//                .basic("johndoe", "am9obmRvZTEyMw==")
                .body(jsonBody)
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .contentType(ContentType.JSON)
//                .header("Authorization", "Basic c29tZWNsaWVudDphbmRpdHNzZWNyZXQ=")
                .expect()
                .statusCode(200)
                .when()
                .post("http://test-api.d6.dev.devcaz.com/v2/oauth2/token")
                .getBody().asString();
    }
}