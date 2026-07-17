package ru.iteco.fmhandroid.ui;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.data.Data;
import ru.iteco.fmhandroid.ui.pageObject.AuthorizationPage;
import ru.iteco.fmhandroid.EspressoIdlingResources;


@RunWith(AllureAndroidJUnit4.class)
@Epic("Страница авторизации")
public class AuthorizationTest {
    @Rule
    public ActivityScenarioRule<AppActivity> activityRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthorizationPage authPage = new AuthorizationPage();

    @Before
    public void setUpIntents() {
        try {
            EspressoIdlingResources.increment();
        } catch (Exception e) {
            System.err.println("Ошибка инициализации IdlingResource: " + e.getMessage());
        }
    }

    @After
    public void tearDown() {
        try {
            EspressoIdlingResources.decrement();
        } catch (Exception e) {
           System.err.println("Ошибка при освобождении IdlingResource: " + e.getMessage());
        }
    }


    @Test
    @DisplayName("Пустое поле логина")
    public void Empty_loginActivityTest() {
        authPage.enterLogin(Data.EMPTY_FIELD);
        authPage.assertLoginFieldVisible();
        authPage.enterPassword(Data.VALID_PASSWORD);
        authPage.assertPasswordFieldVisible();
        authPage.clickSignIn();
        authPage.verifyAuthorizationHeaderVisible();
    }

    @Test
    @DisplayName("Пустое поле пароля")
    public void Empty_passwordActivityTest() {
        authPage.enterLogin(Data.VALID_LOGIN);
        authPage.assertLoginFieldVisible();
        authPage.enterPassword(Data.EMPTY_FIELD);
        authPage.assertPasswordFieldVisible();
        authPage.clickSignIn();
        authPage.verifyAuthorizationHeaderVisible();
    }

    @Test
    @DisplayName("Пустые поля логина и пароля")
    public void empty_authorizationActivityTest() {
        authPage.enterLogin(Data.EMPTY_FIELD);
        authPage.assertLoginFieldVisible();
        authPage.enterPassword(Data.EMPTY_FIELD);
        authPage.assertPasswordFieldVisible();
        authPage.clickSignIn();
        authPage.verifyAuthorizationHeaderVisible();
    }

    @Test
    @DisplayName("Невалидный логин")
    public void Not_valid_loginActivityTest() {
        authPage.enterLogin(Data.INVALID_LOGIN);
        authPage.assertLoginFieldVisible();
        authPage.enterPassword(Data.VALID_PASSWORD);
        authPage.assertPasswordFieldVisible();
        authPage.clickSignIn();
        authPage.verifyAuthorizationHeaderVisible();
    }

    @Test
    @DisplayName("Невалидный пароль")
    public void Not_valid_passwordActivityTest() {
        authPage.enterLogin(Data.VALID_LOGIN );
        authPage.assertLoginFieldVisible();
        authPage.enterPassword(Data.INVALID_PASSWORD);
        authPage.assertPasswordFieldVisible();
        authPage.clickSignIn();
        authPage.verifyAuthorizationHeaderVisible();
    }

    @Test
    @DisplayName("Невалидный логин и пароль")
    public void Not_validActivityTest() {
        authPage.enterLogin(Data.INVALID_LOGIN);
        authPage.assertLoginFieldVisible();
        authPage.enterPassword(Data.INVALID_PASSWORD);
        authPage.assertPasswordFieldVisible();
        authPage.clickSignIn();
        authPage.verifyAuthorizationHeaderVisible();
    }

    @Test
    @DisplayName("Успешная авторизация")
    public void successfulAuthorization() {
        authPage.enterLogin(Data.VALID_LOGIN);
        authPage.assertLoginFieldVisible();
        authPage.enterPassword(Data.VALID_PASSWORD);
        authPage.assertPasswordFieldVisible();
        authPage.clickSignIn();
        authPage.waitForNewsElementWithTimeout();
        authPage.verifyNewsHeaderVisible();
    }
}








