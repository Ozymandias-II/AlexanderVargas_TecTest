# Alexander Vargas Test - Microservices

### Prerequisites

- [Docker](https://www.docker.com/get-started) (v20.10.0+)
- [Docker Compose](https://docs.docker.com/compose/install/) (v2.5.0+)
- Java 17+ ([OpenJDK recommended](https://adoptium.net/))
- [Postman](https://www.postman.com/downloads/) (v11.34.5+ for API testing)

### Deployment
```bash
# Clone the repository
git clone https://github.com/your-username/banking-system.git
cd banking-system

# Build and start containers
docker-compose up -d --build

# Verify services
docker-compose ps

###API Documentation
#Postman Collection:

    - Import BankingSystem.postman_collection.json to test all endpoints

    - Contains pre-configured requests with examples

#Key Endpoints:

    - POST /api/clients - Register new customer

    - GET /api/accounts/{id} - Get account details

    - POST /api/transactions - Process financial transactions