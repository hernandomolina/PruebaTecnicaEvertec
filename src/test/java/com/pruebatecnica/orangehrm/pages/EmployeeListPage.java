package com.pruebatecnica.orangehrm.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import java.time.Duration;
import java.util.List;

@DefaultUrl("https://opensource-demo.orangehrmlive.com/web/index.php/pim/viewEmployeeList")
public class EmployeeListPage extends PageObject {

    // Botón de búsqueda
    @FindBy(xpath = "//button[@type='submit']")
    private WebElementFacade searchButton;
    
    // Campo de búsqueda por Employee ID
    @FindBy(xpath = "//form[@class='oxd-form']//input[contains(@class,'oxd-input')]")
    private WebElementFacade employeeIdInput;

    // Elementos de la tabla de empleados
    @FindBy(xpath = "//div[@class='oxd-table-card']")
    private List<WebElementFacade> employeeCards;
    
    // Botón eliminar del primer empleado en la lista
    @FindBy(xpath = "(//div[@class='oxd-table-cell-actions']//button)[2]")
    private WebElementFacade firstEmployeeDeleteButton;
    
    // Botón de confirmación de eliminación en la alerta
    @FindBy(xpath = "//div[contains(@class,'oxd-dialog-container')]//button[normalize-space()='Yes, Delete']")
    private WebElementFacade confirmDeleteAlertButton;


    public void searchEmployeeById(String employeeId) {
        try {
            // Buscar solo por ID del empleado
            employeeIdInput.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(5));
            employeeIdInput.clear();
            employeeIdInput.sendKeys(employeeId);
            searchButton.click();
            
            System.out.println("Búsqueda realizada por ID: " + employeeId);
        } catch (Exception e) {
            System.out.println("Error en la búsqueda por ID: " + e.getMessage());
            throw e;
        }
    }

    
    // Métodos de acción
    public void clickOnFirstEmployee() {
        if (!employeeCards.isEmpty()) {
            employeeCards.get(0).click();
        }
    }



    public void waitForPageToLoad() {
        try {
            employeeIdInput.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(8));
            System.out.println("Campo de búsqueda visible - página Employee List lista");
        } catch (Exception e) {
            System.out.println("Página Employee List cargada pero campo de búsqueda no encontrado: " + e.getMessage());
        }
    }

    // ===== MÉTODOS PARA ELIMINAR EMPLEADO =====

    public void clickDeleteButtonForFirstEmployee() {
        try {
            waitForPageToLoad();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            firstEmployeeDeleteButton.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(10));
            firstEmployeeDeleteButton.waitUntilEnabled().withTimeoutOf(Duration.ofSeconds(5));

            firstEmployeeDeleteButton.click();
            
            System.out.println("Botón eliminar clickeado para el primer empleado");
        } catch (Exception e) {
            System.out.println("Error al hacer clic en el botón eliminar: " + e.getMessage());
            throw e;
        }
    }

    
    public void confirmDeletionInAlert() {
        try {
            // Esperar a que aparezca la alerta de confirmación
            confirmDeleteAlertButton.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(10));
            confirmDeleteAlertButton.waitUntilEnabled().withTimeoutOf(Duration.ofSeconds(5));
            
            // Hacer clic en el botón de confirmación
            confirmDeleteAlertButton.click();
            
            System.out.println("Eliminación confirmada en la alerta");
        } catch (Exception e) {
            System.out.println("Error al confirmar eliminación en la alerta: " + e.getMessage());
            throw e;
        }
    }

    
    public boolean isEmployeeInList(String employeeId) {
        try {
            // Buscar el empleado por ID en la lista
            WebElementFacade employeeRow = find(By.xpath("//div[@class='oxd-table-body']//div[@class='oxd-table-row']//div[contains(text(), '" + employeeId + "')]"));
            
            // Si encuentra el elemento, el empleado existe
            return employeeRow.isVisible();
        } catch (Exception e) {
            // Si no encuentra el elemento, el empleado no existe
            System.out.println("Empleado no encontrado en la lista (eliminado exitosamente)");
            return false;
        }
    }
}
