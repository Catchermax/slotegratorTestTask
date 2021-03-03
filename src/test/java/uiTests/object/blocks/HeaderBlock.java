package uiTests.object.blocks;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class HeaderBlock {
    public SelenideElement getSelf = $("header");
    public SelenideElement profileButton = getSelf.find("li.nav-profile");
}
