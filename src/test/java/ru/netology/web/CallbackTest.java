package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CallbackTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {

        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestCorrect() {
        driver.findElement(By.cssSelector("[data-test-id= 'name'] input")).sendKeys("Фыв Карлов");
        driver.findElement(By.cssSelector("[data-test-id= 'phone'] input")).sendKeys("+79068884487");
        driver.findElement(By.cssSelector("[data-test-id= 'agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());

    }
    @Test
    void shouldTestOnlyRussiaText() {
        driver.findElement(By.cssSelector("[data-test-id= 'name'] input")).sendKeys("as fsdf");
        driver.findElement(By.cssSelector("[data-test-id= 'phone'] input")).sendKeys("+79068884487");
        driver.findElement(By.cssSelector("[data-test-id= 'agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());

    }
    @Test
    void shouldTestWrongNumber() {
        driver.findElement(By.cssSelector("[data-test-id= 'name'] input")).sendKeys("фыв апр");
        driver.findElement(By.cssSelector("[data-test-id= 'phone'] input")).sendKeys("+79068884487");
        driver.findElement(By.cssSelector("button.button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id= 'agreement'] .checkbox__text")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", text.trim());

    }


}

