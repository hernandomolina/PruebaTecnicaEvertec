package com.pruebatecnica.orangehrm.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.support.FindBy;
import java.time.Duration;

@DefaultUrl("https://opensource-demo.orangehrmlive.com/web/index.php/pim/viewPersonalDetails/empNumber/")
public class EmployeeDetailsPage extends PageObject {

    // Campo de nombre
    @FindBy(xpath = "//input[@name='firstName']")
    private WebElementFacade firstNameInput;

    // Campo de apellido
    @FindBy(xpath = "//input[@name='lastName']")
    private WebElementFacade lastNameInput;

    // Campo de ID del empleado
    @FindBy(xpath = "//input[@name='employeeId']")
    private WebElementFacade employeeIdInput;

    // Botón de guardar
    @FindBy(xpath = "//button[@type='submit']")
    private WebElementFacade saveButton;

    // Botón de editar
    @FindBy(xpath = "//button[contains(@class, 'oxd-button--label-warn')]")
    private WebElementFacade editButton;

    // Mensajes
    @FindBy(xpath = "//div[@class='oxd-toast-content oxd-toast-content--success']")
    private WebElementFacade successMessage;

    // Métodos para editar
    public void clickEditButton() {
        try {
            // Esperar a que el botón esté visible y habilitado
            editButton.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(10));
            editButton.waitUntilEnabled().withTimeoutOf(Duration.ofSeconds(5));
            editButton.click();
            System.out.println("Botón Edit clickeado exitosamente");
        } catch (Exception e) {
            System.out.println("Error al hacer clic en el botón Edit: " + e.getMessage());
            throw e;
        }
    }

    public void enterFirstName(String firstName) {
        try {
            firstNameInput.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(10));
            firstNameInput.waitUntilEnabled().withTimeoutOf(Duration.ofSeconds(5));
            firstNameInput.clear();
            
            // Verificar que el campo esté vacío y limpiar nuevamente si es necesario
            String currentValue = firstNameInput.getAttribute("value");
            if (currentValue != null && !currentValue.isEmpty()) {
                System.out.println("Campo no se limpió completamente, valor actual: " + currentValue);
                
                firstNameInput.sendKeys(org.openqa.selenium.Keys.CONTROL + "a");
                firstNameInput.sendKeys(org.openqa.selenium.Keys.DELETE);

                currentValue = firstNameInput.getAttribute("value");
                if (currentValue != null && !currentValue.isEmpty()) {
                    System.out.println("Segundo intento de limpieza, valor actual: " + currentValue);
                    firstNameInput.clear();
                }
            }
            
            firstNameInput.sendKeys(firstName);
            System.out.println("Nombre ingresado: " + firstName);
        } catch (Exception e) {
            System.out.println("Error al ingresar nombre: " + e.getMessage());
            throw e;
        }
    }

    public void enterLastName(String lastName) {
        try {
            lastNameInput.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(10));
            lastNameInput.waitUntilEnabled().withTimeoutOf(Duration.ofSeconds(5));
            lastNameInput.clear();
            String currentValue = lastNameInput.getAttribute("value");
            if (currentValue != null && !currentValue.isEmpty()) {
                System.out.println("Campo no se limpió completamente, valor actual: " + currentValue);
                lastNameInput.sendKeys(org.openqa.selenium.Keys.CONTROL + "a");
                lastNameInput.sendKeys(org.openqa.selenium.Keys.DELETE);
                
                currentValue = lastNameInput.getAttribute("value");
                if (currentValue != null && !currentValue.isEmpty()) {
                    System.out.println("Segundo intento de limpieza, valor actual: " + currentValue);
                    lastNameInput.clear();
                }
            }
            
            lastNameInput.sendKeys(lastName);
            System.out.println("Apellido ingresado: " + lastName);
        } catch (Exception e) {
            System.out.println("Error al ingresar apellido: " + e.getMessage());
            throw e;
        }
    }

    public void clickSave() {
        try {
            saveButton.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(10));
            saveButton.waitUntilEnabled().withTimeoutOf(Duration.ofSeconds(5));
            saveButton.click();
            System.out.println("Botón Save clickeado exitosamente en EmployeeDetailsPage");
        } catch (Exception e) {
            System.out.println("Error al hacer clic en el botón Save: " + e.getMessage());
            throw e;
        }
    }

    // Métodos para verificar resultados
    public boolean isSuccessMessageDisplayed() {
        return successMessage.isDisplayed();
    }

    public String getSuccessMessage() {
        return successMessage.getText();
    }

    
}
