package apiTests.methods;

import apiTests.data.Constants;
import apiTests.data.DataGuest;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Methods {
    public static Response getGuest() {
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
                .post("/v2/oauth2/token");
    }

    public static String getTokenGuest() {
        return getGuest().getBody().jsonPath().getString("access_token");
    }

    public static Response registrationPlayer(String body, String bearerToken) {
        return given()
                .header("Authorization", "Bearer " + bearerToken)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/v2/players");
    }

    public static Response authorizationViaPlayer(String username, String password) {
        String body = "{\"grant_type\":\"password\",\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";

        return given()
                .auth()
                .preemptive()
                .basic(Constants.GUEST_LOGIN, Constants.GUEST_PASSWORD)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/v2/oauth2/token");
    }

    public static Response getProfileData(String tokenPlayer, String idPlayer) {
        return given()
                .header("Authorization", "Bearer " + tokenPlayer)
                .when()
                .get("/v2/players/" + idPlayer);
    }
}
