package page;

import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class Transfer {
    private final SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private final SelenideElement amountInput = $("[data-test-id='amount'] input");
    private final SelenideElement fromInput = $("[data-test-id='from'] input");
    private final SelenideElement transferHead= $("Пополнение карты");
    private final SelenideElement errorMessage = $("[data-test-id='error-message']");


        public Transfer() {transferHead.shouldBe(visible); }

    public Dashboard makeValidTransfer (String amountToTransfer, DataHelper.CardInfo cardInfo) {
        makeValidTransfer(amountToTransfer,cardInfo);
        return new Dashboard();
    }

    public void makeTransfer (String amountToTransfer, DataHelper.CardInfo cardInfo) {
        amountInput.setValue(amountToTransfer);
        fromInput.setValue(cardInfo.getCardNumber());
        transferButton.click();
    }

    public void findErrorMessage(String expectedText) {
        errorMessage.shouldHave(exactText(expectedText), Duration.ofSeconds(15)).shouldBe(visible);

    }

   }
