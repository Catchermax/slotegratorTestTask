package apiTests;

import apiTests.data.DataPlayer;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.response.Response;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static apiTests.methods.Methods.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ApiTests extends BaseClass {
    Faker faker = new Faker();
    Gson gson = new Gson();

    @Test
    void getGuestTokenTest() {
        Response response = getGuest();
        response.then().statusCode(200).body(matchesJsonSchemaInClasspath("schema/tokenGuestSchema.json"));
    }

    @Test
    void registrationPlayerWithFullDataTest() {
        String password = Base64.encodeBase64String(faker.letterify("????????").getBytes(StandardCharsets.UTF_8));

        DataPlayer dataPlayer = new DataPlayer(
                faker.name().username(),
                password,
                password,
                faker.letterify("????????@example.com"),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.currency().code()
        );

        registrationPlayer(gson.toJson(dataPlayer), getTokenGuest())
                .then()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("schema/createUserSchema.json"));
    }

    @Test
    void registrationPlayerWithRequiredDataTest() {
        String password = Base64.encodeBase64String(faker.letterify("????????").getBytes(StandardCharsets.UTF_8));

        DataPlayer dataPlayer = new DataPlayer(
                faker.name().username(),
                password,
                password,
                faker.letterify("????????@example.com"),
                null,
                null,
                null
        );

        registrationPlayer(gson.toJson(dataPlayer), getTokenGuest())
                .then()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("schema/createUserSchema.json"));
    }

    @Test
    void registrationTwoPlayersWithEqualsUsername() {
        String userName = faker.name().username();
        String password = Base64.encodeBase64String(faker.letterify("????????").getBytes(StandardCharsets.UTF_8));

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

        registrationPlayer(gson.toJson(dataPlayer1), getTokenGuest())
                .then()
                .statusCode(201);

        registrationPlayer(gson.toJson(dataPlayer2), getTokenGuest())
                .then()
                .statusCode(422);
    }

    @Test
    void registrationTwoPlayersWithEqualsEmail() {
        String email = faker.letterify("????????@example.com");
        String password = Base64.encodeBase64String(faker.letterify("????????").getBytes(StandardCharsets.UTF_8));

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

        registrationPlayer(gson.toJson(dataPlayer1), getTokenGuest())
                .then()
                .statusCode(201);

        registrationPlayer(gson.toJson(dataPlayer2), getTokenGuest())
                .then()
                .statusCode(422);
    }

    @Test
    void authorizationViaCreatedPlayer() {
        String password = Base64.encodeBase64String(faker.letterify("????????").getBytes(StandardCharsets.UTF_8));

        DataPlayer dataPlayer = new DataPlayer(
                faker.name().username(),
                password,
                password,
                faker.letterify("????????@example.com"),
                null,
                null,
                null
        );

        registrationPlayer(gson.toJson(dataPlayer), getTokenGuest())
                .then()
                .statusCode(201);

        authorizationViaPlayer(dataPlayer.username, dataPlayer.password_change)
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schema/tokenGuestSchema.json"));
    }

    @Test
    void getPlayerProfileData() {
        String password = Base64.encodeBase64String(faker.letterify("????????").getBytes(StandardCharsets.UTF_8));
        String idPlayer;
        String tokenPlayer;

        DataPlayer dataPlayer = new DataPlayer(
                faker.name().username(),
                password,
                password,
                faker.letterify("????????@example.com"),
                null,
                null,
                null
        );

        idPlayer = registrationPlayer(gson.toJson(dataPlayer), getTokenGuest())
                .getBody().jsonPath().getString("id");

        tokenPlayer = authorizationViaPlayer(dataPlayer.username, dataPlayer.password_change)
                .getBody().jsonPath().getString("access_token");

        getProfileData(tokenPlayer, idPlayer)
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schema/createUserSchema.json"));
    }

    @Test
    void getAnotherPlayerData() {
        String password = Base64.encodeBase64String(faker.letterify("????????").getBytes(StandardCharsets.UTF_8));
        String idPlayer1;
        String tokenPlayer2;

        DataPlayer dataPlayer1 = new DataPlayer(
                faker.name().username(),
                password,
                password,
                faker.letterify("????????@example.com"),
                null,
                null,
                null
        );

        DataPlayer dataPlayer2 = new DataPlayer(
                faker.name().username(),
                password,
                password,
                faker.letterify("????????@example.com"),
                null,
                null,
                null
        );

        idPlayer1 = registrationPlayer(gson.toJson(dataPlayer1), getTokenGuest())
                .getBody().jsonPath().getString("id");

        registrationPlayer(gson.toJson(dataPlayer2), getTokenGuest())
                .getBody().jsonPath().getString("id");

        tokenPlayer2 = authorizationViaPlayer(dataPlayer2.username, dataPlayer2.password_change)
                .getBody().jsonPath().getString("access_token");

        getProfileData(tokenPlayer2, idPlayer1)
                .then()
                .statusCode(404);
    }
}