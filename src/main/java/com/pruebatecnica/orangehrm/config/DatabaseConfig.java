package com.pruebatecnica.orangehrm.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Configuración de conexión a base de datos
 * Esta clase maneja la conexión a la base de datos para validaciones del ABM
 */
public class DatabaseConfig {
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/orangehrm_test";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    
    private static Connection connection = null;
    
    /**
     * Obtiene una conexión a la base de datos
     * @return Connection objeto de conexión
     * @throws SQLException si hay error en la conexión
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName(DB_DRIVER);
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                System.out.println("Conexión a base de datos establecida exitosamente");
            } catch (ClassNotFoundException e) {
                System.out.println("Error: Driver de MySQL no encontrado - " + e.getMessage());
                throw new SQLException("Driver de MySQL no encontrado", e);
            } catch (SQLException e) {
                System.out.println("Error al conectar con la base de datos - " + e.getMessage());
                throw e;
            }
        }
        return connection;
    }
    
    /**
     * Cierra la conexión a la base de datos
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexión a base de datos cerrada");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión - " + e.getMessage());
        }
    }
    
    /**
     * Verifica si la conexión está activa
     * @return true si está activa, false si no
     */
    public static boolean isConnectionActive() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
