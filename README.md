# Digital Banking System — Microservices
## YouTube Series: Building Digital Banking System from Scratch

---

## Services Overview

| Service | Port | Responsibility |
|---|---|---|
| api-gateway | 8080 | Single entry point, Rate limiting |
| account-service | 8081 | Account management, Balance |
| transaction-service | 8082 | Money transfers, Transaction history |
| payment-service | 8083 | Razorpay integration, Webhooks |
| fraud-detection-service | 8084 | Real time fraud detection via Redis |
| notification-service | 8085 | Transaction and fraud alerts |

---

## Architecture Flow

```
User → API Gateway (rate limiting)
             ↓
    Account / Transaction / Payment Service
             ↓
        Apache Kafka
             ↓
    ┌────────────────────────┐
    │                        │
Fraud Detection      Notification Service
(Redis patterns)     (alerts via email/SMS)
    │
Account Service
(block if fraud)
```

---

## Kafka Topics

| Topic | Publisher | Consumer |
|---|---|---|
| transaction.initiated | Transaction Service | Fraud Detection |
| fraud.check.result | Fraud Detection | Transaction Service |
| transaction.completed | Transaction Service | Account Service, Notification |
| fraud.detected | Fraud Detection | Account Service, Notification |
| payment.completed | Payment Service | Notification |

---

## How To Run

### Step 1: Start Infrastructure
```bash
docker-compose up -d
```

### Step 2: Start All Services
```bash
# Terminal 1
cd account-service && mvn spring-boot:run

# Terminal 2
cd transaction-service && mvn spring-boot:run

# Terminal 3
cd payment-service && mvn spring-boot:run

# Terminal 4
cd fraud-detection-service && mvn spring-boot:run

# Terminal 5
cd notification-service && mvn spring-boot:run

# Terminal 6
cd api-gateway && mvn spring-boot:run
```

---

