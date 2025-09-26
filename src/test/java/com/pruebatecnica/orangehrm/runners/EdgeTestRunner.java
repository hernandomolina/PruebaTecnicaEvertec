package com.pruebatecnica.orangehrm.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

/**
 * Runner para ejecutar todos los tests en Microsoft Edge.
 * Ejecuta los mismos escenarios que ChromeTestRunner pero en Edge.
 * 
 * Este runner se ejecutará en paralelo con ChromeTestRunner cuando se lance
 * el comando: mvn clean verify
 */
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        // Ubicación de los archivos de características (features)
        features = "src/test/resources/features",
        
        // Paquetes donde están los step definitions y hooks
        glue = {
                "com.pruebatecnica.orangehrm.stepdefinitions",
                "com.pruebatecnica.orangehrm.hooks"
        },
        
        // Plugins para reportes - separados por navegador
        plugin = {
                "pretty",
                "html:target/cucumber-reports/edge-report.html",
                "json:target/cucumber-reports/edge-report.json",
                "junit:target/cucumber-reports/edge-report.xml"
        },
        
        // Etiquetas para filtrar tests - Solo ABM
        tags = "@abm or @login",
        
        // Formato de salida
        monochrome = true,
        
        // Snippets para step definitions
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class EdgeTestRunner {

    /**
     * Configuración que se ejecuta antes de todos los tests de esta clase.
     * Establece Edge como el navegador para este runner.
     */
    @BeforeClass
    public static void setUp() {
        // Edge: Usar driver local (configuración robusta)
        System.setProperty("webdriver.driver", "edge");
        System.setProperty("serenity.browser.name", "edge");
        System.setProperty("browser.name", "edge");
        System.setProperty("webdriver.edge.driver", "C:\\Drivers\\Edge\\msedgedriver.exe");
        
        // Desactivar WebDriverManager y Selenium Manager para Edge
        System.setProperty("webdriver.autodownload", "false");
        System.setProperty("wdm.avoidAutoReset", "true");
        System.setProperty("wdm.avoidFallback", "true");
        System.setProperty("wdm.avoidReadReleaseFromRepository", "true");
        System.setProperty("wdm.avoidBrowserDetection", "true");
        
        // Desactivar Selenium Manager para Edge
        System.setProperty("webdriver.edge.useChromiumDriver", "true");
        
        // Asegurar que las propiedades se propaguen a todos los threads
        System.setProperty("serenity.browser.width", "1920");
        System.setProperty("serenity.browser.height", "1080");
        
        System.out.println("=== CONFIGURANDO EJECUCIÓN EN EDGE ===");
        System.out.println("Navegador: EDGE (driver local)");
        System.out.println("Thread: " + Thread.currentThread().getName());
        System.out.println("Proceso: " + ProcessHandle.current().pid());
        System.out.println("Driver Edge: " + System.getProperty("webdriver.edge.driver"));
    }
}