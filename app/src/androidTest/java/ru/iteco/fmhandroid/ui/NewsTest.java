package ru.iteco.fmhandroid.ui;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.EspressoIdlingResources;
import ru.iteco.fmhandroid.ui.data.Data;
import ru.iteco.fmhandroid.ui.pageObject.AuthorizationPage;
import ru.iteco.fmhandroid.ui.pageObject.EditorialPage;
import ru.iteco.fmhandroid.ui.pageObject.MainPage;
import ru.iteco.fmhandroid.ui.pageObject.NewsPage;



@RunWith(AllureAndroidJUnit4.class)
@Epic(value = "Приветствующая страница «News»")
public class NewsTest {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthorizationPage authPage = new AuthorizationPage();
    NewsPage newsPage = new NewsPage();
    MainPage mainPage = new MainPage();
    EditorialPage editorialPage = new EditorialPage();

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
        mainPage.clickOnNews();
        EspressoIdlingResources.increment();
    }

    @After
    public void tearDown() {
        try {
            EspressoIdlingResources.decrement();
            mActivityScenarioRule.getScenario().close();
        } catch (Exception e) {
            Allure.step("Ошибка при закрытии ActivityScenario: " + e.getMessage());
        }
    }


    @Test
    @DisplayName("Навигация к странице Main с приветствующей страницы 'News'")
    public void shouldGoToMainPageFromNewsPage() {
        mainPage.clickOnHamburgerMenu();
        mainPage.clickOnMain();
        mainPage.theAllNewsItemIsDisplayed();
    }

    @Test
    @DisplayName("Ввод поочередно каждой категории в фильтре новостей")
    public void theFieldShouldAcceptAllNewsCategories() {
        newsPage.openNewsFilter();
        editorialPage.verifySelectedCategories();
    }

    @Test
    @DisplayName("Проверка кликабельности кнопок сортировки и фильтра")
    public void shouldSortAndFilterButtonsBeClickable() {
        newsPage.verifySortButtonClickable();
        newsPage.verifyFilterButtonClickable();
        newsPage.clickOnSortingNews();
        newsPage.openNewsManagementPage();
    }

    @Test
    @DisplayName("Отмена применения фильтра новостей")
    public void shouldCancelNewsFilterApplication() {
        newsPage.openNewsFilter();
        editorialPage.pressCancel();
        newsPage.verifyTextAfterCancelingFilter(Data.NEWS_TEXT);
    }

    @Test
    @DisplayName("Проверка навигации на страницу управления новостями")
    public void shouldNavigateToNewsManagementPage() {
        newsPage.showNewsManagementButton();
        newsPage.openNewsManagementPage();
    }
}














