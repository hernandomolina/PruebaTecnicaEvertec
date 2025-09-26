package com.pruebatecnica.orangehrm.stepdefinitions;

import com.pruebatecnica.orangehrm.pages.*;
import com.pruebatecnica.orangehrm.service.EmployeeValidationService;
import com.pruebatecnica.orangehrm.service.ValidationResult;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

public class EmployeeStepDefinitions {

    @Steps
    LoginPage loginPage;
    @Steps
    PimPage pimPage;
    @Steps
    AddEmployeePage addEmployeePage;
    @Steps
    EmployeeListPage employeeListPage;
    @Steps
    EmployeeDetailsPage employeeDetailsPage;

    @Given("que el usuario está logueado en OrangeHRM como administrador")
    public void queElUsuarioEstaLogueadoEnOrangeHRMComoAdministrador() {
        loginPage.open();
        loginPage.enterUsername("Admin");
        loginPage.enterPassword("admin123");
        loginPage.clickLoginButton();
    }

    @When("el usuario navega al módulo PIM")
    public void elUsuarioNavegaAlModuloPIM() {
        String pimUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/pim/viewEmployeeList";
        loginPage.getDriver().get(pimUrl);
        pimPage.waitForPageToLoad();
        System.out.println("Página PIM cargada exitosamente");
    }

    @And("hace clic en el botón Add Employee")
    public void haceClicEnElBotonAddEmployee() {
        pimPage.clickAddEmployee();
    }


    @And("ingresa el nombre {string}")
    public void ingresaElNombre(String firstName) {
        addEmployeePage.enterFirstName(firstName);
    }

    @And("ingresa el apellido {string}")
    public void ingresaElApellido(String lastName) {
        addEmployeePage.enterLastName(lastName);
    }

    @And("ingresa el ID generico")
    public void ingresaElIDGenerico() {
        addEmployeePage.enterRandomEmployeeId();
    }
    
    @And("hace clic en el botón Save")
    public void haceClicEnElBotonSave() {
        addEmployeePage.clickSave();
    }

