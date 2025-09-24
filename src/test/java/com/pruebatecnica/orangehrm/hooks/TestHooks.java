package com.pruebatecnica.orangehrm.hooks;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;

/**
 * Hooks para configuración y limpieza de tests.
 * 
 * Esta clase contiene los hooks de Cucumber que se ejecutan
 * antes y después de cada escenario para configurar el entorno
 * de prueba y realizar limpieza.
 */
public class TestHooks {

    /**
     * Hook que se ejecuta antes de cada escenario.
     * 
     * @param scenario El escenario que se va a ejecutar
     */
    @Before
    public void setUp(Scenario scenario) {
        System.out.println("=== Iniciando escenario: " + scenario.getName() + " ===");
        System.out.println("Tags del escenario: " + scenario.getSourceTagNames());
        
        // Configuraciones adicionales pueden ir aquí
        // Por ejemplo: limpiar cookies, configurar timeouts, etc.
    }

    /**
     * Hook que se ejecuta después de cada escenario.
     * 
     * @param scenario El escenario que se ejecutó
     */
    @After
    public void tearDown(Scenario scenario) {
        System.out.println("=== Finalizando escenario: " + scenario.getName() + " ===");
        System.out.println("Estado del escenario: " + (scenario.isFailed() ? "FALLÓ" : "EXITOSO"));
        
        // Capturar screenshot si el escenario falló
        if (scenario.isFailed()) {
            captureScreenshot(scenario);
        }
        
        // Limpieza adicional puede ir aquí
        // Por ejemplo: cerrar ventanas adicionales, limpiar datos de prueba, etc.
    }

    /**
     * Captura un screenshot cuando un escenario falla.
     * 
     * @param scenario El escenario que falló
     */
    private void captureScreenshot(Scenario scenario) {
        try {
            // Screenshot será manejado automáticamente por Serenity BDD
            System.out.println("Screenshot será capturado automáticamente por Serenity BDD");
            
            // Log adicional para debugging
            scenario.log("Escenario falló: " + scenario.getName());
            
        } catch (Exception e) {
            System.err.println("Error al procesar screenshot: " + e.getMessage());
        }
    }

    /**
     * Hook específico para escenarios con tag @smoke.
     * Se ejecuta antes de escenarios marcados con @smoke.
     */
    @Before("@smoke")
    public void setUpSmokeTests() {
        System.out.println("=== Configurando test de Smoke ===");
        // Configuraciones específicas para tests de smoke
    }

    /**
     * Hook específico para escenarios con tag @regression.
     * Se ejecuta antes de escenarios marcados con @regression.
     */
    @Before("@regression")
    public void setUpRegressionTests() {
        System.out.println("=== Configurando test de Regresión ===");
        // Configuraciones específicas para tests de regresión
    }

    /**
     * Hook específico para escenarios con tag @login.
     * Se ejecuta antes de escenarios marcados con @login.
     */
    @Before("@login")
    public void setUpLoginTests() {
        System.out.println("=== Configurando test de Login ===");
        // Configuraciones específicas para tests de login
    }
}
