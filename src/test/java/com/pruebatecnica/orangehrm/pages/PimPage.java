package com.pruebatecnica.orangehrm.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.support.FindBy;
import java.time.Duration;

@DefaultUrl("https://opensource-demo.orangehrmlive.com/web/index.php/pim/viewEmployeeList")
public class PimPage extends PageObject {

    @FindBy(xpath = "//a[contains(text(), 'Add Employee')]")
    private WebElementFacade addEmployeeLink;

    public void clickAddEmployee() {
        System.out.println("🔗 Haciendo clic en enlace Add Employee...");
        
        // Espera explícita con timeout específico
        addEmployeeLink.waitUntilClickable().withTimeoutOf(Duration.ofSeconds(5)).click();
        System.out.println("Enlace Add Employee clickeado exitosamente");
    }

    public void waitForPageToLoad() {
        try {
            // Esperar a que el enlace esté visible con timeout específico
            addEmployeeLink.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(8));
            System.out.println("Enlace Add Employee visible - página PIM lista");
        } catch (Exception e) {
            System.out.println("Página PIM cargada pero enlace Add Employee no encontrado: " + e.getMessage());
        }
    }

}