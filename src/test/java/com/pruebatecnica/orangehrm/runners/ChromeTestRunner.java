package com.pruebatecnica.orangehrm.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

/**
 * Runner para ejecutar todos los tests en Google Chrome.
 * Ejecuta los mismos escenarios que EdgeTestRunner pero en Chrome.
 * 
 * Este runner se ejecutará en paralelo con EdgeTestRunner cuando se lance
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
                "html:target/cucumber-reports/chrome-report.html",
                "json:target/cucumber-reports/chrome-report.json",
                "junit:target/cucumber-reports/chrome-report.xml"
        },
        
        // Etiquetas para filtrar tests - Solo ABM
        tags = "@abm or @login",
        
        // Formato de salida
        monochrome = true,
        
        // Snippets para step definitions
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class ChromeTestRunner {

    /**
     * Configuración que se ejecuta antes de todos los tests de esta clase.
     * Establece Chrome como el navegador para este runner.
     */
    @BeforeClass
    public static void setUp() {
        // Chrome: Usar descarga automática de WebDriverManager
        System.setProperty("webdriver.driver", "chrome");
        System.setProperty("serenity.browser.name", "chrome");
        System.setProperty("browser.name", "chrome");
        System.setProperty("webdriver.autodownload", "true");
        
        // Asegurar que las propiedades se propaguen a todos los threads
        System.setProperty("serenity.browser.width", "1920");
        System.setProperty("serenity.browser.height", "1080");
        
        System.out.println("=== CONFIGURANDO EJECUCIÓN EN CHROME ===");
        System.out.println("Navegador: CHROME (descarga automática)");
        System.out.println("Thread: " + Thread.currentThread().getName());
        System.out.println("Proceso: " + ProcessHandle.current().pid());
    }
}
