package com.jonatas.playwright;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

public class PlaywrightExample {
    public static void main(String[] args) {

        try (Playwright playwright = Playwright.create()) {

            Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions()
                            .setHeadless(false)
            );

            Page page = browser.newPage();

            page.navigate("https://www.webdriveruniversity.com/");

            Page contactPage = page.waitForPopup(() -> {
                page.getByRole(
                        AriaRole.LINK,
                        new Page.GetByRoleOptions().setName("CONTACT US Contact Us Form")
                ).click();       });

            contactPage.getByPlaceholder("First Name").fill("João");
            contactPage.getByPlaceholder("Last Name").fill("Da Silva Mello");
            contactPage.getByPlaceholder("Email Address").fill("Oruansmello@gmail.com");
            contactPage.getByPlaceholder("Comments").fill("Não quero essa joça não, mas fazer o que?");
           // contactPage.get

            System.out.println(
                    "Valor preenchido: " +
                            contactPage.getByPlaceholder("First Name").inputValue()
            );
            System.out.println("URL: " + contactPage.url());

            browser.close();
        }
    }
}
