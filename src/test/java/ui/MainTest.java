package ui;

import core.configs.basetest.BaseTest;
import core.listeners.SelenideListener;
import io.qameta.allure.Epic;
import models.api.utils.ApiOperations;
import models.ui.site.homepage.HomePage;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.text;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.testng.AssertJUnit.assertTrue;

/**
 * https://reqres.in/
 * <p>
 * Написать по 2 теста (API и UI) на каждый end-point, указанный на ресурсе https://reqres.in/  причём:
 * a.	Если API тест добавляет или удаляет что-то, то UI должен проверить, что удаляемого объекта нет или наоборот объект появился
 * b.	Если API тест проверяет что-то в ответе, то UI тест должен выполнить точно такую же проверку (пример такого API запроса:
 * https://reqres.in/api/users?page=2 )
 * Использовать: Java, TestNG, Selenide, RestAssured, Allure, Maven.
 */

@Epic("Site")
@Listeners(SelenideListener.class)
public class MainTest extends BaseTest {

    private final ApiOperations apiOperations = new ApiOperations();
    private final HomePage homePage = new HomePage();

    @Test(description = "тест статуса каждого endpoint-а (UI)", dataProvider = "endpoint name and response", priority = 0)
    public void endpointsStatusCodeTest(String endpointName, String response) {
        //нажать по каждому endpoint
        homePage.endpointsBlock.getEndpoints().findBy(text(endpointName)).click();
        //response соответствует указанному:
//        "LIST USERS", "200"
//        "SINGLE USER", "200"
//        "SINGLE USER NOT FOUND", "404"
//        "LIST <RESOURCE>", "200"
//        "SINGLE <RESOURCE>", "200"
//        "SINGLE <RESOURCE> NOT FOUND", "404"
//        "CREATE", "201"
//        "UPDATE", "200"
//        "DELETE", "204"
//        "REGISTER - SUCCESSFUL", "200"
//        "REGISTER - UNSUCCESSFUL", "400"
//        "LOGIN - SUCCESSFUL", "200"
//        "LOGIN - UNSUCCESSFUL", "400"
//        "DELAYED RESPONSE", "200"
        homePage.endpointsBlock.getResponseStatus().shouldBe(text(response));
    }


    @Test(priority = 1)
    public void listUsersPageUITest() {
        //нажать на LIST USERS "//api/users?page=2"
        homePage.endpointsBlock.getEndpoints().findBy(text("LIST USERS")).click();
        // отображается json второй страницы  пользователей в блоке response
        assertTrue(homePage.endpointsBlock.getResponseBlock().text().contains("\"page\": 2"));
    }


    @Test(priority = 1)
    public void listUsersStatusCodeAndActivePageApiTest() {
        // при get запросе (/api/users?page=2) загружается json второй страницы  пользователей   и статус код 200
        apiOperations.checkJsonObject("/api/users?page=2", 200, "page", "2");
    }

    @Test(priority = 2)
    public void singleUserUITest() {
        //нажать на SINGLE USER "//api/users/2"
        homePage.endpointsBlock.getEndpoints().findBy(text("SINGLE USER")).click();
        // отображается пользователь с "id" = 2,
        homePage.endpointsBlock.getResponseBlock().shouldBe(text("\"id\": 2"));
    }

    @Test(priority = 3)
    public void singleUserApiTest() {
        // при get запросе "//api/users/2" загружается json  пользователя с "id" = 2   и статус код 200
        apiOperations.checkJsonObject("/api/users/2", 200, "data.id", "2");

    }

    @Test(priority = 4)
    public void singleUserNotFoundUITest() {
        //нажать на SINGLE USER NOT FOUND "/api/users/23"
        homePage.endpointsBlock.getEndpoints().findBy(text("SINGLE USER NOT FOUND")).click();
        // отображается пустой json
        homePage.endpointsBlock.getResponseBlock().shouldBe(text("{}"));
    }

    @Test(priority = 5)
    public void singleUserNotFoundApiTest() {
        // при get запросе "/api/users/23" приходит статус код 404 и пустой json
        apiOperations.checkJsonObject("/api/users/23", 404, "", "{}");
    }

    @Test(priority = 6)
    public void listResourceUITest() {
        //нажать на LIST <RESOURCE> "/api/unknown"
        homePage.endpointsBlock.getEndpoints().findBy(text("LIST <RESOURCE>")).click();
        // установлено свойство "per_page": 6
        homePage.endpointsBlock.getResponseBlock().shouldBe(text("\"per_page\": 6"));
    }

    @Test(priority = 7)
    public void listResourceApiTest() {
        // при get запросе "/api/unknown" приходит статус код 200 и свойство "per_page": 6
        apiOperations.checkJsonObject("/api/unknown", 200, "per_page", "6");
    }


    @Test(priority = 8)
    public void singleResourceUITest() {
        //нажать на SINGLE <RESOURCE> "/api/unknown/2"
        homePage.endpointsBlock.getEndpoints().findBy(text("SINGLE <RESOURCE>")).click();
        // установлено свойство "company": "StatusCode Weekly"
        homePage.endpointsBlock.getResponseBlock().shouldBe(text("\"company\": \"StatusCode Weekly\""));
    }

    @Test(priority = 9)
    public void singleResourceApiTest() {
        // при get запросе "/api/unknown/2" приходит статус код 200 и свойство "company": "StatusCode Weekly"
        apiOperations.checkJsonObject("/api/unknown/2", 200, "ad.company", "StatusCode Weekly");
    }


