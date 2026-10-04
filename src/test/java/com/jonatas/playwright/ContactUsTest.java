package com.jonatas.playwright;

import com.jonatas.playwright.base.BaseTest;
import com.jonatas.playwright.config.TestConfig;
import com.jonatas.playwright.pages.ContactUsPage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContactUsTest extends BaseTest {


    @Test
    void shouldSubmitContactFormSuccessfully() {

        ContactUsPage contactUsPage = openContactUsPage();

        contactUsPage.fillContactForm(
                "João",
                "Da Silva Mello",
                "Oruansmello@gmail.com",
                "Não quero essa joça não, mas fazer o que?"
        );
        contactUsPage.clickSubmit();

        assertEquals("Thank You for your Message!", contactUsPage.getSuccessMessage());
    }

    @Test
    void shouldNotSubmitContactFormWithInvalidEmail() {

        ContactUsPage contactUsPage = openContactUsPage();

        contactUsPage.fillContactForm(
                "João",
                "Da Silva Mello",
                "email_invalido",
                "Não quero essa joça não, mas fazer o que?"
        );
        contactUsPage.clickSubmit();

        assertTrue(contactUsPage.hasInvalidEmailMessage());
    }

    @ParameterizedTest(name = "{0}")
    @CsvSource({
            "Send form without fill first name, firstNameEmpty",
            "Send form without fill last name, lastNameEmpty",
            "Send form without fill email, emailEmpty",
            "Send form without fill message, messageEmpty"
    })
    void shouldNotSubmitContactFormWithEmptyField(String scenario,
                                                  String field) {

        ContactUsPage contactUsPage = openContactUsPage();

        contactUsPage.fillContactFormWithEmptyField(field);

        contactUsPage.clickSubmit();

        assertTrue(contactUsPage.hasRequiredFieldValidation());

    }

    private ContactUsPage openContactUsPage() {
        page.navigate(TestConfig.BASE_URL);

        ContactUsPage contactUsPage = new ContactUsPage(page);

        return contactUsPage.openContactUs();
    }
}
