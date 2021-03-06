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
                .expect()
                .statusCode(200)
                .when()
                .post(Constants.EndPoints.TOKEN);
    }

    public static String getTokenGuest() {
        return getGuest().getBody().jsonPath().getString("access_token");
    }

    public static Response registrationPlayer(String body, String bearerToken) {
        return given()
                .header("Authorization", "Bearer " + bearerToken)
                .body(body)
                .when()
                .post(Constants.EndPoints.PLAYERS);
    }

    public static Response authorizationViaPlayer(String username, String password) {
        String body = "{\"grant_type\":\"password\",\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";

        return given()
                .auth()
                .preemptive()
                .basic(Constants.GUEST_LOGIN, Constants.GUEST_PASSWORD)
                .body(body)
                .when()
                .post(Constants.EndPoints.TOKEN);
    }

    public static Response getProfileData(String tokenPlayer, String idPlayer) {
        return given()
                .header("Authorization", "Bearer " + tokenPlayer)
                .when()
                .get(Constants.EndPoints.PLAYER, idPlayer);
    }
}