    // Validation
    @Then("el empleado debería crearse exitosamente")
    public void elEmpleadoDeberiaCrearseExitosamente() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Assert.assertTrue("El empleado no se creó exitosamente", addEmployeePage.isFormSubmitted());
    }

    // ========== STEP DEFINITIONS PARA MODIFICAR EMPLEADO ==========
    
    // Variable para almacenar el ID del empleado creado
    private String empleadoIdGenerado;
    
    // Servicio de validación de base de datos (opcional)
    private EmployeeValidationService validationService = new EmployeeValidationService();

    @Given("que existe un empleado con nombre {string} y apellido {string}")
    public void queExisteUnEmpleadoConNombreYApellido(String firstName, String lastName) {
        // Navegar a PIM y crear un empleado de prueba
        elUsuarioNavegaAlModuloPIM();
        haceClicEnElBotonAddEmployee();
        ingresaElNombre(firstName);
        ingresaElApellido(lastName);
        ingresaElIDGenerico();
        haceClicEnElBotonSave();
        
        // Esperar a que se cree el empleado y capturar el ID generado
        try {
            Thread.sleep(3000);
            // Capturar el ID del empleado desde la URL
            String currentUrl = loginPage.getDriver().getCurrentUrl();
            System.out.println("URL actual después de crear empleado: " + currentUrl);
            
            if (currentUrl.contains("/empNumber/")) {
                empleadoIdGenerado = currentUrl.substring(currentUrl.lastIndexOf("/") + 1);
                System.out.println("Empleado creado con ID: " + empleadoIdGenerado);
            } else {
                System.out.println("No se pudo capturar el ID del empleado desde la URL");
                // Intentar capturar desde el campo Employee ID en la página
                try {
                    String employeeIdFromPage = addEmployeePage.getEmployeeId();
                    if (employeeIdFromPage != null && !employeeIdFromPage.isEmpty()) {
                        empleadoIdGenerado = employeeIdFromPage;
                        System.out.println("ID capturado desde la página: " + empleadoIdGenerado);
                    }
                } catch (Exception e) {
                    System.out.println("No se pudo capturar el ID desde la página: " + e.getMessage());
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("Empleado de prueba creado: " + firstName + " " + lastName + " con ID: " + empleadoIdGenerado);
        
        // Navegar de vuelta a la lista de empleados después de crear
        String employeeListUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/pim/viewEmployeeList";
        loginPage.getDriver().get(employeeListUrl);
        employeeListPage.waitForPageToLoad();
        System.out.println("Navegación de vuelta a la lista de empleados completada");
    }

    @When("el usuario busca el empleado por ID")
    public void elUsuarioBuscaElEmpleadoPorID() {
             
        // Buscar el empleado solo por ID
        employeeListPage.searchEmployeeById(empleadoIdGenerado);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Búsqueda realizada por ID: " + empleadoIdGenerado);
    }

    @And("hace clic en el primer empleado de la lista")
    public void haceCicEnElPrimerEmpleadoDeLaLista() {
        employeeListPage.clickOnFirstEmployee();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Primer empleado de la lista seleccionado");
    }


    @And("modifica el nombre a {string}")
    public void modificaElNombreA(String newFirstName) {
    
        employeeDetailsPage.enterFirstName(newFirstName);
        System.out.println("Nombre modificado a: " + newFirstName);
    }

    @And("modifica el apellido a {string}")
    public void modificaElApellidoA(String newLastName) {
        
        employeeDetailsPage.enterLastName(newLastName);
        System.out.println("Apellido modificado a: " + newLastName);
    }

    @And("hace clic en el botón Save para guardar cambios")
    public void haceClicEnElBotonSaveParaGuardarCambios() {
        employeeDetailsPage.clickSave();
    }

    @Then("el empleado debería actualizarse exitosamente")
    public void elEmpleadoDeberiaActualizarseExitosamente() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Assert.assertTrue("El empleado no se actualizó exitosamente", 
                         employeeDetailsPage.isSuccessMessageDisplayed());
        System.out.println("Empleado actualizado exitosamente");
    }

    @Then("debería ver el mensaje de confirmación {string}")
    public void deberiaVerElMensajeDeConfirmacion(String expectedMessage) {
        String actualMessage = employeeDetailsPage.getSuccessMessage();
        Assert.assertTrue("Mensaje esperado: " + expectedMessage + ", pero se obtuvo: " + actualMessage,
                         actualMessage.contains(expectedMessage));
        System.out.println("Mensaje de confirmación verificado: " + actualMessage);
    }

    // ===== STEP DEFINITIONS PARA ELIMINAR EMPLEADO =====

     @When("hace clic en el botón eliminar del empleado encontrado")
    public void haceClicEnElBotonEliminarDelEmpleadoEncontrado() {
        try {
            
            employeeListPage.clickDeleteButtonForFirstEmployee();
            System.out.println("Botón eliminar clickeado para el empleado encontrado");
        } catch (Exception e) {
            System.out.println("Error al hacer clic en el botón eliminar: " + e.getMessage());
            throw e;
        }
    }

    @When("confirma la eliminación en la alerta")
    public void confirmaLaEliminacionEnLaAlerta() {
        try {
            // Confirmar la eliminación en la alerta/ventana modal
            employeeListPage.confirmDeletionInAlert();
            System.out.println("Eliminación confirmada en la alerta");
        } catch (Exception e) {
            System.out.println("Error al confirmar eliminación: " + e.getMessage());
            throw e;
        }
    }

    @Then("el empleado debería eliminarse exitosamente")
    public void elEmpleadoDeberiaEliminarseExitosamente() {
        try {
            // Verificar que el empleado ya no existe buscándolo por ID
            employeeListPage.searchEmployeeById(empleadoIdGenerado);
            
            // Esperar un momento para que se actualice la lista
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            // Verificar que no hay resultados en la lista
            boolean employeeExists = employeeListPage.isEmployeeInList(empleadoIdGenerado);
            Assert.assertFalse("El empleado aún existe en la lista después de la eliminación", employeeExists);
            
            System.out.println("Empleado eliminado exitosamente - no se encuentra en la lista");
        } catch (Exception e) {
            System.out.println("Error al verificar eliminación: " + e.getMessage());
            throw e;
        }
    }
    
    // ===== MÉTODOS DE VALIDACIÓN DE BASE DE DATOS (OPCIONALES) =====
    
    /**
     * Valida la creación de empleado en base de datos (opcional)
     * Este método se puede llamar después de crear un empleado para validar en BD
     */
    public void validateEmployeeCreationInDatabase(String employeeId, String firstName, String lastName) {
        try {
            System.out.println("=== VALIDACIÓN DE BASE DE DATOS - CREAR EMPLEADO ===");
            ValidationResult result = validationService.validateCreateEmployee(employeeId, firstName, lastName);
            
            if (result.isValid()) {
                System.out.println("✓ Validación BD exitosa: " + result.getMessage());
                if (result.hasWarnings()) {
                    System.out.println("⚠ Advertencias: " + result.getWarningsAsString());
                }
            } else {
                System.out.println("✗ Validación BD falló: " + result.getErrorsAsString());
            }
        } catch (Exception e) {
            System.out.println("Error en validación de BD (no crítico): " + e.getMessage());
        }
    }
    
    /**
     * Valida la modificación de empleado en base de datos (opcional)
     * Este método se puede llamar después de modificar un empleado para validar en BD
     */
    public void validateEmployeeUpdateInDatabase(String employeeId, String newFirstName, String newLastName) {
        try {
            System.out.println("=== VALIDACIÓN DE BASE DE DATOS - MODIFICAR EMPLEADO ===");
            ValidationResult result = validationService.validateUpdateEmployee(employeeId, newFirstName, newLastName);
            
            if (result.isValid()) {
                System.out.println("✓ Validación BD exitosa: " + result.getMessage());
                if (result.hasWarnings()) {
                    System.out.println("⚠ Advertencias: " + result.getWarningsAsString());
                }
            } else {
                System.out.println("✗ Validación BD falló: " + result.getErrorsAsString());
            }
        } catch (Exception e) {
            System.out.println("Error en validación de BD (no crítico): " + e.getMessage());
        }
    }
    
    /**
     * Valida la eliminación de empleado en base de datos (opcional)
     * Este método se puede llamar después de eliminar un empleado para validar en BD
     */
    public void validateEmployeeDeletionInDatabase(String employeeId) {
        try {
            System.out.println("=== VALIDACIÓN DE BASE DE DATOS - ELIMINAR EMPLEADO ===");
            ValidationResult result = validationService.validateDeleteEmployee(employeeId);
            
            if (result.isValid()) {
                System.out.println("✓ Validación BD exitosa: " + result.getMessage());
                if (result.hasWarnings()) {
                    System.out.println("⚠ Advertencias: " + result.getWarningsAsString());
                }
            } else {
                System.out.println("✗ Validación BD falló: " + result.getErrorsAsString());
            }
        } catch (Exception e) {
            System.out.println("Error en validación de BD (no crítico): " + e.getMessage());
        }
    }
}