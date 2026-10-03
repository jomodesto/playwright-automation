package com.jonatas.playwright.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;

public class ContactUsPage {
    private final Page page;

    public ContactUsPage(Page page) {
        this.page = page;
    }

    public void fillFirstName(String firstName) {
        page.locator("[name='first_name']").fill(firstName);

    }

    public void fillLastName(String lastName) {
        page.locator("[name='last_name']").fill(lastName);

    }

    public void fillEmail(String email) {
        page.locator("[name='email']").fill(email);

    }

    public void fillMessage(String message) {
        page.locator("[name='message']").fill(message);

    }

    public void clickSubmit() {
        page.locator("input[value='SUBMIT']").click();

    }

    public String getSuccessMessage() {
        return page.locator("#contact_reply h1").innerText();
    }

    public ContactUsPage openContactUs() {
        Page contactPage = page.waitForPopup(() -> {
            page.getByRole(
                    AriaRole.LINK,
                    new Page.GetByRoleOptions()
                            .setName("CONTACT US Contact Us Form")
            ).click();
        });

        contactPage.waitForLoadState(LoadState.DOMCONTENTLOADED);

        return new ContactUsPage(contactPage);
    }

    public boolean hasInvalidEmailMessage(){
        return page.locator("body")
                .innerText()
                .contains("Error: Invalid email address");
    }

    public boolean hasRequiredFieldValidation() {
        return page.locator("body")
                .innerText()
                .contains("Error: all fields are required");
    }

    public void fillContactForm(String firstName,
                                String lastName,
                                String email,
                                String message){

        fillFirstName(firstName);
        fillLastName(lastName);
        fillEmail(email);
        fillMessage(message);

    }
    public void fillContactFormWithEmptyField(String field) {
        String firstName = field.equals("firstNameEmpty") ? "" : "João";
        String lastName = field.equals("lastNameEmpty") ? "" : "Da Silva Mello";
        String email = field.equals("emailEmpty") ? "" : "Oruansmello@gmail.com";
        String message = field.equals("messageEmpty") ? "" : "Não quero essa joça não, mas fazer o que?";

        fillContactForm(firstName, lastName, email, message);
    }

}
