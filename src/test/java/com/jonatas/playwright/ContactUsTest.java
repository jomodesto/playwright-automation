package com.jonatas.playwright;

import com.jonatas.playwright.pages.ContactUsPage;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContactUsTest {
    private Playwright playwright;
    private Browser browser;
    private Page page;

    @BeforeEach
    void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );
        page = browser.newPage();
    }

    @AfterEach
    void tearDown() {
        browser.close();
        playwright.close();
    }

    @Test
    void shouldSubmitContactFormSuccessfully() {

        page.navigate("https://www.webdriveruniversity.com/");

        Page contactPage = page.waitForPopup(() -> {
            page.getByRole(
                    AriaRole.LINK,
                    new Page.GetByRoleOptions()
                            .setName("CONTACT US Contact Us Form")
            ).click();
        });

        contactPage.waitForLoadState(LoadState.DOMCONTENTLOADED);

        ContactUsPage contactUsPage = new ContactUsPage(contactPage);

        contactUsPage.fillFirstName("João");
        contactUsPage.fillLastName("Da Silva Mello");
        contactUsPage.fillEmail("Oruansmello@gmail.com");
        contactUsPage.fillMessage("Não quero essa joça não, mas fazer o que?");
        contactUsPage.clickSubmit();

        assertEquals("Thank You for your Message!", contactUsPage.getSuccessMessage());
    }

    @Test
    void shouldNotSubmitContactFormWithInvalidEmail(){
        page.navigate("https://www.webdriveruniversity.com/");

        Page contactPage = page.waitForPopup(() -> {
            page.getByRole(
                    AriaRole.LINK,
                    new Page.GetByRoleOptions()
                            .setName("CONTACT US Contact Us Form")
            ).click();
        });

        contactPage.waitForLoadState(LoadState.DOMCONTENTLOADED);

        ContactUsPage contactUsPage = new ContactUsPage(contactPage);

        contactUsPage.fillFirstName("João");
        contactUsPage.fillLastName("Da Silva Mello");
        contactUsPage.fillEmail("email_invalido");
        contactUsPage.fillMessage("Não quero essa joça não, mas fazer o que?");
        contactUsPage.clickSubmit();

        assertThat(contactPage.locator("body"))
                .containsText("Error: Invalid email address");
    }
}
