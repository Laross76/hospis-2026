package ru.iteco.fmhandroid.ui.test;

import static ru.iteco.fmhandroid.ui.data.Helper.generateRandomThreeDigitString;
import static ru.iteco.fmhandroid.ui.data.Helper.getCurrentTime;
import static ru.iteco.fmhandroid.ui.data.Helper.randomCategory;

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
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.data.Data;
import ru.iteco.fmhandroid.ui.pageObject.AuthorizationPage;
import ru.iteco.fmhandroid.ui.pageObject.EditorialPage;
import ru.iteco.fmhandroid.ui.pageObject.MainPage;
import ru.iteco.fmhandroid.ui.pageObject.NewsPage;


@RunWith(AllureAndroidJUnit4.class)
@Epic(value = "Страница «News» создание, редактирование, удаление новости")
public class EditorialTest {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthorizationPage authPage = new AuthorizationPage();
    EditorialPage editorialPage = new EditorialPage();
    NewsPage newsPage = new NewsPage();
    MainPage mainPage = new MainPage();
    String randomTitle = Data.NEWS_TITLE_TEXT + generateRandomThreeDigitString();


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
        newsPage.openNewsManagementPage();
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
    @DisplayName("Ввод поочередно каждой категории при создании новости")
    public void theFieldShouldAcceptAllNewsCategories() {
        editorialPage.openPageAddingNews();
        editorialPage.verifySelectedCategories();
    }

    @Test
    @DisplayName("Создание новости")
    public void shouldCreateNews() {
        editorialPage.openPageAddingNews();
        editorialPage.enterCategoryNews(randomCategory());
        editorialPage.enterTitleNews(randomTitle);
        editorialPage.setDate(0); // Опубликовать через кол-во дней
        editorialPage.setTime(getCurrentTime());
        editorialPage.enterNewsDescription("Текст описания");
        editorialPage.clickOnSave();
        editorialPage.scrollingThroughTheNewsFeed(randomTitle);
        editorialPage.checkSearchResultIsDisplayed(randomTitle);
    }

    @Test
    @DisplayName("Не должен создавать новость с незаполненными полями")
    public void shouldNotCreateNewsWithEmptyFields() {
        editorialPage.openPageAddingNews();
        editorialPage.clickOnSave();
        editorialPage.cancelButton_shouldBeDisplayedOnEditorialPage();
    }

    @Test
    @DisplayName("Возвращение к созданию новости с сохранением данных")
    public void shouldReturnToNewsCreation() {
        editorialPage.openPageAddingNews();
        editorialPage.enterCategoryNews(Data.BIRTHDAY_CATEGORY);
        editorialPage.pressCancel();
        editorialPage.pressCancelAlertDialog();
        editorialPage.checkSearchResultIsDisplayed(Data.BIRTHDAY_CATEGORY);
    }

    @Test
    @DisplayName("Отмена публикации новости")
    public void cancelNewsPublication() {
        editorialPage.openPageAddingNews();
        editorialPage.pressCancel();
        editorialPage.pressOkAlertDialog();
        editorialPage.checkingAddNewsButton();
    }

    @Test
    @DisplayName("Удаление созданной новости")
    public void deletingCreatedNews() {
        editorialPage.createNews(Data.MASSAGE_CATEGORY, randomTitle, 2, getCurrentTime(), Data.DESCRIPTION_TEXT);
        editorialPage.scrollingThroughTheNewsFeed(randomTitle);
        editorialPage.clickOnDeletingNews(randomTitle);
        editorialPage.pressOkAlertDialog();
        editorialPage.checkingTheResultOfDeletingNews(randomTitle);
    }

    @Test
    @DisplayName("Отмена удаления новости")
    public void shouldCancelDeletionOfNews() {
        editorialPage.createNews(randomCategory(), randomTitle, 9, getCurrentTime(), Data.DESCRIPTION_TEXT);
        editorialPage.scrollingThroughTheNewsFeed(randomTitle);
        editorialPage.clickOnDeletingNews(randomTitle);
        editorialPage.pressCancelAlertDialog();
        editorialPage.checkingAddNewsButton();
    }

    @Test
    @DisplayName("Ввод поочередно каждой категории при редактировании новости")
    public void enterEachCategoryInTurn() {
        editorialPage.createNews(randomCategory(), randomTitle, 3, getCurrentTime(), Data.DESCRIPTION_TEXT);
        editorialPage.scrollingThroughTheNewsFeed(randomTitle);
        editorialPage.openNewsEditor(randomTitle);
        editorialPage.verifySelectedCategories();
    }

    @Test
    @DisplayName("Отмена редактирования новости")
    public void cancelEditingNews() {
        editorialPage.createNews(Data.NEED_HELP_CATEGORY, randomTitle, 0, getCurrentTime(), Data.DESCRIPTION_TEXT);
        editorialPage.scrollingThroughTheNewsFeed(randomTitle);
        editorialPage.openNewsEditor(randomTitle);
        editorialPage.pressCancel();
        editorialPage.pressOkAlertDialog();
        editorialPage.checkingAddNewsButton();
    }
}


