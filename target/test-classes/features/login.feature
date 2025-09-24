@smoke @login
Feature: Funcionalidad de inicio de sesión
  Como usuario de OrangeHRM
  Quiero poder iniciar sesión en el sistema
  Para poder acceder a las funcionalidades de la aplicación

  Background:
    Given que el usuario navega a la página de inicio de sesión de OrangeHRM

  @positive @valid-login
  Scenario: Inicio de sesión exitoso con credenciales válidas
    When el usuario ingresa un nombre de usuario y contraseña válidos
    And hace clic en el botón de inicio de sesión
    Then el usuario debería iniciar sesión exitosamente

  @negative @invalid-login
  Scenario: Fallo en el inicio de sesión con credenciales inválidas
    When el usuario ingresa un nombre de usuario inválido "usuario_invalido"
    And ingresa una contraseña inválida "contraseña_incorrecta"
    And hace clic en el botón de inicio de sesión
    Then el usuario debería ver un mensaje de error
    And debería permanecer en la página de inicio de sesión

  @negative @empty-credentials
  Scenario: Fallo en el inicio de sesión con credenciales vacías
    When el usuario ingresa un nombre de usuario vacío
    And ingresa una contraseña vacía
    And hace clic en el botón de inicio de sesión
    Then el usuario debería ver errores de validación
    And debería permanecer en la página de inicio de sesión