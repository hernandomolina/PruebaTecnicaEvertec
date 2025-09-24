package com.pruebatecnica.orangehrm.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * Runner principal para ejecutar tests de Cucumber con Serenity BDD.
 * s
 * Esta clase configura la ejecución de tests de Cucumber integrados
 * con Serenity BDD para generar reportes detallados.
 * s
 * Configuraciones principales:
 * - features: Ubicación de los archivos .feature
 * - glue: Paquetes donde están los step definitions
 * - plugin: Plugins para reportes
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
        
        // Plugins para reportes
        plugin = {
                "pretty",
                "html:target/cucumber-reports/report.html",
                "json:target/cucumber-reports/report.json",
                "junit:target/cucumber-reports/report.xml"
        },
        
        // Etiquetas para filtrar tests
        tags = "@smoke or @regression",
        
        // Formato de salida
        monochrome = true,
        
        // Snippets para step definitions
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class TestRunner {

}
