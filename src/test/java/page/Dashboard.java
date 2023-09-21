package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;
import lombok.val;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class Dashboard {

    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private final SelenideElement heading = $ ("[data-test-id='dashboard']");
    private final ElementsCollection cards = $$ (".list__item div");

public Dashboard() {
    heading.shouldBe(visible);
}

public int getCardBalance (DataHelper.CardInfo cardInfo) {
    var text = cards.findBy(text(cardInfo.getCardNumber().substring(15))).getText();
    return extractBalance(text);
}

public Transfer selectCardToTransfer (DataHelper. CardInfo cardInfo) {
    cards.findBy(attribute("data-test-id", cardInfo.getId())).$("button").click();
    return new Transfer();
}


private int extractBalance (String text) {
    val start = text.indexOf(balanceStart);
    val finish = text.indexOf(balanceFinish);
    val value = text.substring(start + balanceStart.length(), finish);
    return Integer.parseInt(value);
}
}





