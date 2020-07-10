package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.UserLoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferServiceTest {
    private String amount = "2000";
    private String amountZero = "0";
    private String amountOne = "1";
    private String amountOverLimit = "35000";


    @Nested
    public class ValidLogin {
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
            DashboardPage dashboardPage = new DashboardPage();
            int expected1 = dashboardPage.getBalanceCard1() + Integer.parseInt(amount);
            int expected2 = dashboardPage.getBalanceCard2() - Integer.parseInt(amount);
            dashboardPage.refillCard1();
            dashboardPage.paymentVisible();
            dashboardPage.setAmount(amount);
            dashboardPage.setFromCard(DataHelper.getCard2());
            dashboardPage.getTransfer();
            assertEquals(expected1, dashboardPage.getBalanceCard1());
            assertEquals(expected2, dashboardPage.getBalanceCard2());
        }

        @Test
        void shouldErrorWhenTransferFromInvalidCard() {
            DashboardPage dashboardPage = new DashboardPage();
            dashboardPage.refillCard1();
            dashboardPage.paymentVisible();
            dashboardPage.setAmount(amount);
            dashboardPage.setFromCard(DataHelper.getWrongCard());
            dashboardPage.getTransfer();
            //ошибка о неверном номере карты
            dashboardPage.invalidTransfer();
        }

        @Test
        void shouldErrorTransferFromCard1toCard1() {
            DashboardPage dashboardPage = new DashboardPage();
            dashboardPage.refillCard1();
            dashboardPage.paymentVisible();
            dashboardPage.setAmount(amount);
            dashboardPage.setFromCard(DataHelper.getCard1());
            dashboardPage.getTransfer();
            //ошибка о невозможности перевести деньги с карты на карту с одним и тем же номером.
            dashboardPage.invalidTransfer();
        }

        @Test
        void shouldTransferWhenCanceled() {
            DashboardPage dashboardPage = new DashboardPage();
            int expected1 = dashboardPage.getBalanceCard1();
            int expected2 = dashboardPage.getBalanceCard2();
            dashboardPage.refillCard1();
            dashboardPage.paymentVisible();
            dashboardPage.setAmount(amount);
            dashboardPage.setFromCard(DataHelper.getCard2());
            dashboardPage.getCancelTransfer();
            assertEquals(expected1, dashboardPage.getBalanceCard1());
            assertEquals(expected2, dashboardPage.getBalanceCard2());
        }

        @Test
        void shouldErrorTransferWhenAmountZero() {
            DashboardPage dashboardPage = new DashboardPage();
            int expected1 = dashboardPage.getBalanceCard1();
            int expected2 = dashboardPage.getBalanceCard2();
            dashboardPage.refillCard1();
            dashboardPage.paymentVisible();
            dashboardPage.setAmount(amountZero);
            dashboardPage.setFromCard(DataHelper.getCard2());
            dashboardPage.getTransfer();
            assertEquals(expected1, dashboardPage.getBalanceCard1());
            assertEquals(expected2, dashboardPage.getBalanceCard2());
            //ошибка с требованием ввести сумму перевода отличную от нуля
            dashboardPage.invalidTransfer();
        }

        @Test
        void shouldTransferWhenAmount1() {
            DashboardPage dashboardPage = new DashboardPage();
            int expected1 = dashboardPage.getBalanceCard1() + Integer.parseInt(amountOne);
            int expected2 = dashboardPage.getBalanceCard2() - Integer.parseInt(amountOne);
            dashboardPage.refillCard1();
            dashboardPage.paymentVisible();
            dashboardPage.setAmount(amountOne);
            dashboardPage.setFromCard(DataHelper.getCard2());
            dashboardPage.getTransfer();
            assertEquals(expected1, dashboardPage.getBalanceCard1());
            assertEquals(expected2, dashboardPage.getBalanceCard2());
        }

        @Test
        void shouldTransferAllTheMoney() {
            DashboardPage dashboardPage = new DashboardPage();
            int expected1 = dashboardPage.getBalanceCard1() + dashboardPage.getBalanceCard2();
            int expected2 = 0;
            String mount = dashboardPage.getBalanceCard2().toString();
            dashboardPage.refillCard1();
            dashboardPage.paymentVisible();
            dashboardPage.setAmount(mount);
            dashboardPage.setFromCard(DataHelper.getCard2());
            dashboardPage.getTransfer();
            assertEquals(expected1, dashboardPage.getBalanceCard1());
            assertEquals(expected2, dashboardPage.getBalanceCard2());
        }

        @Test
        void shouldErrorTransferWhenNotEnoughBalance() {
            DashboardPage dashboardPage = new DashboardPage();
            dashboardPage.refillCard2();
            dashboardPage.paymentVisible();
            dashboardPage.setAmount(amountOverLimit);
            dashboardPage.setFromCard(DataHelper.getCard1());
            dashboardPage.getTransfer();
            //ошибка о недостаточной сумме на карте
            dashboardPage.invalidTransfer();
        }
    }

    @Nested
    public class InvalidLogin {
        @Test
        void shouldInvalidLogin() {
            val loginPage = new UserLoginPage();
            val authInfo = DataHelper.getWrongAuthInfo();
            loginPage.invalidLogin(authInfo);
        }

        @Test
        void shouldInvalidVerify() {
            val loginPage = new UserLoginPage();
            val authInfo = DataHelper.getAuthInfo();
            val verificationPage = loginPage.validLogin(authInfo);
            val codeVerify = DataHelper.getInvalidVerificationCode();
            verificationPage.invalidVerify(codeVerify);
        }
    }

}
