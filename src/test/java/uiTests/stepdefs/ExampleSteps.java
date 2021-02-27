package uiTests.stepdefs;

import com.codeborne.selenide.Selenide;
import io.cucumber.java8.En;

public class ExampleSteps implements En {
    public ExampleSteps() {
        Given("first step", () -> {
            System.out.println("Hi");;
        });

        Given("second step", () -> {
            System.out.println("Hi2");;
        });

        Given("third step", () -> {
            System.out.println("Hi3");;
        });
    }
}
