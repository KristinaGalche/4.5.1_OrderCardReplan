package test;

import com.codeborne.selenide.logevents.SelenideLogger;

import java.time.Duration;

import data.DataGenerator;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$x;

public class OrderTest {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void openApp() {
        open("http://localhost:9999/");
    }

    @Test
    public void shouldPlanFirstDate() {
        val user = DataGenerator.Registration.generateUser("ru");
        $x("//input[@placeholder=\"Город\"]").val(user.getCity());
        String dateOfMeeting = DataGenerator.generateDate(3);
        $x("//input[@placeholder=\"Дата встречи\"]").val(dateOfMeeting);
        $x("//input[@name=\"name\"]").val(user.getName());
        $x("//input[@name=\"phone\"]").val(user.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(),'Запланировать')]").click();
        $x("//*[contains(text(), 'Успешно!')]").shouldBe(visible, Duration.ofSeconds(15));
        $x("//*[contains(text(), 'Встреча успешно запланирована')]").should(exactText("Встреча успешно запланирована на " + dateOfMeeting));
    }

    @Test
    public void shouldPlanSecondDate() {
        val user = DataGenerator.Registration.generateUser("ru");
        $x("//input[@placeholder=\"Город\"]").val(user.getCity());
        val firstDate = 3;
        val secondDate = 5;
        String dateOfMeeting1 = DataGenerator.generateDate(firstDate);
        String dateOfMeeting2 = DataGenerator.generateDate(secondDate);
        $x("//input[@placeholder=\"Дата встречи\"]").val(dateOfMeeting1);
        $x("//input[@name=\"name\"]").val(user.getName());
        $x("//input[@name=\"phone\"]").val(user.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(),'Запланировать')]").click();
        $x("//*[contains(text(), 'Успешно!')]").shouldBe(visible, Duration.ofSeconds(15));
        $x("//*[contains(text(), 'Встреча успешно запланирована')]").should(exactText("Встреча успешно запланирована на " + dateOfMeeting1));

        $x("//input[@type=\"tel\"]").doubleClick().sendKeys("DELETE");
        $x("//input[@placeholder=\"Дата встречи\"]").val(dateOfMeeting2);
        $x("//*[contains(text(),'Запланировать')]").click();
        $("[data-test-id=replan-notification]").should(visible, Duration.ofSeconds(15));
        $x("//*[contains(text(), 'Перепланировать')]").click();
        $x("//*[contains(text(), 'Встреча успешно запланирована')]").should(visible, Duration.ofSeconds(15)).should(exactText("Встреча успешно запланирована на " + dateOfMeeting2));
    }

}
