# POS API - Sistema de Punto de Venta

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.java.com)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2+-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.8+-blue.svg)](https://maven.apache.org)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16+-blue.svg)](https://www.postgresql.org)
[![Docker](https://img.shields.io/badge/Docker-20+-blue.svg)](https://www.docker.com)
[![SendGrid](https://img.shields.io/badge/SendGrid-Email-blue.svg)](https://sendgrid.com)
[![AWS SES](https://img.shields.io/badge/AWS%20SES-Email-orange.svg?logo=amazon-aws&logoColor=white)](https://aws.amazon.com/ses/)
[![SMTP](https://img.shields.io/badge/SMTP-Email-gray.svg)](https://en.wikipedia.org/wiki/Simple_Mail_Transfer_Protocol)

## 📋 Descripción

API REST desarrollada en Spring Boot para gestión de punto de venta (POS). Sistema sencillo y funcional que permite la administración completa de clientes, productos y servicios, con generación automática de facturas y envío por correo electrónico.

## ✨ Características Principales

- 👥 **Gestión de Clientes**: CRUD completo para administración de clientes
- 🛍️ **Items Flexibles**:
    - **Productos**: Con control de inventario automático
    - **Servicios**: Sin manejo de inventario
- 🧾 **Sistema de Facturación**: Generación y gestión de facturas
- 📧 **Envío Automático**: Facturas enviadas por email con plantillas HTML profesionales
- 🖼️ **Logo Empresarial**: Soporte para logo de empresa en correos electrónicos
- 🔐 **Seguridad JWT**: Autenticación y autorización con Spring Security
- 🐳 **Docker Ready**: Completamente dockerizado con PostgreSQL
- ⚡ **Inicialización Automática**: Base de datos configurada automáticamente

## 🚀 Tecnologías Utilizadas

- **Backend**: Spring Boot 3.2+, Java 17+, Maven
- **Security**: Spring Security + JWT Authentication
- **Base de Datos**: PostgreSQL 16
- **Email Service**: SendGrid API / AWS SES API / SMTP (Cualquiera de los tres se puede utilizar)
- **Template Engine**: Thymeleaf (para plantillas de email)
- **Containerización**: Docker & Docker Compose
- **ORM**: Spring Data JPA / Hibernate

## 🛠️ Instalación y Configuración

### Prerrequisitos

- Java 17+
- Maven 3.8+
- Docker & Docker Compose
- Cuenta SendGrid, AWS SES o servidor SMTP (para envío de correos)

### 1. Clonar el Repositorio

```bash
git clone https://github.com/Anthony3064/pos-backend.git
cd pos-api
```

### 2. Configurar Variables de Entorno

Crear archivo `.env` en el directorio raíz con la siguiente estructura:

```env
# DATABASE
DB_HOST=localhost
DB_EXTERNAL_PORT=5433
DB_NAME=pos_database
DB_SCHEMA=pos_schema
DB_USER=pos_user
DB_PASSWORD=SecurePassword123!

# SECURITY
SECRET_KEY=mySecretJWTKeyForPOSApplication2024!

# EMAIL SENDER Para SENDGRID
SEND_GRID_API_KEY=SG.xxxxxxxxxxxxxxxx.xxxxxxxxxxxxxxxxxxxxxxxxxx
SEND_GRID_FROM_EMAIL=noreply@miempresa.com (correo verificado en sendgrid)

# EMAIL SENDER Para AWS SES
AWS_ACCESS_KEY_ID=El ID de la llave de acceso de AWS
AWS_SECRET_ACCESS_KEY=clave secreta de AWS
AWS_REGION=Región del servicio de AWS por ejemplo: us-east-1
AWS_FROM_EMAIL=noreply@miempresa.com (correo verificado en AWS SES)

# EMAIL SENDER Para SMTP
SMTP_HOST=smtp.miproveedor.com
SMTP_PORT=587
SMTP_USERNAME=tu_usuario@miproveedor.com
SMTP_PASSWORD=tu_contraseña_smtp
SMTP_FROM=noreply@miempresa.com
SMTP_SECURE=false

# COMPANY INFO
COMPANY_NAME=Mi Empresa S.A.
COMPANY_ADDRESS=Av. Principal 123, Ciudad, País
COMPANY_PHONE=+506 2222-3333
COMPANY_EMAIL=info@miempresa.com

# APPLICATION -> Lo puedes personalizar para usar el contexto que gustes
CONTEXT_PATH_URL=/api 
```

### 3. Configurar Logo de Empresa (Opcional)

Para mostrar el logo de tu empresa en los correos de factura:

1. Coloca tu logo en: `src/main/resources/static/logo.png`
2. Formato recomendado: PNG, máximo 200x100px
3. Si no existe el archivo, los correos se envían sin logo

### 4. Ejecutar con Docker Compose

```bash
# Construir y ejecutar todos los servicios
docker-compose up -d

# Ver logs en tiempo real
docker-compose logs -f

# Verificar estado de contenedores
docker-compose ps

# Detener todos los servicios
docker-compose down
```

**🗄️ Importante**: Al ejecutar por primera vez, Docker ejecutará automáticamente el script `sql/init.sql` para crear la base de datos, esquemas y tablas necesarias. Debes asegurarte que el esquema y la base de datos sean coherentes con lo que esta en el archivo del .env y docker-compose.yml.

### 5. Ejecutar en Desarrollo (Sin Docker)

```bash
# Instalar dependencias
mvn clean install

# Ejecutar aplicación
mvn spring-boot:run

# O ejecutar el JAR compilado
java -jar target/pos-api-1.0.0.jar
```
## 📧 Sistema de Emails

### Configuración SendGrid

1. **Crear cuenta**: [SendGrid](https://sendgrid.com)
2. **Generar API Key**: Settings > API Keys > Create API Key
3. **Verificar dominio**: Para evitar spam filters
4. **Configurar** en `.env`:

```env
SEND_GRID_API_KEY=tu_api_key_aqui
SEND_GRID_FROM_EMAIL=tu_email_verificado@dominio.com
```

o 

### Configuración AWS SES

1. **Crear cuenta**: [AWS SES](https://aws.amazon.com/ses/)
2. **Completar todos los pasos indicados:** Verificación de domio, correo, etc.
3. **Generar los API Key correspondientes con:**: [IAM](https://aws.amazon.com/iam/)
4. **Configurar** en `.env`:

```env
AWS_ACCESS_KEY_ID=El ID de la llave de acceso de AWS
AWS_SECRET_ACCESS_KEY=clave secreta de AWS
AWS_REGION=Región del servicio de AWS por ejemplo: us-east-1
AWS_FROM_EMAIL=noreply@miempresa.com (correo verificado en AWS SES)
```

o

### Configuración SMTP

Opción recomendada si tu VPS o servidor bloquea los puertos de servicios externos. Funciona con cualquier proveedor SMTP estándar (Gmail, Outlook, Zoho, servidor propio, etc.).

1. **Obtener credenciales SMTP** de tu proveedor de correo
2. **Configurar** en `.env`:

```env
SMTP_HOST=smtp.miproveedor.com
SMTP_PORT=587
SMTP_USERNAME=tu_usuario@miproveedor.com
SMTP_PASSWORD=tu_contraseña_smtp
SMTP_FROM=noreply@miempresa.com
SMTP_SECURE=false
```

> **Nota sobre `SMTP_SECURE`:**
> - `false` (default) → usa **STARTTLS** en el puerto `587` (recomendado)
> - `true` → usa **SSL/TLS** directo en el puerto `465`

### Cambiar el proveedor activo

El proveedor de correo se configura directamente en `EmailSenderService.java`:

```java
// Opciones disponibles: EmailSenderProvider.SENDGRID | AWS_SES | SMTP
this.provider = EmailSenderProvider.SMTP;
```

### Plantilla de Factura

La plantilla Thymeleaf (`templates/invoice.html`) incluye:

- ✅ Logo de empresa (si existe `static/logo.png`)
- ✅ Información de la empresa
- ✅ Datos del cliente
- ✅ Detalles de productos/servicios
- ✅ Cálculos automáticos (subtotal, total)
- ✅ Diseño responsive para email

## 🏢 Lógica de Negocio

### Items: Productos vs Servicios

```json
// Producto (con inventario)
{
  "id": "1e7f5a76-a05b-4558-b294-fb73f65232ad",
  "code": "SERV-008",
  "name": "Trenzas",
  "description": "Trenzas africanas: estilo y autenticidad.",
  "type": "SERVICE",
  "price": 8000.00,
  "active": true,
}
// Servicio (sin inventario)
{
    "id": "1cf64bca-5a15-4a5a-9da3-4a55ea5abfaa",
    "code": "PROD-001",
    "name": "Fijador de cabello",
    "description": "Fijador de cabello con alta fijación.",
    "type": "PRODUCT",
    "price": 6000.00,
    "imageUrl": null,
    "active": true,
    "inventory": {
        "id": "0c92bba9-cb3d-4fd6-8b65-3872480f02f0",
        "stock": 2,
        "minimumStock": 0,
        "maxStock": 0
    },
}
```

### Proceso de Facturación

1. **Crear factura** → Seleccionar cliente e items (Productos y servicios) y método de pago.
2. **Validar stock** → Solo para productos
3. **Calcular totales** → Subtotal
4. **Generar factura** → Guardar en base de datos
5. **Enviar email** → Plantilla HTML + logo empresa
6. **Actualizar inventario** → Reducir stock de productos

## 🗄️ Base de Datos

### Inicialización Automática

El archivo `sql/init.sql` contiene lo mínimo necesario para que el proyecto se ejecute.


### Estructura de Tablas

- **clients**: Información de clientes
- **items**: Productos y servicios
- **invoices**: Cabecera de facturas
- **invoice_details**: Detalle de items por factura
- **users**: Usuarios del sistema (autenticación).

**Nota:** El usuario administrador por defecto será **admin** y la contraseña es *173946.$$* si se desea utilizar otra
se deberá generar tomando el encoder con "BCryptPasswordEncoder". 

### El sql que insertar el usuario administrador por defecto.
```
INSERT INTO pos.users
(user_id, created_at, updated_at, active, complete_name, "password", user_role, username, created_by, updated_by)
VALUES('51c06a69-2ad3-4192-81be-9b8cccbf82e0', '2025-09-01 18:13:43.227', '2025-09-01 18:13:43.227', true, 'Usuario Administrador', '$2a$10$UQDcJ1WrgN0I1OUyjpRLZOK3fKpo4UFnnDZUkg13bJpb71F2vWc9S', 'ADMIN', 'admin', 'SYSTEM', 'SYSTEM');
```

## 🔒 Seguridad

### JWT Authentication

```bash
# Header de autorización requerido
Authorization: Bearer <jwt_token>

# Ejemplo de login
curl -X POST http://localhost:8080/api/v1/auth/authenticate \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"password"}'
```

### Configuración de Seguridad

- ✅ Endpoints públicos: `/v1/auth/**`
- 🔒 Endpoints protegidos: Todos los demás
- ⏰ Token expiration: Configurable
- 🔄 Refresh token: Disponible

## 🤝 Contribuir

1. Fork el proyecto
2. Crear feature branch (`git checkout -b feature/nueva-funcionalidad`)
3. Commit cambios (`git commit -m 'Agregar gestión de inventario'`)
4. Push al branch (`git push origin feature/nueva-funcionalidad`)
5. Crear Pull Request

## 📝 Notas Importantes

- 🖼️ **Logo**: El archivo `static/logo.png` es opcional pero recomendado
- 📧 **SendGrid**: Requiere verificación de dominio para producción
- 📧 **AWS SES**: Requiere verificación de dominio para producción
- 📧 **SMTP**: Compatible con cualquier proveedor SMTP estándar; usar `SMTP_SECURE=true` para SSL en puerto 465
- 🗄️ **Base de datos**: Se inicializa automáticamente en primer arranque
- 🔒 **JWT**: Tokens expiran según configuración (default: 15 minutos)
- 🔒 **Refresh Token**: El refresh token expira según configuración (default: 7 días)
- 📦 **Inventario**: Solo se controla para items tipo "PRODUCT"

## 📝 Licencia

Este proyecto está bajo la licencia MIT. Ver [LICENSE](LICENSE) para más detalles.

## 👨‍💻 Autor

**Anthony Flores Boza**
- GitHub: [@anthonyfloresdev](https://github.com/anthonyfloresdev)
- LinkedIn: [Anthony Flores Boza](www.linkedin.com/in/anthony-flores-boza-1b0533212)
- Email: contacto@anthonyflores.dev

---

⭐ ¡Si te gusta este proyecto, dale una estrella en GitHub!
