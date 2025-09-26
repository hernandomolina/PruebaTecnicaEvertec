package com.pruebatecnica.orangehrm.hooks;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;


public class TestHooks {

    @Before
    public void setUp(Scenario scenario) {
        String browser = System.getProperty("webdriver.driver", "chrome");
        String browserName = System.getProperty("browser.name", browser + "-auto");
        String threadName = Thread.currentThread().getName();
        
        System.out.println("=== Iniciando escenario: " + scenario.getName() + " ===");
        System.out.println("Navegador: " + browserName.toUpperCase());
        System.out.println("Thread: " + threadName);
        System.out.println("Tags del escenario: " + scenario.getSourceTagNames());
        
    }

    @After
    public void tearDown(Scenario scenario) {

        String browser = System.getProperty("webdriver.driver", "chrome");
        String browserName = System.getProperty("browser.name", browser + "-auto");
        String threadName = Thread.currentThread().getName();
        
        System.out.println("=== Finalizando escenario: " + scenario.getName() + " ===");
        System.out.println("Navegador: " + browserName.toUpperCase());
        System.out.println("Thread: " + threadName);
        System.out.println("Estado del escenario: " + (scenario.isFailed() ? "FALLÓ" : "EXITOSO"));
        
        if (scenario.isFailed()) {
            captureScreenshot(scenario);
        }
        
    }

    /**
     * Captura un screenshot cuando un escenario falla.
     * 
     * @param scenario El escenario que falló
     */
    private void captureScreenshot(Scenario scenario) {
        try {
            System.out.println("Screenshot será capturado automáticamente por Serenity BDD");
            
            scenario.log("Escenario falló: " + scenario.getName());
            
        } catch (Exception e) {
            System.err.println("Error al procesar screenshot: " + e.getMessage());
        }
    }
}
