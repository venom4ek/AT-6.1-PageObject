package ru.netology.web;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.CardNumber;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.Payment;
import ru.netology.web.page.UserLoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferServiceTest {

    @BeforeEach
    public void login() {
        val loginPage = new UserLoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val codeVerify = DataHelper.getVerificationCode();
        verificationPage.validVerify(codeVerify);
    }

    @Test
    void shouldTransferAmountFromCard1ToCard2() {
        CardNumber cardNumber = new CardNumber();
        int expected1 = cardNumber.getBalanceCard1() + 2000;
        int expected2 = cardNumber.getBalanceCard2() - 2000;
        cardNumber.refillCard1();
        Payment payment = new Payment();
        payment.getAmount().setValue("2000");
        payment.getFromCard().setValue(cardNumber.getFromCard2());
        payment.getTransfer();
        assertEquals(expected1, cardNumber.getBalanceCard1());
        assertEquals(expected2, cardNumber.getBalanceCard2());
    }

    @Test
    void shouldErrorWhenTransferFromInvalidCard() {
        CardNumber cardNumber = new CardNumber();
        cardNumber.refillCard1();
        Payment payment = new Payment();
        payment.getAmount().setValue("2000");
        payment.getFromCard().setValue("5559 0000 0000 0015");
        payment.getTransfer();
        //ошибка о неверном номере карты
        payment.invalidTransfer();
    }

    @Test
    void shouldErrorTransferFromCard1toCard1() {
        CardNumber cardNumber = new CardNumber();
        cardNumber.refillCard1();
        Payment payment = new Payment();
        payment.getAmount().setValue("2000");
        payment.getFromCard().setValue(cardNumber.getFromCard1());
        payment.getTransfer();
        //ошибка о невозможности перевести деньги с карты на карту с одним и тем же номером.
        payment.invalidTransfer();
    }

    @Test
    void shouldTransferWhenCanceled() {
        CardNumber cardNumber = new CardNumber();
        int expected1 = cardNumber.getBalanceCard1();
        int expected2 = cardNumber.getBalanceCard2();
        cardNumber.refillCard1();
        Payment payment = new Payment();
        payment.getAmount().setValue("2000");
        payment.getFromCard().setValue(cardNumber.getFromCard2());
        payment.getCancelTransfer();
        assertEquals(expected1, cardNumber.getBalanceCard1());
        assertEquals(expected2, cardNumber.getBalanceCard2());
    }

    @Test
    void shouldErrorTransferWhenAmountZero() {
        CardNumber cardNumber = new CardNumber();
        int expected1 = cardNumber.getBalanceCard1();
        int expected2 = cardNumber.getBalanceCard2();
        cardNumber.refillCard1();
        Payment payment = new Payment();
        payment.getAmount().setValue("0");
        payment.getFromCard().setValue(cardNumber.getFromCard2());
        payment.getTransfer();
        assertEquals(expected1, cardNumber.getBalanceCard1());
        assertEquals(expected2, cardNumber.getBalanceCard2());
        //ошибка с требованием ввести сумму перевода отличную от нуля
        payment.invalidTransfer();
    }

    @Test
    void shouldTransferWhenAmount1() {
        CardNumber cardNumber = new CardNumber();
        int expected1 = cardNumber.getBalanceCard1() + 1;
        int expected2 = cardNumber.getBalanceCard2() - 1;
        cardNumber.refillCard1();
        Payment payment = new Payment();
        payment.getAmount().setValue("1");
        payment.getFromCard().setValue(cardNumber.getFromCard2());
        payment.getTransfer();
        assertEquals(expected1, cardNumber.getBalanceCard1());
        assertEquals(expected2, cardNumber.getBalanceCard2());
    }

    @Test
    void shouldTransferAllTheMoney() {
        CardNumber cardNumber = new CardNumber();
        int expected1 = cardNumber.getBalanceCard1() + cardNumber.getBalanceCard2();
        int expected2 = 0;
        String mount = cardNumber.getBalanceCard2().toString();
        cardNumber.refillCard1();
        Payment payment = new Payment();
        payment.getAmount().setValue(mount);
        payment.getFromCard().setValue(cardNumber.getFromCard2());
        payment.getTransfer();
        assertEquals(expected1, cardNumber.getBalanceCard1());
        assertEquals(expected2, cardNumber.getBalanceCard2());
    }

    @Test
    void shouldErrorTransferWhenNotEnoughBalance() {
        CardNumber cardNumber = new CardNumber();
        cardNumber.refillCard2();
        Payment payment = new Payment();
        payment.getAmount().setValue("35000");
        payment.getFromCard().setValue(cardNumber.getFromCard1());
        payment.getTransfer();
        //ошибка о недостаточной сумме на карте
        payment.invalidTransfer();
    }

}
