package com.pruebatecnica.orangehrm.service;

import com.pruebatecnica.orangehrm.dao.EmployeeDAO;
import com.pruebatecnica.orangehrm.model.Employee;

/**
 * Servicio de validaciones para operaciones ABM de empleados
 * Contiene la lógica de validación para cada operación del ABM
 */
public class EmployeeValidationService {
    
    private EmployeeDAO employeeDAO;
    
    public EmployeeValidationService() {
        this.employeeDAO = new EmployeeDAO();
    }
    
    /**
     * Valida la operación de ALTA (Crear empleado)
     * @param employeeId ID del empleado a crear
     * @param firstName Nombre del empleado
     * @param lastName Apellido del empleado
     * @return ValidationResult con el resultado de la validación
     */
    public ValidationResult validateCreateEmployee(String employeeId, String firstName, String lastName) {
        ValidationResult result = new ValidationResult();
        
        try {
            // Validar que los campos requeridos no estén vacíos
            if (employeeId == null || employeeId.trim().isEmpty()) {
                result.addError("El ID del empleado es requerido");
            }
            
            if (firstName == null || firstName.trim().isEmpty()) {
                result.addError("El nombre del empleado es requerido");
            }
            
            if (lastName == null || lastName.trim().isEmpty()) {
                result.addError("El apellido del empleado es requerido");
            }
            
            // Validar que el empleado no exista ya
            if (employeeId != null && !employeeId.trim().isEmpty()) {
                if (employeeDAO.employeeExists(employeeId)) {
                    result.addError("Ya existe un empleado con el ID: " + employeeId);
                }
            }
            
            // Validar formato del ID (solo números)
            if (employeeId != null && !employeeId.trim().isEmpty() && !employeeId.matches("\\d+")) {
                result.addError("El ID del empleado debe contener solo números");
            }
            
            // Si no hay errores, marcar como válido
            if (result.getErrors().isEmpty()) {
                result.setValid(true);
                result.setMessage("Validación de creación exitosa");
                System.out.println("Validación CREATE exitosa para empleado: " + employeeId);
            }
            
        } catch (Exception e) {
            result.addError("Error durante la validación: " + e.getMessage());
            System.out.println("Error en validación CREATE: " + e.getMessage());
        }
        
        return result;
    }
    
    /**
     * Valida la operación de MODIFICACIÓN (Actualizar empleado)
     * @param employeeId ID del empleado a modificar
     * @param newFirstName Nuevo nombre del empleado
     * @param newLastName Nuevo apellido del empleado
     * @return ValidationResult con el resultado de la validación
     */
    public ValidationResult validateUpdateEmployee(String employeeId, String newFirstName, String newLastName) {
        ValidationResult result = new ValidationResult();
        
        try {
            // Validar que el empleado existe
            if (!employeeDAO.employeeExists(employeeId)) {
                result.addError("No existe un empleado con el ID: " + employeeId);
                return result;
            }
            
            // Validar que los nuevos datos no estén vacíos
            if (newFirstName == null || newFirstName.trim().isEmpty()) {
                result.addError("El nuevo nombre del empleado es requerido");
            }
            
            if (newLastName == null || newLastName.trim().isEmpty()) {
                result.addError("El nuevo apellido del empleado es requerido");
            }
            
            // Validar que los datos sean diferentes a los actuales
            Employee currentEmployee = employeeDAO.findEmployeeById(employeeId);
            if (currentEmployee != null) {
                if (newFirstName != null && newFirstName.equals(currentEmployee.getFirstName())) {
                    result.addWarning("El nuevo nombre es igual al actual");
                }
                
                if (newLastName != null && newLastName.equals(currentEmployee.getLastName())) {
                    result.addWarning("El nuevo apellido es igual al actual");
                }
            }
            
            // Si no hay errores, marcar como válido
            if (result.getErrors().isEmpty()) {
                result.setValid(true);
                result.setMessage("Validación de modificación exitosa");
                System.out.println("Validación UPDATE exitosa para empleado: " + employeeId);
            }
            
        } catch (Exception e) {
            result.addError("Error durante la validación: " + e.getMessage());
            System.out.println("Error en validación UPDATE: " + e.getMessage());
        }
        
        return result;
    }
    
    /**
     * Valida la operación de BAJA (Eliminar empleado)
     * @param employeeId ID del empleado a eliminar
     * @return ValidationResult con el resultado de la validación
     */
    public ValidationResult validateDeleteEmployee(String employeeId) {
        ValidationResult result = new ValidationResult();
        
        try {
            // Validar que el empleado existe
            if (!employeeDAO.employeeExists(employeeId)) {
                result.addError("No existe un empleado activo con el ID: " + employeeId);
                return result;
            }
            
            // Obtener información del empleado para validaciones adicionales
            Employee employee = employeeDAO.findEmployeeById(employeeId);
            if (employee != null) {
                // Validar que el empleado no tenga dependencias (ejemplo: no tener registros relacionados)
                // Aquí se podrían agregar validaciones de negocio específicas
                System.out.println("Validando eliminación de empleado: " + employee.getFirstName() + " " + employee.getLastName());
            }
            
            // Si no hay errores, marcar como válido
            if (result.getErrors().isEmpty()) {
                result.setValid(true);
                result.setMessage("Validación de eliminación exitosa");
                System.out.println("Validación DELETE exitosa para empleado: " + employeeId);
            }
            
        } catch (Exception e) {
            result.addError("Error durante la validación: " + e.getMessage());
            System.out.println("Error en validación DELETE: " + e.getMessage());
        }
        
        return result;
    }
    
    /**
     * Ejecuta la operación de creación en la base de datos
     * @param employeeId ID del empleado
     * @param firstName Nombre del empleado
     * @param lastName Apellido del empleado
     * @return true si se creó exitosamente, false si no
     */
    public boolean executeCreateEmployee(String employeeId, String firstName, String lastName) {
        try {
            Employee employee = new Employee(employeeId, firstName, lastName);
            return employeeDAO.createEmployee(employee);
        } catch (Exception e) {
            System.out.println("Error al ejecutar CREATE: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Ejecuta la operación de actualización en la base de datos
     * @param employeeId ID del empleado
     * @param newFirstName Nuevo nombre
     * @param newLastName Nuevo apellido
     * @return true si se actualizó exitosamente, false si no
     */
    public boolean executeUpdateEmployee(String employeeId, String newFirstName, String newLastName) {
        try {
            Employee employee = employeeDAO.findEmployeeById(employeeId);
            if (employee != null) {
                employee.setFirstName(newFirstName);
                employee.setLastName(newLastName);
                return employeeDAO.updateEmployee(employee);
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error al ejecutar UPDATE: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Ejecuta la operación de eliminación en la base de datos
     * @param employeeId ID del empleado
     * @return true si se eliminó exitosamente, false si no
     */
    public boolean executeDeleteEmployee(String employeeId) {
        try {
            return employeeDAO.deleteEmployee(employeeId);
        } catch (Exception e) {
            System.out.println("Error al ejecutar DELETE: " + e.getMessage());
            return false;
        }
    }
}
