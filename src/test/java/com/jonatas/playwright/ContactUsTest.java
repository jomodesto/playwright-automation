package com.jonatas.playwright;

import com.jonatas.playwright.base.BaseTest;
import com.jonatas.playwright.config.TestConfig;
import com.jonatas.playwright.pages.ContactUsPage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContactUsTest extends BaseTest {


    @Test
    void shouldSubmitContactFormSuccessfully() {

        page.navigate(TestConfig.BASE_URL);

        ContactUsPage contactUsPage = new ContactUsPage(page);

        contactUsPage = contactUsPage.openContactUs();

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
    void shouldNotSubmitContactFormWithInvalidEmail(){
        page.navigate(TestConfig.BASE_URL);

        ContactUsPage contactUsPage = new ContactUsPage(page);

        contactUsPage = contactUsPage.openContactUs();

        contactUsPage.fillContactForm(
                "João",
                "Da Silva Mello",
                "email_invalido",
                "Não quero essa joça não, mas fazer o que?"
        );
        contactUsPage.clickSubmit();

        assertTrue(contactUsPage.hasInvalidEmailMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "firstNameEmpty",
            "lastNameEmpty",
            "emailEmpty",
            "messageEmpty"
    })
    void shouldNotSubmitContactFormWithEmptyField(String field){
        page.navigate(TestConfig.BASE_URL);

        ContactUsPage contactUsPage = new ContactUsPage(page);

        contactUsPage = contactUsPage.openContactUs();

        contactUsPage.fillContactFormWithEmptyField(field);

        contactUsPage.clickSubmit();

        assertTrue(contactUsPage.hasRequiredFieldValidation());


    }
}
