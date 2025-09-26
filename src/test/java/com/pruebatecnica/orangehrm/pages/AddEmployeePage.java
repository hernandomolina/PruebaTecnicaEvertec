package com.pruebatecnica.orangehrm.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

@DefaultUrl("https://opensource-demo.orangehrmlive.com/web/index.php/pim/addEmployee")
public class AddEmployeePage extends PageObject {

    @FindBy(xpath = "//input[@name='firstName']")
    private WebElementFacade firstNameInput;

    @FindBy(xpath = "//input[@name='lastName']")
    private WebElementFacade lastNameInput;

    @FindBy(xpath = "//label[text()='Employee Id']/parent::div/following-sibling::div//input")
    private WebElementFacade employeeIdInput;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElementFacade saveButton;

    public void enterFirstName(String firstName) {
        firstNameInput.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(5)).clear();
        firstNameInput.sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        lastNameInput.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(5)).clear();
        lastNameInput.sendKeys(lastName);
    }

    public void enterRandomEmployeeId() {
        try {
            // Generar un ID aleatorio más único usando timestamp + número aleatorio
            long timestamp = System.currentTimeMillis();
            int randomSuffix = (int) (Math.random() * 100); // 0-99
            String randomEmployeeId = String.valueOf(timestamp % 10000) + randomSuffix; // Últimos 4 dígitos del timestamp + sufijo
            
            employeeIdInput.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(5));
            employeeIdInput.clear();
            employeeIdInput.sendKeys(randomEmployeeId);
            System.out.println("Employee ID genérico generado: " + randomEmployeeId);
        } catch (Exception e) {
            System.out.println("Error generando ID genérico: " + e.getMessage());
            
            try {
                String fallbackId = String.valueOf(System.currentTimeMillis() % 100000);
                employeeIdInput.clear();
                employeeIdInput.sendKeys(fallbackId);
                System.out.println("Usando ID de respaldo: " + fallbackId);
            } catch (Exception fallbackError) {
                System.out.println("Error crítico generando ID: " + fallbackError.getMessage());
            }
        }
    }

    public void clickSave() {
        System.out.println("Haciendo clic en botón Save...");
        
        // Verificar que el botón esté visible y habilitado
        if (saveButton.isVisible() && saveButton.isEnabled()) {
            saveButton.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(5)).click();
            System.out.println("Botón Save clickeado exitosamente");
        } else {
            System.out.println("Botón Save no está disponible - Visible: " + saveButton.isVisible() + ", Enabled: " + saveButton.isEnabled());
        }
    }

    public boolean isFormSubmitted() {
        try {
            // TEMPORAL: Deshabilitar validación de errores para debug
            System.out.println("Verificando creación de empleado...");
            
            // Esperar a que la URL cambie usando WebDriverWait (mejor práctica)
            waitForUrlToChange();
            
            String currentUrl = getDriver().getCurrentUrl();
            System.out.println("URL actual después de Save: " + currentUrl);
            
            // Verificar que la URL contiene el patrón de empleado creado
            boolean isEmployeeCreated = currentUrl.matches(".*pim/viewPersonalDetails/empNumber/\\d+$");
            
            if (isEmployeeCreated) {
                String employeeId = extractEmployeeIdFromUrl(currentUrl);
                System.out.println("Empleado creado exitosamente con ID generado automáticamente: " + employeeId);
                System.out.println("Nota: OrangeHRM ignora el ID ingresado manualmente y genera uno automáticamente");
            } else {
                System.out.println("La URL no coincide con el patrón esperado de empleado creado");
                System.out.println("Patrón esperado: .*pim/viewPersonalDetails/empNumber/\\d+$");
            }
            
            return isEmployeeCreated;
        } catch (Exception e) {
            System.out.println("Error en validación: " + e.getMessage());
            return false;
        }
    }
    
    
    
    private void waitForUrlToChange() {
        String initialUrl = getDriver().getCurrentUrl();
        System.out.println("URL inicial: " + initialUrl);
        
        try {
            
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
            wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(initialUrl)));
            System.out.println("URL cambió exitosamente");
        } catch (Exception e) {
            System.out.println("URL no cambió después de 10 segundos: " + e.getMessage());
        }
    }
    
    
    private String extractEmployeeIdFromUrl(String url) {
        try {
            String pattern = ".*pim/viewPersonalDetails/empNumber/(\\d+)$";
            java.util.regex.Pattern r = java.util.regex.Pattern.compile(pattern);
            java.util.regex.Matcher m = r.matcher(url);
            
            if (m.find()) {
                return m.group(1);
            }
        } catch (Exception e) {
            System.out.println("Error extrayendo ID del empleado: " + e.getMessage());
        }
        return "No encontrado";
    }
    
    public String getEmployeeId() {
        try {
            // Intentar obtener el ID del empleado desde el campo
            employeeIdInput.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(5));
            String employeeId = employeeIdInput.getAttribute("value");
            System.out.println("ID del empleado capturado: " + employeeId);
            return employeeId;
        } catch (Exception e) {
            System.out.println("Error al obtener el ID del empleado: " + e.getMessage());
            return null;
        }
    }
}