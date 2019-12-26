package netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import netology.pages.CreditPage;
import netology.pages.Page;
import netology.pages.PaymentPage;
import netology.utils.Card;
import netology.utils.DataGenerator;
import netology.utils.SqlHelper;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

public class PurchaseTest {

    @DisplayName("Сценарий № 1. Оплата по карте: успешная операция + Сценарий № 8. Проверка полей Месяц, Год (дата равна текущей)")
    @Test
    public void shouldBeSuccessCurrentMonthAndYearPayment() {
        Card approvedCard = new Card();
        approvedCard.setCardNumber(DataGenerator.approvedCardNumber());
        approvedCard.setMonth(DataGenerator.currentMonth());
        approvedCard.setYear(DataGenerator.currentYear());

        Page startPage = new Page();
        PaymentPage paymentPage = startPage.toPaymentPage();
        paymentPage.fillData(approvedCard);
        paymentPage.commit();
        paymentPage.checkOperationApproved();

        assertTrue(SqlHelper.checkApprovedPayment(), "Строка не найдена в БД");
    }

    private void assertTrue(boolean checkApprovedPayment, String строка_не_найдена_в_бд) {
    }

    @DisplayName("Сценарий № 2. Оплата по карте: неуспешная операция")
    @Test
    public void shouldBeDeclinedValidCardPayment() {
        Card declinedCard = new Card();
        declinedCard.setCardNumber(DataGenerator.declinedCardNumber());

        Page startPage = new Page();
        PaymentPage paymentPage = startPage.toPaymentPage();
        paymentPage.fillData(declinedCard);
        paymentPage.commit();
        paymentPage.checkOperationDeclined();

        assertTrue(SqlHelper.checkDeclinedPayment(), "Строка не найдена в БД");
    }

    @DisplayName("Сценарий № 3. Оплата в кредит: успешная операция + Сценарий № 8. Проверка полей Месяц, Год (дата равна текущей)")
    @Test
    public void shouldBeSuccessCurrentMonthAndYearCredit() {
        Card approvedCard = new Card();
        approvedCard.setCardNumber(DataGenerator.approvedCardNumber());
        approvedCard.setMonth(DataGenerator.currentMonth());
        approvedCard.setYear(DataGenerator.currentYear());

        Page startPage = new Page();
        CreditPage creditPage = startPage.toCreditPage();
        creditPage.fillData(approvedCard);
        creditPage.commit();
        creditPage.checkOperationApproved();

        assertTrue(SqlHelper.checkApprovedCredit(), "Строка не найдена в БД");
    }

    @DisplayName("Сценарий № 4. Оплата в кредит: неуспешная операция")
    @Test
    public void shouldBeDeclinedValidCardCredit() {
        Card declinedCard = new Card();
        declinedCard.setCardNumber(DataGenerator.declinedCardNumber());;

        Page startPage = new Page();
        CreditPage creditPage = startPage.toCreditPage();
        creditPage.fillData(declinedCard);
        creditPage.commit();
        creditPage.checkOperationDeclined();

        assertTrue(SqlHelper.checkDeclinedCredit(), "Строка не найдена");
    }

    @DisplayName("Сценарий № 5. Проверка поля Номер карты")
    @Test
    public void shouldBeErrorCardNumberInvalid() {
        Card invalidCard = new Card();
        invalidCard.setCardNumber(DataGenerator.invalidCardNumber());

        Page startPage = new Page();
        PaymentPage paymentPage = startPage.toPaymentPage();
        paymentPage.fillData(invalidCard);
        paymentPage.commit();
        paymentPage.checkCardNumberError();
    }

    @DisplayName("Сценарий № 6. Проверка поля Месяц (дата меньше текущей)")
    @Test
    public void shouldBeErrorEarlyMonth() {
        Card invalidCard = new Card();
        invalidCard.setMonth(DataGenerator.pastMonth());
        invalidCard.setYear(DataGenerator.currentYear());

        Page startPage = new Page();
        PaymentPage paymentPage = startPage.toPaymentPage();
        paymentPage.fillData(invalidCard);
        paymentPage.commit();
        paymentPage.checkWrongMonthError();
    }

    @DisplayName("Сценарий № 7. Проверка поля Год (дата меньше текущей)")
    @Test
    public void shouldBeErrorEarlyYear() {
        Card invalidCard = new Card();
        invalidCard.setYear(DataGenerator.pastYear());

        Page startPage = new Page();
        PaymentPage paymentPage = startPage.toPaymentPage();
        paymentPage.fillData(invalidCard);
        paymentPage.commit();
        paymentPage.checkEarlyYearError();
    }

    @DisplayName("Сценарий № 9. Проверка поля cvc")
    @Test
    public void shouldBeErrorShortCvc() {
        Card invalidCard = new Card();
        invalidCard.setCvc(DataGenerator.shortCvc());

        Page startPage = new Page();
        PaymentPage paymentPage = startPage.toPaymentPage();
        paymentPage.fillData(invalidCard);
        paymentPage.commit();
        paymentPage.checkWrongCvcError();
    }

    @DisplayName("Сценарий № 10. Проверка поля Имя (не заполнено)")
    @Test
    public void shouldBeErrorEmptyName() {
        Card invalidCard = new Card();
        invalidCard.setOwner("");

        Page startPage = new Page();
        PaymentPage paymentPage = startPage.toPaymentPage();
        paymentPage.fillData(invalidCard);
        paymentPage.commit();
        paymentPage.checkEmptyNameError();
    }

    @DisplayName("Сценарий № 11. Проверка поля Номер карты (не заполнено)")
    @Test
    public void shouldBeErrorEmptyCard() {
        Card invalidCard = new Card();
        invalidCard.setCardNumber("");

        Page startPage = new Page();
        PaymentPage paymentPage = startPage.toPaymentPage();
        paymentPage.fillData(invalidCard);
        paymentPage.commit();
        paymentPage.checkEmptyCardError();
    }

    @DisplayName("Сценарий № 12. Проверка покупки с несуществующей карты - UI")
    @Test
    public void shouldBeErrorCardDoesNotExist() {
        Card invalidCard = new Card();

        Page startPage = new Page();
        PaymentPage paymentPage = startPage.toPaymentPage();
        paymentPage.fillData(invalidCard);
        paymentPage.commit();
        paymentPage.checkOperationDeclined();
    }

    @AfterEach
    public void cleanTables() throws SQLException{
        SqlHelper.cleanTables();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }
}
