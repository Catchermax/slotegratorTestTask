package uiTests.stepdefs;

import com.codeborne.selenide.Selenide;
import io.cucumber.java8.En;

public class BaseSteps implements En {
    public BaseSteps() {
        Given("open page", () -> {
            Selenide.open("/");
        });
    }
}
