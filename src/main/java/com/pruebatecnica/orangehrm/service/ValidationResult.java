package com.pruebatecnica.orangehrm.service;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase para manejar resultados de validaciones
 * Contiene información sobre si la validación fue exitosa y los mensajes correspondientes
 */
public class ValidationResult {
    
    private boolean valid = false;
    private String message = "";
    private List<String> errors = new ArrayList<>();
    private List<String> warnings = new ArrayList<>();
    
    /**
     * Agrega un error a la lista de errores
     * @param error mensaje de error
     */
    public void addError(String error) {
        this.errors.add(error);
    }
    
    /**
     * Agrega una advertencia a la lista de advertencias
     * @param warning mensaje de advertencia
     */
    public void addWarning(String warning) {
        this.warnings.add(warning);
    }
    
    /**
     * Verifica si hay errores
     * @return true si hay errores, false si no
     */
    public boolean hasErrors() {
        return !errors.isEmpty();
    }
    
    /**
     * Verifica si hay advertencias
     * @return true si hay advertencias, false si no
     */
    public boolean hasWarnings() {
        return !warnings.isEmpty();
    }
    
    /**
     * Obtiene todos los errores como una cadena
     * @return cadena con todos los errores separados por comas
     */
    public String getErrorsAsString() {
        return String.join(", ", errors);
    }
    
    /**
     * Obtiene todas las advertencias como una cadena
     * @return cadena con todas las advertencias separadas por comas
     */
    public String getWarningsAsString() {
        return String.join(", ", warnings);
    }
    
    // Getters y Setters
    public boolean isValid() {
        return valid;
    }
    
    public void setValid(boolean valid) {
        this.valid = valid;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public List<String> getErrors() {
        return errors;
    }
    
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
    
    public List<String> getWarnings() {
        return warnings;
    }
    
    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ValidationResult{");
        sb.append("valid=").append(valid);
        sb.append(", message='").append(message).append('\'');
        
        if (!errors.isEmpty()) {
            sb.append(", errors=").append(errors);
        }
        
        if (!warnings.isEmpty()) {
            sb.append(", warnings=").append(warnings);
        }
        
        sb.append('}');
        return sb.toString();
    }
}
