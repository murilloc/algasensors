# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Architecture

This is a microservices-based sensor monitoring system with three Spring Boot services:

### Microservices
- **device-management** (port 8080): Manages sensor devices and provides REST API for sensor operations
- **temperature-monitoring** (port 8082): Monitors temperature data and handles sensor alerts
- **temperature-processing** (port 8081): Processes temperature data via RabbitMQ messaging

### Key Technologies
- **Spring Boot 3.4.5** with Java 21
- **H2 Database** for persistence (file-based)
- **RabbitMQ** for messaging between services
- **Hypersistence TSID** for time-sorted unique identifiers
- **Lombok** for boilerplate code reduction

## Development Commands

### Build and Run
Each microservice can be built and run independently:
```bash
# Build a specific service
cd microservices/[service-name]
./gradlew build

# Run a specific service
./gradlew bootRun

# Run tests
./gradlew test
```

### Infrastructure
Start RabbitMQ with Docker Compose:
```bash
docker-compose up -d
```
- RabbitMQ Management UI: http://localhost:15672 (rabbitmq/rabbitmq)

### Service URLs
- Device Management: http://localhost:8080
- Temperature Processing: http://localhost:8081  
- Temperature Monitoring: http://localhost:8082

## Code Architecture

### Domain-Driven Design Structure
Each microservice follows DDD patterns:
- `domain/model/`: Entity classes and domain logic
- `domain/repository/`: Repository interfaces
- `api/controller/`: REST controllers
- `api/model/`: Request/response DTOs
- `api/config/`: Configuration classes

### Key Patterns
- **TSID Generation**: Uses hypersistence-tsid for unique identifiers
- **Jackson Configuration**: Custom serializers/deserializers for TSID
- **JPA Converters**: TSID to Long conversion for database storage
- **Web Converters**: String to TSID conversion for HTTP requests
- **Client Communication**: Declarative REST clients for inter-service communication

### Database Configuration
- All services use H2 file-based databases
- H2 console enabled for development
- Hibernate DDL auto-update enabled
- SQL logging enabled

### Messaging
- RabbitMQ integration in temperature-monitoring and temperature-processing
- Custom RabbitMQ configuration and initialization classes