package com.pruebatecnica.orangehrm.pages;


import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.support.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;




import java.time.Duration;

/**
 * Page Object refactorizado para la página de Login de OrangeHRM.
 * Utiliza WebElementFacade para manejar automáticamente las esperas.
 */
@DefaultUrl("https://opensource-demo.orangehrmlive.com/")
public class LoginPage extends PageObject {

    // ==============================
    // Elementos de la página
    // ==============================
    @FindBy(name = "username")
    private WebElementFacade usernameInput;

    @FindBy(name = "password")
    private WebElementFacade passwordInput;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElementFacade loginButton;

    @FindBy(xpath = "//div[@class='oxd-alert-content oxd-alert-content--error']")
    private WebElementFacade loginErrorMessage;

    @FindBy(xpath = "//form[@class='oxd-form']")
    private WebElementFacade loginForm;

    @FindBy(xpath = "//p[@class='oxd-text oxd-text--p orangehrm-login-forgot-header']")
    private WebElementFacade forgotPasswordLink;

    // ==============================
    // Métodos de interacción
    // ==============================

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

    @Step("Realizar login con usuario: {0}")
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton(); // Dejamos aquí el click para el flujo completo
    }

    @Step("Hacer clic en el enlace de Forgot Password")
    public void clickForgotPasswordLink() {
        forgotPasswordLink.click();
    }

    @Step("Abrir página de login")
    public void openLoginPage() {
        open(); // Usa la URL de @DefaultUrl
        waitFor(loginForm).withTimeoutOf(Duration.ofSeconds(10));
    }


    public void waitForPageToLoad() {
        waitForCondition().until(webDriver ->
                ((org.openqa.selenium.JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete"));
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

    public String getLoginErrorMessage() {
        return loginErrorMessage.isVisible() ? loginErrorMessage.getText() : "";
    }

    public boolean isLoginFormVisible() {
        return loginForm.isVisible();
    }

    public boolean isForgotPasswordLinkVisible() {
        return forgotPasswordLink.isVisible();
    }

    public boolean isUsernameFieldEmpty() {
        return usernameInput.getValue().isEmpty();
    }

    public boolean isPasswordFieldEmpty() {
        return passwordInput.getValue().isEmpty();
    }

    public boolean isPageLoaded() {
        return loginForm.isVisible() && usernameInput.isVisible() &&
                passwordInput.isVisible() && loginButton.isVisible();
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
