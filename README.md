## 📈 Microservice Report: Stock Market Quotation Service

This report details the design and implementation of the **Stock Market Quotation Microservice**, a backend service designed to manage and retrieve stock market data, specifically company stock quotations.

---

### 1. 🎯 Service Overview

This microservice provides a robust API for **CRUD (Create, Read, Update, Delete)** operations on stock market quotation data. Its primary function is to store daily stock price information (Open, Close, Volume) for various companies and quickly retrieve the most recent price for a given company.

**Key Features:**

* **Quotation Management:** Add, delete, and retrieve specific stock quotations.
* **Current Price Retrieval:** Efficiently find the latest recorded closing price for any company.
* **RESTful API:** Provides standard HTTP endpoints for easy integration.

---

### 2. 🧱 Technical Architecture

The service follows a standard **Spring Boot** architecture, utilizing core components for data persistence, business logic, and API exposure.

#### 2.1. Data Model (`StockMarket` Entity)

The core data structure represents a single day's quotation for a stock.

| Field | Type | Description |
| :--- | :--- | :--- |
| `id` | `Long` | Primary key, auto-generated. |
| `date` | `Date` | The date of the quotation. |
| `openValue` | `Double` | The stock price at market open. |
| `closeValue` | `Double` | The stock price at market close (used as the current price). |
| `volume` | `Double` | The number of shares traded on that day. |
| `companyId` | `String` | Identifier for the company (e.g., ticker symbol). |

#### 2.2. Data Access Layer (`StockMarketRepository`)

The repository extends **`JpaRepository`** for standard CRUD operations and adds a custom query method:
* `findTopByCompanyIdOrderByDateDesc(String companyId)`: Used to retrieve the single **latest** quotation for a specific company by ordering the results by date in descending order.

* <img width="965" height="136" alt="image" src="https://github.com/user-attachments/assets/7641c1b0-fa16-410b-adae-a43812191002" />


#### 2.3. Business Logic Layer (`StockMarketService` & `StockMarketServiceImpl`)

This layer encapsulates the business rules and interactions with the data access layer.

* **`addQuotation(StockMarketDTO)`**: Saves a new quotation record.
* **`deleteQuotation(Long id)`**: Removes a quotation by its ID. Includes robust checks and logging.
* **`getCurrentStockPrice(String companyId)`**: Calls the custom repository method to fetch the latest closing price.
* **`getAllQuotations()`**: Retrieves all stored quotation records.
* **`getQuotationById(Long id)`**: Retrieves a specific quotation by ID, throwing an exception if not found.

<img width="934" height="195" alt="image" src="https://github.com/user-attachments/assets/9fc89054-e6b7-42ef-85ab-22e6d3f011e5" />
<img width="911" height="375" alt="image" src="https://github.com/user-attachments/assets/65c6aac2-d27a-4fed-96a2-80c8bb1debef" />



#### 2.4. API Layer (`StockMarketController`)

The controller exposes the service functionality as a **RESTful API** under the base path `/api/stock`.

| HTTP Method | Endpoint | Description |
| :--- | :--- | :--- |
| **POST** | `/api/stock` | Adds a new stock quotation. |
| **DELETE** | `/api/stock/{id}` | Deletes a quotation by ID. |
| **GET** | `/api/stock/current-price/{companyId}` | Retrieves the **latest closing price** for a company. |
| **GET** | `/api/stock` | Retrieves all stock quotations. |
| **GET** | `/api/stock/{id}` | Retrieves a specific quotation by ID. |

<img width="1071" height="401" alt="image" src="https://github.com/user-attachments/assets/3d47b00c-18d8-43d7-a2f6-eae82f3f4da0" />
<img width="1040" height="800" alt="image" src="https://github.com/user-attachments/assets/0a18caa4-8db9-41d3-b1c3-8edf6ae93e56" />


---

### 3. 🖥️ Service Screens and Interactions

This section outlines potential user interfaces or API interactions for the service.

<img width="479" height="686" alt="image" src="https://github.com/user-attachments/assets/4c670206-ec48-4a5c-9fd4-f5970d4d1141" />



#### 3.1. Add New Quotation Screen / API Request Body

This shows the required fields for adding a new stock quotation.
<img width="363" height="175" alt="image" src="https://github.com/user-attachments/assets/4b10367a-1a31-44d8-a12c-189f4f7975ba" />
<img width="513" height="254" alt="image" src="https://github.com/user-attachments/assets/b6f93bb3-5906-4207-bc56-f40679059688" />



**Placeholder: UI for Adding a New Quotation**


#### 3.2. Retrieve All Quotations / Data Table

This demonstrates how a list of all quotations might be displayed or the structure of the API response.

<img width="583" height="533" alt="image" src="https://github.com/user-attachments/assets/8522e4f7-884e-4767-adf6-c99a87fe75e9" />


