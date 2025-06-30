# AlgaSensors - Sistema de Monitoramento de Sensores

Sistema de monitoramento de sensores baseado em microserviços Java Spring Boot, desenvolvido para gerenciar dispositivos sensores e processar dados de temperatura.

## Arquitetura

Esta aplicação segue uma arquitetura de microserviços com separação clara de responsabilidades:

- **3 microserviços** Spring Boot independentes
- Arquitetura baseada em domínio (DDD)
- Uso de TSID (Time-Sorted Unique Identifier) para IDs únicos ordenados por tempo

## Microserviços

### 1. Device Management (`device-management`)
**Responsabilidade**: Gerenciamento de sensores

**Principais componentes**:
- `SensorController`: API REST para operações com sensores
- `Sensor`: Entidade de domínio principal
- `SensorRepository`: Camada de persistência
- `SensorMonitoringClient`: Cliente para comunicação com o serviço de monitoramento

### 2. Temperature Monitoring (`temperature-monitoring`)
**Responsabilidade**: Monitoramento de temperatura e sistema de alertas

**Principais componentes**:
- `TemperatureLogController`: Gerenciamento de logs de temperatura
- `SensorAlertController`: Sistema de alertas de sensores
- `SensorMonitoringController`: Status e controle de monitoramento
- **Entidades**: `TemperatureLog`, `SensorAlert`, `SensorMonitoring`

### 3. Temperature Processing (`temperature-processing`)
**Responsabilidade**: Processamento e análise de dados de temperatura

**Principais componentes**:
- `TemperatureProcessingController`: API para processamento de dados
- Utilitários para geração de IDs (UUIDV7, TSID)

## Tecnologias Utilizadas

- **Spring Boot** - Framework principal
- **Gradle** - Gerenciamento de dependências e build
- **JPA/Hibernate** - Persistência de dados
- **Jackson** - Serialização/deserialização JSON
- **TSID** - Identificadores únicos ordenados por tempo

## Estrutura do Projeto

```
algasensors/
├── microservices/
│   ├── device-management/     # Gerenciamento de sensores
│   ├── temperature-monitoring/ # Monitoramento e alertas
│   └── temperature-processing/ # Processamento de dados
└── README.md
```

## Executando os Serviços

Cada microserviço pode ser executado independentemente:

```bash
# Device Management
cd microservices/device-management
./gradlew bootRun

# Temperature Monitoring  
cd microservices/temperature-monitoring
./gradlew bootRun

# Temperature Processing
cd microservices/temperature-processing
./gradlew bootRun
```