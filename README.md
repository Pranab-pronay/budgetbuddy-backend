# BudgetBuddy Backend

BudgetBuddy is a comprehensive personal budget management web application designed to help users effectively track and manage their financial activities. This repository contains the backend code, developed using Spring Boot.

## Features

- **User Authentication**: Secure authentication and authorization using JSON Web Tokens (JWT).
- **Income and Expense Management**: CRUD operations to manage income and expenses.
- **Category Management**: Users can categorize their financial records for better insights.
- **Data Persistence**: MySQL is used to ensure reliable and efficient data storage.
- **API Documentation**: Integrated with Swagger for comprehensive API documentation and testing.

## Technologies Used

- **Spring Boot**: Java-based framework to build the backend services.
- **MySQL**: Relational database management system used for data persistence.
- **JWT (JSON Web Tokens)**: For secure authentication and session management.
- **Swagger**: For interactive API documentation.
- **JUnit & Mockito**: Testing framework for unit tests and mocking dependencies.
- **Maven**: Project management and build tool.

## Setup Instructions

### Prerequisites

- JDK 11 or higher
- Maven 3.6+
- MySQL database

### Installation

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/Pranab-pronay/budgetbuddy-backend.git
   cd budgetbuddy-backend
   
2. **Database Configuration**:

- Create a MySQL database named budgetbuddy.
- Update the src/main/resources/application.properties file with your MySQL database credentials.
properties
    ```bash
   spring.datasource.url=jdbc:mysql://localhost:3306/budgetbuddy
   spring.datasource.username=your-username
   spring.datasource.password=your-password
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   ```
3. **Build the Project**:
   
   ```bash
   ./mvnw clean package
   ```

4. **Run the Application**:
   
   ```bash
   ./mvnw spring-boot:run
   ```