package uiTests.tests;

import com.codeborne.selenide.Configuration;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;

public class BaseClass {
    @BeforeAll
    void beforeAll() {
        Configuration.baseUrl = " http://test-app.d6.dev.devcaz.com";
    }
}
