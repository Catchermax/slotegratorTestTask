package uiTests.stepdefs;

import io.cucumber.java.en.And;
import uiTests.object.pages.LoginPage;

public class LoginPageSteps {
    LoginPage loginPage = new LoginPage();

    @And("^on \"Login page\" log in with a login \"(.*)\" and password \"(.*)\"$")
    public void typeTextInLoginField(String login, String password) {
        loginPage.loginField.sendKeys(login);
        loginPage.passwordField.sendKeys(password);
        loginPage.sighInButton.click();
    }
}
