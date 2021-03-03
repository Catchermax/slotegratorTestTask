package uiTests.object.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    public SelenideElement loginField = $("#UserLogin_username");
    public SelenideElement passwordField = $("#UserLogin_password");
    public SelenideElement sighInButton = $("input[value='Sign in']");
}
