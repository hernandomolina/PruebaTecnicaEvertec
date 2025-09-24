# Proyecto de Automatización de Pruebas - OrangeHRM

Este proyecto implementa automatización de pruebas para OrangeHRM usando **Java 17**, **Serenity BDD**, **JUnit 4** y **Selenium WebDriver**, siguiendo el patrón **Page Object Model (POM)**.

## 🏗️ Estructura del Proyecto

```
├── serenity.properties               # Configuración de Serenity BDD
├── pom.xml                          # Configuración de Maven
└── src/
    ├── main/
    │   └── java/                     # Código fuente principal (si es necesario)
    └── test/
        └── java/com/pruebatecnica/orangehrm/
            ├── pages/                # Page Objects
            │   ├── BasePage.java     # Página base con métodos comunes
            │   └── LoginPage.java    # Página de login (ejemplo)
            ├── tests/                # Clases de prueba
            │   ├── TestBase.java     # Clase base para todos los tests
            │   └── ExampleTest.java  # Test de ejemplo
            ├── runners/              # Test Runners
            │   └── TestRunner.java   # Runner principal para Cucumber
            └── utils/                # Utilidades
                ├── WebDriverFactory.java  # Factory para crear drivers
                └── Config.java            # Manejo de configuraciones
```

## 🚀 Tecnologías Utilizadas

- **Java 17**: Lenguaje de programación
- **Maven**: Herramienta de construcción y gestión de dependencias
- **Serenity BDD**: Framework para testing BDD con reportes avanzados
- **JUnit 4**: Framework de testing
- **Selenium WebDriver 4.28.0**: Automatización de navegadores web
- **WebDriverManager 5.6.2**: Gestión automática de drivers
- **AssertJ 3.25.1**: Librería de assertions más fluidas

## 📋 Requisitos Previos

- Java 17 o superior
- Maven 3.6 o superior
- Navegador web (Chrome, Firefox o Edge)

## 🛠️ Configuración del Proyecto

### 1. Clonar el repositorio
```bash
git clone <url-del-repositorio>
cd orangehrm-automation
```

### 2. Instalar dependencias
```bash
mvn clean install
```

### 3. Configurar navegador
El proyecto está configurado para usar Chrome por defecto. Para cambiar el navegador, edita el archivo `serenity.properties` en la raíz del proyecto:

```properties
# Para Chrome (por defecto)
webdriver.driver=chrome

# Para Firefox
webdriver.driver=firefox

# Para Edge
webdriver.driver=edge
```

## 🏃‍♂️ Ejecución de Tests

### Ejecutar todos los tests
```bash
mvn clean test
```

### Ejecutar tests con reportes de Serenity
```bash
mvn clean verify
```

### Ejecutar tests específicos
```bash
mvn clean test -Dtest=ExampleTest
```

### Ejecutar con navegador específico
```bash
mvn clean test -Dbrowser=firefox
```

## 📊 Reportes

Después de ejecutar los tests, los reportes se generan en:
- **Serenity Reports**: `target/site/serenity/index.html`
- **Cucumber Reports**: `target/cucumber-reports/`

## 🏛️ Arquitectura del Proyecto

### Page Object Model (POM)
- **BasePage**: Clase base con métodos comunes para todas las páginas
- **LoginPage**: Ejemplo de Page Object para la página de login
- Cada Page Object encapsula elementos y acciones de una página específica

### Configuración Centralizada
- **Config.java**: Manejo centralizado de configuraciones
- **serenity.properties**: Configuraciones de Serenity BDD (ubicado en la raíz del proyecto)
- **WebDriverFactory.java**: Factory para crear instancias de WebDriver

### Base de Tests
- **TestBase.java**: Clase base para todos los tests con setup/teardown
- **ExampleTest.java**: Ejemplo de test que extiende de TestBase

## 📝 Convenciones de Código

### Naming Conventions
- Clases: PascalCase (ej: `LoginPage`, `TestBase`)
- Métodos: camelCase (ej: `enterUsername`, `clickLoginButton`)
- Variables: camelCase (ej: `username`, `loginButton`)
- Constantes: UPPER_SNAKE_CASE (ej: `USERNAME_FIELD`, `LOGIN_BUTTON`)

### Anotaciones Serenity
- `@Step`: Para métodos que representan pasos de prueba
- `@Title`: Para dar títulos descriptivos a los tests
- `@RunWith(SerenityRunner.class)`: Para ejecutar tests con Serenity BDD

## 🔧 Configuraciones Importantes

### Timeouts
```properties
webdriver.timeouts.implicitlywait=10000
serenity.timeout.for.page.loads=30000
```

### Capturas de Pantalla
```properties
serenity.take.screenshots=FOR_EACH_ACTION
```

### URLs de la Aplicación
```properties
app.base.url=https://opensource-demo.orangehrmlive.com/
app.admin.username=Admin
app.admin.password=admin123
```

## 📚 Próximos Pasos

1. **Implementar más Page Objects** según las páginas de la aplicación
2. **Crear casos de prueba específicos** basados en los requerimientos
3. **Configurar CI/CD** para ejecución automática
4. **Agregar más configuraciones** según necesidades del proyecto
5. **Implementar manejo de datos de prueba** (Excel, JSON, etc.)

## 🤝 Contribución

1. Fork el proyecto
2. Crear una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

---

**Nota**: Este es un proyecto de estructura base. Los casos de prueba específicos deben implementarse según los requerimientos del proyecto.
