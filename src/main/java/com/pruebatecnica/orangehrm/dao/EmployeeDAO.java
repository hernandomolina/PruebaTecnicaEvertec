package com.pruebatecnica.orangehrm.dao;

import com.pruebatecnica.orangehrm.config.DatabaseConfig;
import com.pruebatecnica.orangehrm.model.Employee;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para operaciones CRUD de Employee
 * Maneja todas las operaciones de base de datos para empleados
 */
public class EmployeeDAO {
    
    private static final String TABLE_NAME = "employees";
    
    /**
     * Crea un nuevo empleado en la base de datos
     * @param employee objeto Employee a crear
     * @return true si se creó exitosamente, false si no
     */
    public boolean createEmployee(Employee employee) {
        String sql = "INSERT INTO " + TABLE_NAME + 
                    " (employee_id, first_name, last_name, middle_name, email, status, created_at, updated_at) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, employee.getEmployeeId());
            stmt.setString(2, employee.getFirstName());
            stmt.setString(3, employee.getLastName());
            stmt.setString(4, employee.getMiddleName());
            stmt.setString(5, employee.getEmail());
            stmt.setString(6, employee.getStatus());
            stmt.setTimestamp(7, Timestamp.valueOf(employee.getCreatedAt()));
            stmt.setTimestamp(8, Timestamp.valueOf(employee.getUpdatedAt()));
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        employee.setId(generatedKeys.getLong(1));
                    }
                }
                System.out.println("Empleado creado en BD: " + employee.getEmployeeId());
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error al crear empleado en BD: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Busca un empleado por su ID de empleado
     * @param employeeId ID del empleado a buscar
     * @return Employee encontrado o null si no existe
     */
    public Employee findEmployeeById(String employeeId) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE employee_id = ? AND status = 'ACTIVE'";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, employeeId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEmployee(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar empleado por ID: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Actualiza un empleado existente
     * @param employee objeto Employee con los datos actualizados
     * @return true si se actualizó exitosamente, false si no
     */
    public boolean updateEmployee(Employee employee) {
        String sql = "UPDATE " + TABLE_NAME + 
                    " SET first_name = ?, last_name = ?, middle_name = ?, email = ?, updated_at = ? " +
                    "WHERE employee_id = ? AND status = 'ACTIVE'";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, employee.getFirstName());
            stmt.setString(2, employee.getLastName());
            stmt.setString(3, employee.getMiddleName());
            stmt.setString(4, employee.getEmail());
            stmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(6, employee.getEmployeeId());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Empleado actualizado en BD: " + employee.getEmployeeId());
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar empleado en BD: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Elimina un empleado (soft delete - cambia status a INACTIVE)
     * @param employeeId ID del empleado a eliminar
     * @return true si se eliminó exitosamente, false si no
     */
    public boolean deleteEmployee(String employeeId) {
        String sql = "UPDATE " + TABLE_NAME + 
                    " SET status = 'INACTIVE', updated_at = ? " +
                    "WHERE employee_id = ? AND status = 'ACTIVE'";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(2, employeeId);
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Empleado eliminado en BD: " + employeeId);
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar empleado en BD: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Verifica si un empleado existe por su ID
     * @param employeeId ID del empleado a verificar
     * @return true si existe, false si no
     */
    public boolean employeeExists(String employeeId) {
        return findEmployeeById(employeeId) != null;
    }
    
    /**
     * Obtiene todos los empleados activos
     * @return Lista de empleados activos
     */
    public List<Employee> getAllActiveEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE status = 'ACTIVE' ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                employees.add(mapResultSetToEmployee(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener empleados: " + e.getMessage());
        }
        return employees;
    }
    
    /**
     * Mapea un ResultSet a un objeto Employee
     * @param rs ResultSet de la consulta
     * @return objeto Employee mapeado
     * @throws SQLException si hay error en el mapeo
     */
    private Employee mapResultSetToEmployee(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        employee.setId(rs.getLong("id"));
        employee.setEmployeeId(rs.getString("employee_id"));
        employee.setFirstName(rs.getString("first_name"));
        employee.setLastName(rs.getString("last_name"));
        employee.setMiddleName(rs.getString("middle_name"));
        employee.setEmail(rs.getString("email"));
        employee.setStatus(rs.getString("status"));
        
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            employee.setCreatedAt(createdAt.toLocalDateTime());
        }
        
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            employee.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        
        return employee;
    }
}
