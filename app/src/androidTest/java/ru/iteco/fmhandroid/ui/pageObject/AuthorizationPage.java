package ru.iteco.fmhandroid.ui.pageObject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewInteraction;

import org.hamcrest.core.IsInstanceOf;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.EspressoIdlingResources;
import ru.iteco.fmhandroid.R;

public class AuthorizationPage {
    public static ViewInteraction LOGIN_INPUT = onView((allOf(withHint("Login"), withParent(withParent(withId(R.id.login_text_input_layout))))));
    public static ViewInteraction PASSWORD_INPUT = onView(allOf(withHint("Password"), withParent(withParent(withId(R.id.password_text_input_layout)))));
    public static ViewInteraction SIGN_IN_BUTTON = onView(allOf(withId(R.id.enter_button), withText("SIGN IN"), withContentDescription("Save"), withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class)))));


    public void enterLogin(String loginText) {
        Allure.step("Ввести логин '" + loginText + "'");
        LOGIN_INPUT.check(matches(isDisplayed())).perform(replaceText(loginText), closeSoftKeyboard());
    }

    public void assertLoginFieldVisible() {
        Allure.step("Проверка отображения поля логин");
        LOGIN_INPUT.check(matches(isDisplayed()));
    }

    public void enterPassword(String passwordText) {
        Allure.step("Ввести пароль '" + passwordText + "'");
        PASSWORD_INPUT.check(matches(isDisplayed())).perform(replaceText(passwordText), closeSoftKeyboard());
    }

    public void assertPasswordFieldVisible() {
        Allure.step("Проверка отображения поля пароль");
        PASSWORD_INPUT.check(matches(isDisplayed()));
    }

    public void clickSignIn() {
        Allure.step("Нажать войти");
        SIGN_IN_BUTTON.check(matches(isDisplayed())).perform(click());
    }

    public void verifyAuthorizationHeaderVisible() {
        Allure.step("Проверка видимости заголовка 'Authorization'");
        onView(allOf(
                withText("Authorization"),
                withParent(withParent(withId(R.id.nav_host_fragment))),
                isDisplayed()
        )).check(matches(isDisplayed()));
    }

    public void verifyNewsHeaderVisible() {
        Allure.step("Проверка видимости заголовка 'News'");
        onView(allOf(
                withText("News"),
                isDescendantOfA(withId(R.id.container_list_news_include_on_fragment_main)),
                isDisplayed()
        )).check(matches(isDisplayed()));
    }

    public void waitForNewsElementWithTimeout() {
        Allure.step("Ожидание появления элемента 'News' после авторизации");
        EspressoIdlingResources.increment();
        long timeoutMillis = 10_000;
        long startTime = System.currentTimeMillis();
        boolean isFound = false;

        while (System.currentTimeMillis() - startTime < timeoutMillis) {
            try {
                onView(allOf(
                        withText("News"),
                        isDescendantOfA(withId(R.id.container_list_news_include_on_fragment_main))
                )).check(matches(isDisplayed()));
                Allure.step("Элемент 'News' найден и отображается");
                isFound = true;
                break;
            } catch (NoMatchingViewException e) {

            }
        }

        EspressoIdlingResources.decrement();

        if (!isFound) {
            throw new AssertionError("Элемент 'News' не появился за " + (timeoutMillis / 1000) + " секунд");
        }
    }
}














