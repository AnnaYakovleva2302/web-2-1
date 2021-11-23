package ru.netology.cardform;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class OrderCardTest {
    @BeforeEach
    void openBrowser() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitRequest() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Яковлева Анна");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$("[type=button]").click();
        $("[data-test-id=order-success]").shouldHave(exactText("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldShowEmptyNameValidation() {
        SelenideElement form = $("form");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$("[type=button]").click();
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldShowInvalidNameValidation() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Яковлева Анна 2");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$("[type=button]").click();
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldShowEmptyPhoneValidation() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Яковлева Анна");
        form.$("[data-test-id=agreement]").click();
        form.$("[type=button]").click();
        $("[data-test-id=phone] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldShowInvalidPhoneValidation() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Яковлева Анна");
        form.$("[data-test-id=phone] input").setValue("123123");
        form.$("[data-test-id=agreement]").click();
        form.$("[type=button]").click();
        $("[data-test-id=phone] .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldShowAgreementValidation() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Яковлева Анна");
        form.$("[data-test-id=phone] input").setValue("+79012345678");
        form.$("[type=button]").click();
        $("[data-test-id=agreement]").shouldHave(cssClass("input_invalid"));
    }
}