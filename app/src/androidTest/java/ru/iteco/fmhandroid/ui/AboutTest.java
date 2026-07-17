package ru.iteco.fmhandroid.ui;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.EspressoIdlingResources;
import ru.iteco.fmhandroid.ui.data.Data;
import ru.iteco.fmhandroid.ui.pageObject.AboutPage;
import ru.iteco.fmhandroid.ui.pageObject.AuthorizationPage;
import ru.iteco.fmhandroid.ui.pageObject.MainPage;

@RunWith(AllureAndroidJUnit4.class)
@Epic(value = "Страница приложения «About»")
public class AboutTest {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthorizationPage authPage = new AuthorizationPage();
     AboutPage aboutPage = new AboutPage();
     MainPage mainPage = new MainPage();

    @Before
    public void setUp() {
        authPage.enterLogin(Data.VALID_LOGIN);
        authPage.assertLoginFieldVisible();
        authPage.enterPassword(Data.VALID_PASSWORD);
        authPage.assertPasswordFieldVisible();
        authPage.clickSignIn();
        authPage.waitForNewsElementWithTimeout();
        authPage.verifyNewsHeaderVisible();

        mainPage.clickOnHamburgerMenu();
        mainPage.clickOnAbout();
        aboutPage.assertAppLabelIsVisible();
    }

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
    @DisplayName("Отображение лейбла приложения")
    public void shouldDisplayTheAppLabel() {
        aboutPage.assertAppLabelIsVisible();
    }

    @Test
    @DisplayName("Отображение текста версии приложения")
    public void displayTheApplicationVersionText() {
        aboutPage.appVersionTextApproval();
    }

    @Test
    @DisplayName("Отображение номера версии приложения")
    public void displayTheApplicationVersionNumber() {
        aboutPage.appVersionNumberApproval();
    }

    @Test
    @DisplayName("Отображение метки политики конфиденциальности")
    public void shouldDisplayPrivacyPolicyLabel() {
        aboutPage.assertDisplayOfPrivacyPolicyLabel();
    }

    @Test
    @DisplayName("Отображение метки условия эксплуатации")
    public void shouldDisplayTermsOfUseLabel() {
        aboutPage.assertDisplayOfTermsOfUseLabel();
    }

    @Test
    @DisplayName("Должен иметь правильный URL Политика конфиденциальности")
    public void thereMustBeCorrectPrivacyPolicyUrl() {
        aboutPage.clickOnPrivacyPolicy();
        aboutPage.verifyIntent(Data.PRIVACY_POLICY_URL);
    }

    @Test
    @DisplayName("Должен иметь правильный URL Условия эксплуатации")
    public void thereMustBeCorrectTermsOfUseUrl() {
        aboutPage.clickOnTermsOfUse();
        aboutPage.verifyIntent(Data.TERMS_OF_USE_URL);
    }

    @Test
    @DisplayName("Выход из раздела About кнопкой назад в приложении")
    public void exitAboutPageByBackButton() {
        aboutPage.clickOnBack();
        mainPage.theAllNewsItemIsDisplayed();
    }
}
