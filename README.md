# Prueba Técnica Evertec - Automatización de Pruebas OrangeHRM

## 📋 Descripción del Proyecto

Este proyecto implementa automatización de pruebas para el sistema OrangeHRM utilizando **Serenity BDD** con **Cucumber** y **Selenium WebDriver**. El framework está diseñado para ejecutar pruebas en paralelo en múltiples navegadores (Chrome y Edge).

## 🚀 Tecnologías Utilizadas

- **Java 17**
- **Maven 3.9.8**
- **Serenity BDD 3.9.8**
- **Cucumber 7.17.0**
- **Selenium WebDriver 4.10.0**
- **JUnit 5.10.0**
- **WebDriverManager 5.6.2**

## 📁 Estructura del Proyecto

```
src/
├── main/java/
└── test/
    ├── java/com/pruebatecnica/orangehrm/
    │   ├── hooks/
    │   │   └── TestHooks.java
    │   ├── pages/
    │   │   ├── AddEmployeePage.java
    │   │   ├── EmployeeDetailsPage.java
    │   │   ├── EmployeeListPage.java
    │   │   ├── LoginPage.java
    │   │   └── PimPage.java
    │   ├── runners/
    │   │   ├── ChromeTestRunner.java
    │   │   └── EdgeTestRunner.java
    │   ├── stepdefinitions/
    │   │   ├── EmployeeStepDefinitions.java
    │   │   └── LoginStepDefinitions.java
    │   └── utils/
    │       └── Config.java
    └── resources/
        └── features/
            ├── employee-abm.feature
            └── login.feature
```

## 🧪 Casos de Prueba Implementados

### 🔐 Funcionalidad de Login
- ✅ **Login exitoso** con credenciales válidas
- ❌ **Login fallido** con credenciales inválidas
- ❌ **Login fallido** con credenciales vacías

### 👥 ABM de Empleados
- ✅ **Crear empleado** exitosamente
- ✅ **Modificar empleado** existente
- ✅ **Eliminar empleado** existente

## 🏃‍♂️ Ejecución de Pruebas

### Prerequisitos
- Java 17 o superior
- Maven 3.6 o superior
- Navegadores Chrome y Edge instalados

### Comandos de Ejecución

#### Ejecutar todas las pruebas en paralelo
```bash
mvn clean verify
```

#### Ejecutar solo pruebas de Chrome
```bash
mvn clean verify "-Dtest=ChromeTestRunner" "-Dserenity.browser=chrome"
```

#### Ejecutar solo pruebas de Edge
```bash
mvn clean verify "-Dtest=EdgeTestRunner" "-Dserenity.browser=edge"
```

#### Ejecutar pruebas específicas por tags
```bash
# Solo pruebas de login
mvn clean verify "-Dcucumber.filter.tags=@login"

# Solo pruebas de ABM
mvn clean verify "-Dcucumber.filter.tags=@abm"

# Solo pruebas de crear empleado
mvn clean verify "-Dcucumber.filter.tags=@create"

# Solo pruebas de modificar empleado
mvn clean verify "-Dcucumber.filter.tags=@modify"

# Solo pruebas de eliminar empleado
mvn clean verify "-Dcucumber.filter.tags=@delete"
```

## 📊 Reportes

Los reportes de Serenity BDD se generan automáticamente en:
- **Reporte HTML:** `target/site/serenity/index.html`
- **Reportes JSON:** `target/cucumber-reports/`
- **Reportes XML:** `target/failsafe-reports/`

## ⚙️ Configuración

### Navegadores Soportados
- **Chrome:** Descarga automática del driver via WebDriverManager
- **Edge:** Driver local configurado en `C:\Drivers\Edge\msedgedriver.exe`

### Configuración de Paralelización
- **Threads:** Configurado para ejecución en paralelo
- **Navegadores:** Chrome y Edge ejecutándose simultáneamente
- **Reportes:** Separados por navegador

## 🔧 Características Técnicas

### Page Object Model (POM)
- Implementación del patrón Page Object Model
- Separación clara entre lógica de negocio y elementos de UI
- Reutilización de código entre diferentes pruebas

### BDD (Behavior-Driven Development)
- Escenarios escritos en Gherkin
- Step definitions en Java
- Documentación viva y legible

### Manejo de Datos
- Generación automática de IDs de empleados
- Captura dinámica de IDs generados por el sistema
- Persistencia de datos entre steps

### Esperas Explícitas
- Reemplazo de `Thread.sleep()` por esperas explícitas
- Uso de `WebElementFacade` para mejor manejo de elementos
- Timeouts configurables por elemento

## 📝 Notas de Desarrollo

### Optimizaciones Implementadas
- ✅ Eliminación de métodos no utilizados
- ✅ Limpieza de código redundante
- ✅ Optimización de navegación entre páginas
- ✅ Eliminación de emojis en logs para mejor legibilidad

### Patrones de Diseño
- **Page Object Model:** Para manejo de páginas web
- **Step Definitions:** Para mapeo de pasos Gherkin
- **Hooks:** Para configuración y limpieza de pruebas
- **Runners:** Para ejecución de diferentes suites de pruebas

## 🐛 Solución de Problemas

### Problemas Comunes
1. **Driver no encontrado:** Verificar que los navegadores estén instalados
2. **Timeouts:** Ajustar timeouts en `serenity.properties`
3. **Elementos no encontrados:** Verificar XPath y esperas explícitas

### Logs de Depuración
- Los logs se generan en consola durante la ejecución
- Información detallada de cada paso ejecutado
- Identificación clara de errores y warnings

## 📞 Contacto

Desarrollado como parte de la prueba técnica para Evertec.

---

**Versión:** 1.0.0  
**Última actualización:** Septiembre 2025