    @Test(priority = 10)
    public void singleResourceNotFoundUITest() {
        //нажать на SINGLE <RESOURCE> NOT FOUND "//api/unknown/23"
        homePage.endpointsBlock.getEndpoints().findBy(text("SINGLE <RESOURCE> NOT FOUND")).click();
        // отображается пустой json
        homePage.endpointsBlock.getResponseBlock().shouldBe(text("{}"));
    }

    @Test(priority = 11)
    public void singleResourceNotFoundApiTest() {
        // при get запросе "//api/unknown/23" приходит статус код 404 и пустой json
        apiOperations.checkJsonObject("/api/unknown/23", 404, "", "{}");
    }


    @Test(priority = 12)
    public void createUserTest() {

        // при post запросе "/api/users" c телом
        //{
        //    "name": "randomName",
        //    "job": "randomUser"
        //}
        // приходит статус код 201 и id созданного user-a

        String idCreated = apiOperations.createUser("TestUser" + randomNumeric(3), "RandomJob" + randomNumeric(3));

        //нажать  на LIST USERS через UI
        homePage.endpointsBlock.getEndpoints().findBy(text("LIST USERS")).click();

        // отображается пользователь с "id" созданным id через api
        Assert.assertTrue(homePage.endpointsBlock.getResponseBlock().text().contains("\"id\": " + idCreated), "User с id:" + idCreated + " не был создан");
    }


    @Test(priority = 13)
    public void updateUserTest() {

        // при put запросе "/api/users/2" c телом
        //{
        //    "name": "TestUser",
        //    "job": "RandomJob"
        //}
        // приходит статус 200, сообщающий об успешной операции

        apiOperations.updateUser("2", "TestUser", "RandomJob");

        //нажать на SINGLE USER через UI
        homePage.endpointsBlock.getEndpoints().findBy(text("SINGLE USER")).click();

        // отображается пользователь <2>  с измененными данными через PUT
        Assert.assertTrue(homePage.endpointsBlock.getResponseBlock().text().contains("TestUser"), "метод PUT не изменил пользователя с id: 2");
    }


    @Test(priority = 14)
    public void deleteUserTest() {

        // при delete запросе "/api/users/2"

        // приходит статус 204, сообщающий об успешной операции

        apiOperations.deleteUser("2");

        //нажать а SINGLE USER  через UI
        homePage.endpointsBlock.getEndpoints().findBy(text("SINGLE USER")).click();

        // не отображается user с id=2
        Assert.assertFalse(homePage.endpointsBlock.getResponseBlock().text().contains("\"id\": 2"), "метод DELETE не удалил user с id: 2");
    }


    @Test(priority = 15)
    public void registerUserTest() {

        // при post запросе "/api/register" c телом
        //{
        //    "email": "eve.holt@reqres.in",
        //    "password": "pistol"
        //}
        // приходит статус код 200 и id созданного user-a

        String idCreated = apiOperations.registerUser("eve.holt@reqres.in", "pistol");

        //нажать на SINGLE USER через UI
        homePage.endpointsBlock.getEndpoints().findBy(text("LIST USERS")).click();

        // отображается пользователь с "id" созданным id через api
        Assert.assertTrue(homePage.endpointsBlock.getResponseBlock().text().contains("\"id\": " + idCreated), "User с id:" + idCreated + " не найден");
    }


    @Test(priority = 16)
    public void registerUnsuccessfulTest() {

        // при post запросе "/api/register" c телом
        // {
        //    "email": "randomEmail
        // }
        // приходит статус код 400 и ошибка создания пользователя

        String errorMessage = apiOperations.registerWithoutPassword(randomAlphabetic(5) + "@gmail.com");
        Assert.assertEquals(errorMessage, "Missing password");
    }

    @Test(priority = 17)
    public void loginTest() {

        // при post запросе "/api/login" c телом
        // {
        //    "email": "eve.holt@reqres.in",
        //    "password": "cityslicka"
        //}
        // приходит статус код 200

        apiOperations.loginUser("eve.holt@reqres.in", "cityslicka");

        //нажать на LOGIN - SUCCESSFUL "/api/login" через UI
        homePage.endpointsBlock.getEndpoints().findBy(text("LOGIN - SUCCESSFUL")).click();
        // отображается json c токеном
        homePage.endpointsBlock.getResponseBlock().shouldBe(text("token"));

    }


    @Test(priority = 18)
    public void loginUnsuccessfulTest() {

        // при post запросе "/api/login" c телом
        // {
        //    "email": "peter@klaven
        // }
        // приходит статус код 400 и ошибка логина

        String errorMessage = apiOperations.loginWithoutPassword("peter@klaven");
        Assert.assertEquals(errorMessage, "Missing password");

        //нажать на LOGIN - UNSUCCESSFUL "/api/login" через UI
        homePage.endpointsBlock.getEndpoints().findBy(text("LOGIN - UNSUCCESSFUL")).click();

        // отображается json c ошибкой

        homePage.endpointsBlock.getResponseBlock().shouldBe(text(" \"error\": \"Missing password\""));

    }


    @Test(priority = 19)
    public void delayedResponseTest() {

        //  get запрос "users?delay=3"

        // приходит статус код 200 и в респонс  json с текстом "total":12
        apiOperations.delayedResponse("3", "total", "12");


        //нажать на DELAYED RESPONSE "/api/delay=3" через UI

        homePage.endpointsBlock.getEndpoints().findBy(text("DELAYED RESPONSE")).click();
        long start = System.currentTimeMillis();

        // через 3 секунды отображается json с текстом "total":12
        homePage.endpointsBlock.getResponseBlock().shouldBe(text("\"total\": 12,"));
        long finish = System.currentTimeMillis();
        long timeConsumedMillis = finish - start;
        assertTrue(timeConsumedMillis > 3000 & timeConsumedMillis < 4000);
    }


}



