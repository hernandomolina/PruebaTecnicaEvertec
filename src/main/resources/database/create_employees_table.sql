-- Script SQL para crear la tabla de empleados
-- Este script debe ejecutarse en la base de datos MySQL antes de usar las validaciones

CREATE DATABASE IF NOT EXISTS orangehrm_test;
USE orangehrm_test;

CREATE TABLE IF NOT EXISTS employees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id VARCHAR(50) NOT NULL UNIQUE,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    middle_name VARCHAR(100),
    email VARCHAR(255),
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_employee_id (employee_id),
    INDEX idx_status (status),
    INDEX idx_created_at (created_at)
);

-- Insertar algunos datos de prueba (opcional)
INSERT INTO employees (employee_id, first_name, last_name, status) VALUES
('001', 'Juan', 'Pérez', 'ACTIVE'),
('002', 'María', 'González', 'ACTIVE'),
('003', 'Carlos', 'López', 'ACTIVE');

-- Verificar que la tabla se creó correctamente
SELECT * FROM employees;
