package uiTests.stepdefs;

import com.codeborne.selenide.Condition;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import uiTests.object.blocks.LeftPanel;
import uiTests.object.pages.DashboardPage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;

public class DashboardPageSteps {
    DashboardPage dashboardPage = new DashboardPage();
    LeftPanel leftPanel = new LeftPanel();

    @Then("^on \"Dashboard page\" page was loaded$")
    public void pageWasLoaded() {
        dashboardPage.headerBlock.profileButton.shouldBe(visible);
        leftPanel.getSelf.shouldBe(visible);
    }

    @And("^on \"Dashboard page\" in the \"Left Panel\" open \"(.*)\"$")
    public void clickButtonInLeftPanel(String textButton) {
        leftPanel.getSelf.shouldBe(visible);
        leftPanel.getSelf.find(byText(textButton)).shouldBe(visible).click();
    }
}
