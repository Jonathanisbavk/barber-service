# barber-service

Proyecto Spring Boot con API REST en memoria y un saludo inicial para frontend usando LangChain4j.

## Requisitos

- Java 21
- Gradle Wrapper (`gradlew.bat`)
- (Opcional) API Key de OpenAI para saludo por LLM

## Configuracion opcional de OpenAI

En Windows PowerShell:

```powershell
$env:OPENAI_API_KEY="tu_api_key"
```

Y agrega esta propiedad en `src/main/resources/application.properties` solo si quieres enlazar por archivo:

```properties
openai.api.key=${OPENAI_API_KEY:}
```

Si no hay API key, el backend responde un saludo por defecto.

## Ejecutar

```powershell
Set-Location "E:\Portafolio\barber-service"
.\gradlew.bat bootRun
```

Frontend:

- Abrir `http://localhost:8080/`
- Al cargar la pagina, consulta `GET /api/saludo/inicio?nombre=cliente`

## Endpoint de saludo

- `GET /api/saludo/inicio?nombre=Juan`

