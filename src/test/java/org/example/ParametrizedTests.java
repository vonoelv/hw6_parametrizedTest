package org.example;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.example.domain.HeaderMenuItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;


@DisplayName("Parametrized tests")
public class ParametrizedTests {
    private static final String NOT_ENOUGH_INFO_ERROR =
            "Недостаточно информации. Введите фамилию, имя и отчество через пробел (Например: Иванов Иван Иванович)";
    private static final String WRONG_CHARACTERS_ERROR =
            "Используйте только русские буквы и дефис";

    @BeforeAll
    static void init() {
        Configuration.browserSize = "1200x1080";
        Configuration.baseUrl = "https://www.tinkoff.ru";
        Configuration.pageLoadTimeout = 70000;
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }

    @ValueSource(strings = {
            "1",
            "0000000000000000"})
    @DisplayName("Login: Error message for invalid phone number(ValueSource)")
    @ParameterizedTest(name = "Login: Error message for invalid phone number: {0}")
    void test1(String phoneNumber) {
        open("/login/");
        $("#phoneNumber").setValue(phoneNumber).pressEnter();
        $("#formError").shouldHave(text("Некорректный номер телефона"));
    }

    @CsvSource(value = {
            "Электронное ОСАГО | https://www.tinkoff.ru/insurance/osago/?internal_source=control",
            "Автострахование КАСКО | https://www.tinkoff.ru/insurance/kasko/?internal_source=control",
            "Страхование для туристов | https://www.tinkoff.ru/insurance/travel/?internal_source=control",
            "Страхование квартиры и дома | https://www.tinkoff.ru/insurance/property/?internal_source=control",
            "Страхование от несчастных случаев | https://www.tinkoff.ru/insurance/health/?internal_source=control"
    }, delimiter = '|')
    @DisplayName("Insurance: Ability to open info page for an insurance type(CsvSource)")
    @ParameterizedTest(name = "Insurance: Ability to open info page for an insurance type: \"{0}\": \"{1}\"")
    void checkInfoForInsuranceType(String insuranceType, String url) {
        open("/insurance/");
        $$("[data-test='htmlTag title']").findBy(text(insuranceType))
                .parent().parent().parent()
                .$(byText("Узнать подробнее")).click();
        webdriver().shouldHave(url(url));
    }


    static Stream<Arguments> fioDataProvider() {
        return Stream.of(
                Arguments.of("Антон", NOT_ENOUGH_INFO_ERROR),
                Arguments.of("Ivanov Ivan Ivanovich", WRONG_CHARACTERS_ERROR)
        );
    }

    @DisplayName("Open invest account: Error messages for invalid name(MethodSource)")
    @MethodSource("fioDataProvider")
    @ParameterizedTest(name = "Open invest account: Error messages for invalid name: \"{0}\":  \"{1}\"")
    void checkFioErrorMessages(String fioInput, String error) {
        open("/invest/");
        $("[data-qa-file='InputBox']").scrollTo().click();
        $("[data-qa-type='uikit/inputFio.value.input']").scrollTo().setValue(fioInput).pressEscape();
        $(byText("Мобильный телефон")).parent().click();
        $("[data-qa-type='uikit/formRow.errorBlock']").shouldHave(text(error));
    }


    @DisplayName("Corresponding URL is open by click on a header menu item(EnumSource)")
    @EnumSource(HeaderMenuItem.class)
    @ParameterizedTest(name = "Corresponding URL is open by click on a header menu item: \"{0}\"")
    void checkHeaderMenuItems(HeaderMenuItem headerMenuItem) {
        open("/");
        $("[data-qa-type='uikit/navigation.menu']").$(byText(headerMenuItem.name)).click();
        waitForMatchingUrl(headerMenuItem.url);
    }

    private static void waitForMatchingUrl(String url) {
        ExpectedCondition<Boolean> urlIsCorrect = d -> WebDriverRunner.url().matches(url);
        Wait().until(urlIsCorrect);
    }
}
