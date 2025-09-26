package com.pruebatecnica.orangehrm.pages;

import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.support.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;


@DefaultUrl("https://opensource-demo.orangehrmlive.com/")
public class LoginPage extends PageObject {

    @FindBy(name = "username")
    private WebElementFacade usernameInput;

    @FindBy(name = "password")
    private WebElementFacade passwordInput;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElementFacade loginButton;

    @FindBy(xpath = "//div[@class='oxd-alert-content oxd-alert-content--error']")
    private WebElementFacade loginErrorMessage;

    @Step("Ingresar nombre de usuario: {0}")
    public void enterUsername(String username) {
        usernameInput.type(username);
    }

    @Step("Ingresar contraseña: {0}")
    public void enterPassword(String password) {
        passwordInput.type(password);
    }

    @Step("Hacer clic en el botón de login")
    public void clickLoginButton() {
        loginButton.click();
    }


    @Step("Abrir página de login")
    public void openLoginPage() {
        open(); // Usa la URL de @DefaultUrl
    }



    @Step("Limpiar campo de usuario")
    public void clearUsernameField() {
        usernameInput.clear();
    }

    @Step("Limpiar campo de contraseña")
    public void clearPasswordField() {
        passwordInput.clear();
    }

    // ==============================
    // Métodos de verificación
    // ==============================

    public boolean isLoginErrorMessageVisible() {
        return loginErrorMessage.isVisible();
    }

    public boolean isUsernameFieldEmpty() {
        return usernameInput.getValue().isEmpty();
    }

    public boolean isPasswordFieldEmpty() {
        return passwordInput.getValue().isEmpty();
    }

    public boolean isDashboardVisible() {
        try {
            String currentUrl = getDriver().getCurrentUrl();
            return currentUrl != null && currentUrl.contains("dashboard");
        } catch (Exception e) {
            return false;
        }
    }
}
