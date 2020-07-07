package ru.netology.web.page;

import static com.codeborne.selenide.Condition.visible;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private SelenideElement head = $("[data-test-id=dashboard]");

    public void isDashboardPage() {
        head.shouldBe(visible);
    }
}
