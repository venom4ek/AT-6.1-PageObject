package ru.netology.web.data;

import static com.codeborne.selenide.Condition.visible;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.page.DashboardPage;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class Payment {
    private SelenideElement paymentPage = $(byText("Пополнение карты"));
    private SelenideElement amount = $("[data-test-id=\"amount\"] input");
    private SelenideElement fromCard = $("[data-test-id=\"from\"] input");
    private SelenideElement transfer = $("[data-test-id=\"action-transfer\"]");
    private SelenideElement cancelTransfer = $("[data-test-id=\"action-cancel\"]");

    public Payment() {
    paymentPage.shouldBe(visible);
    }

    public SelenideElement getAmount() {
        return amount;
    }

    public SelenideElement getFromCard() {
        return fromCard;
    }

    public DashboardPage getTransfer() {
        transfer.click();
        return new DashboardPage();
    }

    public void invalidTransfer() {
        transfer.click();
        $("[data-test-id='error-notification']").shouldBe(visible);
    }

    public DashboardPage getCancelTransfer() {
        cancelTransfer.click();
        return new DashboardPage();
    }
}
