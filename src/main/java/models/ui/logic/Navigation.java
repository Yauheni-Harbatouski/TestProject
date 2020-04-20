package models.ui.logic;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.open;

public class Navigation {
    @Step
    public static void openHomePage() {
        open("https://reqres.in/");
    }

}