**Placeholder: Data Table Showing All Quotations**


-----------------------------------



## ☁️ Microservice Report: API Gateway Service

### 1\. 🎯 Service Overview

This service is configured as an **API Gateway**, typically using **Spring Cloud Gateway**. Its primary role is to act as a single entry point for all client requests, routing them to the appropriate downstream microservices (like the Stock Market Service previously discussed). It also handles cross-cutting concerns like service discovery integration.

**Key Responsibilities:**

  * **Routing:** Dynamically routes requests to other services.
  * **Load Balancing:** Works with Eureka for client-side load balancing.
  * **Security & Filtering (Implicit):** Acts as a central point for applying global filters (though no filters are explicitly defined in the provided code).
  * **Monitoring:** Exposes management endpoints via Actuator.

-----

### 2\. ⚙️ Application Configuration Analysis

| Configuration Property | Value | Purpose & Implication |
| :--- | :--- | :--- |
| **`spring.application.name`** | `gateway-service` | Defines the service ID registered with the Eureka server. |
| **`server.port`** | `8888` | The port on which the Gateway service will listen for incoming requests. |
| **`spring.cloud.config.enabled`** | `false` | Disables connecting to a Spring Cloud Config Server for external configuration. Configuration is local (`application.properties`). |
| **`spring.cloud.discovery.enabled`** | `true` | **Enables Eureka client** functionality, allowing the Gateway to register itself and discover other services. |
| **`eureka.client.service-url.defaultZone`** | `http://localhost:8761/eureka` | Specifies the address of the **Eureka Discovery Server**. |
| **`eureka.instance.prefer-ip-address`** | `true` | Instructs the Gateway to register its IP address instead of its hostname with Eureka. |
| **`spring.cloud.gateway.server.webflux.discovery.locator.enabled`** | `true` | **Crucial setting:** Enables the **Discovery Client Route Definition Locator**. This automatically creates routes for every service registered in Eureka. |
| **`spring.cloud.gateway.server.webflux.discovery.locator.lower-case-service-id`** | `true` | Ensures service IDs are converted to lowercase when creating dynamic routes (e.g., a service named `USER-SERVICE` will be reachable via `/user-service/**`). |
| **`management.endpoints.web.exposure.include`** | `*` | **Enables all Spring Boot Actuator endpoints** (like `/actuator/health`, `/actuator/info`, `/actuator/gateway`) for monitoring and operational inspection. |

-----

### 3\. 🛠️ Code Component Analysis

#### 3.1. Main Application Class (`GatewayServiceApplication`)

The class defines the service entry point and a key configuration bean.

 <img width="1212" height="407" alt="image" src="https://github.com/user-attachments/assets/abd205ce-3c42-4f02-b175-ada3a4d70aa7" />

    This bean is the **heart of dynamic routing**. It programmatically creates the component responsible for listening to the `ReactiveDiscoveryClient` (which fetches service instances from Eureka) and transforming the discovered service IDs into active Gateway routes. This aligns directly with the `spring.cloud.gateway.server.webflux.discovery.locator.enabled=true` property.

-----

### 4\. 🖥️ Operational Screens and Monitoring

The service is primarily monitored using its Actuator endpoints and its interaction with the Eureka server.

#### 4.1. Eureka Server Dashboard View

This screen represents the central registry where the Gateway and all other microservices (like the Stock Market service) are registered and visible.
<img width="1539" height="116" alt="image" src="https://github.com/user-attachments/assets/917c06a0-01c1-4bc4-8f5a-f25ae1911cdd" />


**Placeholder: Eureka Dashboard Showing Registered Services**

#### 4.2. Gateway Actuator Routes Endpoint

This endpoint (`/actuator/gateway/routes`) shows the routes currently active and managed by the Gateway, including the dynamic ones created by the Discovery Locator.

http://localhost:8888/stock-service/api/stock

<img width="430" height="685" alt="image" src="https://github.com/user-attachments/assets/1e8c116b-dc36-4f9a-af2f-f0dbbd0487b4" />



#### 4.3. Health Check Monitoring

A crucial screen for monitoring the service's operational status and connectivity to the Discovery Server.

http://localhost:8888/actuator/health

<img width="168" height="118" alt="image" src="https://github.com/user-attachments/assets/15062c33-8a34-4711-8a01-85da8504a3d6" />



Microservice Report: Chatbot 

<img width="977" height="517" alt="image" src="https://github.com/user-attachments/assets/a060573c-bfa1-4184-a92f-937ac11afab0" />

<img width="1233" height="678" alt="image" src="https://github.com/user-attachments/assets/3ff8e940-f4d6-4ff7-baf3-31a2cdf32de7" />
<img width="1011" height="763" alt="image" src="https://github.com/user-attachments/assets/d4ff2453-c597-4295-9659-2224507f683c" />






