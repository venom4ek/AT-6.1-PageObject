package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private SelenideElement head = $("[data-test-id=dashboard]");

    private SelenideElement card1 = $("[data-test-id=\"92df3f1c-a033-48e6-8390-206f6b1f56c0\"]>[data-test-id=\"action-deposit\"]");
    private SelenideElement card2 = $("[data-test-id=\"0f3f5c2a-249e-4c3d-8287-09f7a039391d\"]>[data-test-id=\"action-deposit\"]");
    private SelenideElement balanceCard1 = $("[data-test-id=\"92df3f1c-a033-48e6-8390-206f6b1f56c0\"]");
    private SelenideElement balanceCard2 = $("[data-test-id=\"0f3f5c2a-249e-4c3d-8287-09f7a039391d\"]");

    private SelenideElement paymentPage = $(byText("Пополнение карты"));
    private SelenideElement amount = $("[data-test-id=\"amount\"] input");
    private SelenideElement fromCard = $("[data-test-id=\"from\"] input");
    private SelenideElement transfer = $("[data-test-id=\"action-transfer\"]");
    private SelenideElement cancelTransfer = $("[data-test-id=\"action-cancel\"]");

    public void isDashboardPage() {
        head.shouldBe(visible);
    }

    public DashboardPage refillCard1() {
        card1.click();
        return new DashboardPage();
    }

    public DashboardPage refillCard2() {
        card2.click();
        return new DashboardPage();
    }

    public Integer getBalanceCard1() {
        String[] text = balanceCard1.getText().split(",");
        String bal = text[text.length-1].replaceAll("[^\\d-]+", "");
        Integer balance = Integer.parseInt(bal);
        return balance;
    }

    public Integer getBalanceCard2() {
        String[] text = balanceCard2.getText().split(",");
        String bal = text[text.length-1].replaceAll("[^\\d-]+", "");
        Integer balance = Integer.parseInt(bal);
        return balance;
    }

    public void paymentVisible() {
        paymentPage.shouldBe(visible);
    }

    public SelenideElement getAmount() {
        return amount;
    }

    public SelenideElement setAmount(String sum) {
        return getAmount().setValue(sum);
    }

    public SelenideElement getFromCard() {
        return fromCard;
    }

    public SelenideElement setFromCard(String numberCard) {
        return getFromCard().setValue(numberCard);
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
