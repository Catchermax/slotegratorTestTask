package uiTests.stepdefs;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import uiTests.object.pages.PlayersPage;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.codeborne.selenide.Selectors.byText;

public class PlayersPageSteps {
    PlayersPage playersPage = new PlayersPage();

    @Then("on \"Players page\" table has {int} rows")
    public void onTableHasRows(int count) {
        playersPage.rowsInTable.shouldHaveSize(count);
    }

    @And("on \"Players page\" sort table by {string}")
    public void onSortTableBy(String sortBy){
        playersPage.headerOfTable.find(byText(sortBy)).click();
        playersPage.headerOfTable.find(byText(sortBy)).shouldHave(Condition.cssClass("asc"));
    }

    @Then("on \"Players page\" table was sorted by \"Username\"")
    public void onTableWasSortedByUsername() {
        ElementsCollection usernameFields = playersPage.bodyOfTable.findAll("tr td:nth-of-type(2)");
        List<String> originalList = usernameFields.texts();
        List<String> sortedByAlphabeticallyList = (List<String>) ((ArrayList<String>)originalList).clone();
        Collections.sort(sortedByAlphabeticallyList, Collator.getInstance());

        Assertions.assertEquals(originalList, sortedByAlphabeticallyList,
                "Table didn't sorted. \nHow it looks: " + originalList + "\nHow it should look: " + sortedByAlphabeticallyList);
    }
}
