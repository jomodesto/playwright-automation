package com.jonatas.playwright;

import com.jonatas.playwright.base.BaseTest;
import com.jonatas.playwright.config.TestConfig;
import com.jonatas.playwright.pages.LoginPortalPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginPortalTest extends BaseTest {

    @ParameterizedTest (name = "{0}")
    @CsvSource({
            "Invalid credentials, invalidUsername, invalidPassword",
            "Empty username, '', invalidPassword",
            "Empty password, invalidUsername, ''",
            "Empty username and password, '', ''"
    })
    void shouldRejectLoginWithInvalidOrEmptyCredentials(String scenario,
                                                          String username,
                                                          String password) {
        page.navigate(TestConfig.BASE_URL);

        LoginPortalPage loginPortalPage = new LoginPortalPage(page);

        loginPortalPage = loginPortalPage.openLoginPortal();

        loginPortalPage.fillUsername(username);
        loginPortalPage.fillPassword(password);

        loginPortalPage.listenForDialog();

        loginPortalPage.clickLoginButton();

        assertEquals("validation failed", loginPortalPage.getDialogMessage());

    }

}
