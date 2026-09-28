package com.jonatas.playwright.pages;

import com.microsoft.playwright.Page;

public class ContactUsPage {
    private final Page page;

    public ContactUsPage(Page page) {
        this.page = page;
    }

    public void fillFirstName(String firstName){
        page.locator("[name='first_name']").fill(firstName);

    }

    public void fillLastName(String lastName){
        page.locator("[name='last_name']").fill(lastName);

    }

    public void fillEmail(String email){
        page.locator("[name='email']").fill(email);

    }

    public void fillMessage(String message){
        page.locator("[name='message']").fill(message);

    }

    public void clickSubmit(){
        page.locator("input[value='SUBMIT']").click();

    }

    public String getSuccessMessage() {
        return page.locator("#contact_reply h1").innerText();
    }

}
