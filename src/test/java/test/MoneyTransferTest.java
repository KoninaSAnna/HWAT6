package test;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.Dashboard;
import page.Login;

import static com.codeborne.selenide.Selenide.open;
import static data.DataHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {
    Dashboard dashboard;

    @BeforeEach
    void setup() {
        var loginPage = open("http://localhost:9999", Login.class);
        var authInfo = getAuthInfo();
        var verification = loginPage.validLogin(authInfo);
        var verificationCode = getVerificationCode();
        dashboard = verification.validVerify(verificationCode);
    }

    @Test
    void TransferFromFirstToSecond() {
        var firstCardInfo = getFirstCardInfo();
        var secondCardInfo = getSecondCardInfo();
        var firstCardBalance = dashboard.getCardBalance(firstCardInfo);
        var secondCardBalance = dashboard.getCardBalance(secondCardInfo);
        var amount = generateValidAmount(firstCardBalance);
        var expectedBalanceFirstCard = firstCardBalance - amount;
        var expectedBalanceSecondCard = secondCardBalance + amount;
        var transferPages = dashboard.selectCardToTransfer(secondCardInfo);
        dashboard = transferPages.makeValidTransfer(String.valueOf(amount), firstCardInfo);
        var actualBalanceFirstCard = dashboard.getCardBalance(firstCardInfo);
        var actualBalanceSecondCard = dashboard.getCardBalance(secondCardInfo);
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }

    @Test
    void getErrorMessageIfAmountMoreBalance() {
        var firstCardInfo = getFirstCardInfo();
        var secondCardInfo = getSecondCardInfo();
        var firstCardBalance = dashboard.getCardBalance(firstCardInfo);
        var secondCardBalance = dashboard.getCardBalance(secondCardInfo);
        var amount = generateInvalidAmount(secondCardBalance);
        var transferPages = dashboard.selectCardToTransfer(firstCardInfo);
        transferPages.makeTransfer(String.valueOf(amount), secondCardInfo);
        transferPages.findErrorMessage("Выполнена поопытка перевода суммы, превышающей остаток на карте списания");
        var actualBalanceFirstCard = dashboard.getCardBalance(firstCardInfo);
        var actualBalanceSecondCard = dashboard.getCardBalance(secondCardInfo);
        assertEquals(firstCardBalance, actualBalanceFirstCard);
        assertEquals(secondCardBalance, actualBalanceSecondCard);

    }
}








