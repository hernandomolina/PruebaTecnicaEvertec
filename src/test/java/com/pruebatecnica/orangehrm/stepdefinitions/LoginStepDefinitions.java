package com.pruebatecnica.orangehrm.stepdefinitions;

import com.pruebatecnica.orangehrm.pages.LoginPage;
import com.pruebatecnica.orangehrm.utils.Config;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;


/**
 * Step Definitions para los escenarios de Login.
 */
public class LoginStepDefinitions {

    @Steps
    LoginPage loginPage;

    private final Config config = Config.getInstance();

    // ============================================
    // Escenario: Navegar a la página de login
    // ============================================
    @Given("que el usuario navega a la página de inicio de sesión de OrangeHRM")
    public void queElUsuarioNavegaALaPaginaDeInicioDeSesionDeOrangeHRM() {
        loginPage.openLoginPage(); // Usa @DefaultUrl
    }

    // ============================================
    // Escenario: Login exitoso
    // ============================================
    @When("el usuario ingresa un nombre de usuario y contraseña válidos")
    public void elUsuarioIngresaUnNombreDeUsuarioYContrasenaValidos() {
        // Llenamos los campos sin hacer click aún
        loginPage.enterUsername(config.getAdminUsername());
        loginPage.enterPassword(config.getAdminPassword());
    }

    @When("hace clic en el botón de inicio de sesión")
    public void haceClicEnElBotonDeInicioDeSesion() {
        loginPage.clickLoginButton();
    }

    @Then("el usuario debería iniciar sesión exitosamente")
    public void elUsuarioDeberiaIniciarSesionExitosamente() {
        assert loginPage.isDashboardVisible() : "El login no fue exitoso, el dashboard no está visible.";
    }

    // ============================================
    // Escenario: Login fallido con credenciales inválidas
    // ============================================
    @When("el usuario ingresa un nombre de usuario inválido {string}")
    public void elUsuarioIngresaUnNombreDeUsuarioInvalido(String username) {
        loginPage.enterUsername(username);
    }

    @When("ingresa una contraseña inválida {string}")
    public void ingresaUnaContrasenaInvalida(String password) {
        loginPage.enterPassword(password);
    }

    @Then("el usuario debería ver un mensaje de error")
    public void elUsuarioDeberiaVerUnMensajeDeError() {
        assert loginPage.isLoginErrorMessageVisible() : "Mensaje de error de login debería ser visible";
    }

    @Then("debería permanecer en la página de inicio de sesión")
    public void deberiaPermanecerEnLaPaginaDeInicioDeSesion() {
        assert loginPage.isLoginFormVisible() : "Debería permanecer en la página de login";
    }

    // ============================================
    // Escenario: Login con campos vacíos
    // ============================================
    @When("el usuario ingresa un nombre de usuario vacío")
    public void elUsuarioIngresaUnNombreDeUsuarioVacio() {
        loginPage.clearUsernameField();
    }

    @When("ingresa una contraseña vacía")
    public void ingresaUnaContrasenaVacia() {
        loginPage.clearPasswordField();
    }

    @Then("el usuario debería ver errores de validación")
    public void elUsuarioDeberiaVerErroresDeValidacion() {
       assert loginPage.isUsernameFieldEmpty() : "Campo de usuario debería estar vacío";
       assert loginPage.isPasswordFieldEmpty() : "Campo de contraseña debería estar vacío";
    }

}
