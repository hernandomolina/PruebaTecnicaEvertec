@abm @employee
Feature: ABM de Empleados en OrangeHRM
  Como administrador del sistema
  Quiero gestionar empleados (crear, modificar, eliminar)
  Para mantener actualizada la información del personal

  Background:
    Given que el usuario está logueado en OrangeHRM como administrador

  @create @positive
  Scenario: Crear nuevo empleado exitosamente
    When el usuario navega al módulo PIM
    And hace clic en el botón Add Employee
    And ingresa el nombre "Juan"
    And ingresa el apellido "Pérez"
    And ingresa el ID generico
    And hace clic en el botón Save
    Then el empleado debería crearse exitosamente

  @modify @positive
  Scenario: Modificar empleado existente
    Given que existe un empleado con nombre "Juan" y apellido "Pérez"
    When el usuario busca el empleado por ID
    And hace clic en el primer empleado de la lista
    And modifica el nombre a "Carlos"
    And modifica el apellido a "García"
    And hace clic en el botón Save para guardar cambios
    Then el empleado debería actualizarse exitosamente

  @delete @positive
  Scenario: Eliminar empleado existente
    Given que existe un empleado con nombre "Juan" y apellido "Pérez"
    When el usuario busca el empleado por ID
    And hace clic en el botón eliminar del empleado encontrado
    And confirma la eliminación en la alerta
    Then el empleado debería eliminarse exitosamente