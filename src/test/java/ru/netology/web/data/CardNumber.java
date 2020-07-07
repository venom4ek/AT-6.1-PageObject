package ru.netology.web.data;

import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.web.page.DashboardPage;

import static com.codeborne.selenide.Selenide.$;

public class CardNumber {
    private SelenideElement card1 = $("[data-test-id=\"92df3f1c-a033-48e6-8390-206f6b1f56c0\"]>[data-test-id=\"action-deposit\"]");
    private SelenideElement card2 = $("[data-test-id=\"0f3f5c2a-249e-4c3d-8287-09f7a039391d\"]>[data-test-id=\"action-deposit\"]");
    private String fromCard1 = "5559000000000001";
    private String fromCard2 = "5559000000000002";
    private SelenideElement balanceCard1 = $("[data-test-id=\"92df3f1c-a033-48e6-8390-206f6b1f56c0\"]");
    private SelenideElement balanceCard2 = $("[data-test-id=\"0f3f5c2a-249e-4c3d-8287-09f7a039391d\"]");


    public DashboardPage refillCard1() {
        card1.click();
        return new DashboardPage();
    }

    public DashboardPage refillCard2() {
        card2.click();
        return new DashboardPage();
    }

    public String getFromCard1() {
        return fromCard1;
    }

    public String getFromCard2() {
        return fromCard2;
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
}
