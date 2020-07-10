package ru.netology.web.page;

import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class UserLoginPage {

private String authUrl = "http://127.0.0.1:9999";

    public UserLoginPage () {
        open(authUrl);
    }

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        $("[data-test-id=\"login\"] input").setValue(info.getLogin());
        $("[data-test-id=\"password\"] input").setValue(info.getPassword());
        $("[data-test-id=\"action-login\"]").click();
        return new VerificationPage();
    }

    public void invalidLogin(DataHelper.AuthInfo info) {
        $("[data-test-id=\"login\"] input").setValue(info.getLogin());
        $("[data-test-id=\"password\"] input").setValue(info.getPassword());
        $("[data-test-id=\"action-login\"]").click();
        $("[data-test-id=error-notification]").shouldBe(visible);
    }

}
