package apiTests;

import apiTests.data.Constants;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseClass {
    @BeforeAll
    void setUp() {
        RestAssured.baseURI = Constants.BASE_URL;
    }
}
