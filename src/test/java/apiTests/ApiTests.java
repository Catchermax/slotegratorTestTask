package apiTests;

import apiTests.data.Constants;
import apiTests.data.DataGuest;
import apiTests.data.DataPlayer;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.Assert.*;

public class ApiTests {
    Faker faker = new Faker();
    String guestToken = getGuest().getBody().jsonPath().getString("access_token");

    @Test
    void getGuestTokenTest() {
        Response response = getGuest();
        response.then().statusCode(200).body(matchesJsonSchemaInClasspath("schema/tokenGuestSchema.json"));
    }

    @Test
    void registrationPlayerWithFullDataTest() {
        String password = Base64.encodeBase64String(faker.letterify("????????").getBytes(StandardCharsets.UTF_8));
        Gson gson = new Gson();

        DataPlayer dataPlayer = new DataPlayer(
                faker.name().username(),
                password,
                password,
                faker.letterify("????????@example.com"),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.currency().code()
        );

        given()
                .header("Authorization", "Bearer " + guestToken)
                .contentType(ContentType.JSON)
                .body(gson.toJson(dataPlayer))
                .when()
                .post("http://test-api.d6.dev.devcaz.com/v2/players")
                .then()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("schema/createUserSchema.json"));
    }

    @Test
    void registrationPlayerWithRequiredDataTest() {
        String password = Base64.encodeBase64String(faker.letterify("????????").getBytes(StandardCharsets.UTF_8));
        Gson gson = new Gson();

        DataPlayer dataPlayer = new DataPlayer(
                faker.name().username(),
                password,
                password,
                faker.letterify("????????@example.com"),
                null,
                null,
                null
        );

        given()
                .header("Authorization", "Bearer " + guestToken)
                .contentType(ContentType.JSON)
                .body(gson.toJson(dataPlayer))
                .when()
                .post("http://test-api.d6.dev.devcaz.com/v2/players")
                .then()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("schema/createUserSchema.json"));
    }

    @Test
    void registrationTwoPlayersWithEqualsUsername() {
        String userName = faker.name().username();
        String password = Base64.encodeBase64String(faker.letterify("????????").getBytes(StandardCharsets.UTF_8));
        Gson gson = new Gson();

        DataPlayer dataPlayer1 = new DataPlayer(
                userName,
                password,
                password,
                faker.letterify("????????@example.com"),
                null,
                null,
                null
        );

        DataPlayer dataPlayer2 = new DataPlayer(
                userName,
                password,
                password,
                faker.letterify("????????@example.com"),
                null,
                null,
                null
        );

        given()
                .header("Authorization", "Bearer " + guestToken)
                .contentType(ContentType.JSON)
                .body(gson.toJson(dataPlayer1))
                .when()
                .post("http://test-api.d6.dev.devcaz.com/v2/players")
                .then()
                .statusCode(201);

        given()
                .header("Authorization", "Bearer " + guestToken)
                .contentType(ContentType.JSON)
                .body(gson.toJson(dataPlayer1))
                .when()
                .post("http://test-api.d6.dev.devcaz.com/v2/players")
                .then()
                .statusCode(422);
    }

    @Test
    void registrationTwoPlayersWithEqualsEmail() {
        String email = faker.letterify("????????@example.com");
        String password = Base64.encodeBase64String(faker.letterify("????????").getBytes(StandardCharsets.UTF_8));
        Gson gson = new Gson();

        DataPlayer dataPlayer1 = new DataPlayer(
                faker.name().username(),
                password,
                password,
                email,
                null,
                null,
                null
        );

        DataPlayer dataPlayer2 = new DataPlayer(
                faker.name().username(),
                password,
                password,
                email,
                null,
                null,
                null
        );

        given()
                .header("Authorization", "Bearer " + guestToken)
                .contentType(ContentType.JSON)
                .body(gson.toJson(dataPlayer1))
                .when()
                .post("http://test-api.d6.dev.devcaz.com/v2/players")
                .then()
                .statusCode(201);

        given()
                .header("Authorization", "Bearer " + guestToken)
                .contentType(ContentType.JSON)
                .body(gson.toJson(dataPlayer1))
                .when()
                .post("http://test-api.d6.dev.devcaz.com/v2/players")
                .then()
                .statusCode(422);
    }

    Response getGuest() {
        DataGuest dataGuest = new DataGuest();
        Gson gson = new Gson();
        gson.toJson(dataGuest);

        return given()
                .auth()
                .preemptive()
                .basic(Constants.GUEST_LOGIN, Constants.GUEST_PASSWORD)
                .body(gson.toJson(dataGuest))
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .contentType(ContentType.JSON)
                .expect()
                .statusCode(200)
                .when()
                .post("http://test-api.d6.dev.devcaz.com/v2/oauth2/token");
    }
}