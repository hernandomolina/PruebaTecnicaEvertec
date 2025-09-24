package com.pruebatecnica.orangehrm.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Clase utilitaria para manejar configuraciones de la aplicación.
 * 
 * Esta clase proporciona métodos para cargar y acceder a propiedades
 * de configuración desde archivos properties o variables de entorno.
 * 
 * Utiliza el patrón Singleton para asegurar una única instancia.
 */
public class Config {
    
    private static Config instance;
    private Properties properties;
    
    /**
     * Constructor privado para implementar el patrón Singleton.
     * Carga las propiedades por defecto.
     */
    private Config() {
        properties = new Properties();
        loadDefaultProperties();
    }
    
    /**
     * Obtiene la instancia única de Config.
     * 
     * @return La instancia de Config
     */
    public static synchronized Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }
    
    /**
     * Carga las propiedades por defecto desde serenity.properties.
     */
    private void loadDefaultProperties() {
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("serenity.properties")) {
            
            if (input != null) {
                properties.load(input);
                System.out.println("Propiedades cargadas desde serenity.properties");
            } else {
                System.out.println("No se encontró el archivo serenity.properties");
            }
        } catch (IOException e) {
            System.err.println("Error al cargar propiedades: " + e.getMessage());
        }
    }
    
    /**
     * Carga propiedades desde un archivo específico.
     * 
     * @param filePath La ruta del archivo properties
     */
    public void loadPropertiesFromFile(String filePath) {
        try (FileInputStream input = new FileInputStream(filePath)) {
            properties.load(input);
            System.out.println("Propiedades cargadas desde: " + filePath);
        } catch (IOException e) {
            System.err.println("Error al cargar propiedades desde archivo: " + e.getMessage());
        }
    }
    
    /**
     * Obtiene una propiedad como String.
     * 
     * @param key La clave de la propiedad
     * @return El valor de la propiedad o null si no existe
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    /**
     * Obtiene una propiedad como String con un valor por defecto.
     * 
     * @param key La clave de la propiedad
     * @param defaultValue El valor por defecto si la propiedad no existe
     * @return El valor de la propiedad o el valor por defecto
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    /**
     * Obtiene una propiedad como entero.
     * 
     * @param key La clave de la propiedad
     * @return El valor de la propiedad como entero o 0 si no existe o no es válido
     */
    public int getIntProperty(String key) {
        return getIntProperty(key, 0);
    }
    
    /**
     * Obtiene una propiedad como entero con un valor por defecto.
     * 
     * @param key La clave de la propiedad
     * @param defaultValue El valor por defecto si la propiedad no existe
     * @return El valor de la propiedad como entero o el valor por defecto
     */
    public int getIntProperty(String key, int defaultValue) {
        try {
            return Integer.parseInt(properties.getProperty(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir la propiedad " + key + " a entero: " + e.getMessage());
            return defaultValue;
        }
    }
    
    /**
     * Obtiene una propiedad como booleano.
     * 
     * @param key La clave de la propiedad
     * @return El valor de la propiedad como booleano o false si no existe
     */
    public boolean getBooleanProperty(String key) {
        return getBooleanProperty(key, false);
    }
    
    /**
     * Obtiene una propiedad como booleano con un valor por defecto.
     * 
     * @param key La clave de la propiedad
     * @param defaultValue El valor por defecto si la propiedad no existe
     * @return El valor de la propiedad como booleano o el valor por defecto
     */
    public boolean getBooleanProperty(String key, boolean defaultValue) {
        return Boolean.parseBoolean(properties.getProperty(key, String.valueOf(defaultValue)));
    }
    
    // Métodos específicos para configuraciones comunes
    
    /**
     * Obtiene la URL base de la aplicación.
     * 
     * @return La URL base configurada
     */
    public String getBaseUrl() {
        return getProperty("webdriver.base.url", "https://opensource-demo.orangehrmlive.com/");
    }
    
    /**
     * Obtiene el nombre de usuario administrador.
     * 
     * @return El nombre de usuario administrador
     */
    public String getAdminUsername() {
        return getProperty("app.admin.username", "Admin");
    }
    
    /**
     * Obtiene la contraseña del administrador.
     * 
     * @return La contraseña del administrador
     */
    public String getAdminPassword() {
        return getProperty("app.admin.password", "admin123");
    }
    
    /**
     * Obtiene el navegador configurado.
     * 
     * @return El nombre del navegador
     */
    public String getBrowser() {
        return getProperty("webdriver.driver", "chrome");
    }
    
    /**
     * Verifica si el modo headless está habilitado.
     * 
     * @return true si está en modo headless, false en caso contrario
     */
    public boolean isHeadlessMode() {
        return getBooleanProperty("headless.mode", false);
    }
    
    /**
     * Obtiene el timeout de espera implícita en milisegundos.
     * 
     * @return El timeout en milisegundos
     */
    public int getImplicitWaitTimeout() {
        return getIntProperty("webdriver.timeouts.implicitlywait", 10000);
    }
}
