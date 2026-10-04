package com.jonatas.playwright.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;

public class LoginPortalPage {
    private final Page page;
    private String dialogMessage;

    public LoginPortalPage(Page page) {
        this.page = page;
    }

    public LoginPortalPage openLoginPortal() {
        Page loginPage = page.waitForPopup(() -> {
            page.getByRole(
                    AriaRole.LINK,
                    new Page.GetByRoleOptions()
                            .setName("Login Portal")
            ).click();
        });

        loginPage.waitForLoadState(LoadState.DOMCONTENTLOADED);

        return new LoginPortalPage(loginPage);
    }

    public void fillUsername(String username) {
        page.locator("[placeholder='Username']").fill(username);
    }

    public void fillPassword(String password) {
        page.locator("[placeholder='Password']").fill(password);

    }

    public void clickLoginButton() {
        page.locator("[id='login-button']").click();
    }

    public void listenForDialog() {
        page.onceDialog(dialog -> {
            dialogMessage = dialog.message();
            dialog.accept();
        });
    }

    public String getDialogMessage() {
        return dialogMessage;
    }


}
