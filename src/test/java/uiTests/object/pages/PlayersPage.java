package uiTests.object.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class PlayersPage {
    public SelenideElement table = $("table");
    public SelenideElement headerOfTable = table.find("thead");
    public SelenideElement bodyOfTable = table.find("tbody");
    public ElementsCollection rowsInTable = bodyOfTable.findAll("tr");
}
