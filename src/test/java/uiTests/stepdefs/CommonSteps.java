package uiTests.stepdefs;

import io.cucumber.java.en.Given;
import uiTests.helpers.Constants;

import static com.codeborne.selenide.Selenide.open;

public class CommonSteps {
    @Given("^open page \"([^\"]*)\"$")
    public void openPage(String page) {
        switch (page) {
            case "Login Page" -> open(Constants.LOGIN_PAGE_URL);
        }
    }
}
