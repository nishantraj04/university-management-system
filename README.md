# University Management System

A robust, menu-driven Command Line Interface (CLI) application built using **Java** and **MySQL** to manage university academic and administrative operations.

---

## 📌 Overview

This project integrates a Java frontend with a MySQL relational database using JDBC. It enforces strict database constraints, maintains data integrity, and performs automated financial calculations for instructor salary management.

---

## 🚀 Features

### 🔹 CRUD Operations
- Manage Student and Instructor records
- Add, update, delete, and retrieve data
- Strict validation for fixed 5-character IDs

### 🔹 Relational Data Retrieval
- Advanced SQL `JOIN` queries
- Fetch:
  - Student enrollments
  - Instructor assignments
  - Course-section relationships

### 🔹 Financial Processing
- Automatically adds:
  - **15% HRA** to instructor salaries
- Automatically deducts:
  - **20% TDS** tax at source

### 🔹 Security & Optimization
- Uses `PreparedStatement`
- Prevents SQL Injection
- Improves query efficiency and security

---

## 🛠️ Tech Stack

| Technology | Purpose |
|------------|---------|
| Java (JDK) | Core application development |
| MySQL Server 8.0 | Relational database |
| JDBC API | Java-Database connectivity |
| MySQL Connector/J v9.7.0 | JDBC driver |

---

## 🗄️ Database Architecture

The system uses a constrained 9-table relational schema:

- `Department`
- `Instructor`
- `Student`
- `Course`
- `Section`
- `Takes`
- `Teaches`
- `Classroom`
- `Advisor`

### Constraints Implemented
- `CHECK (Budget > 0)`
- `CHECK (Salary > 20000)`
- Foreign key constraints
- Referential integrity
- Fixed-length IDs

---

# ⚙️ Setup & Installation

## 1️⃣ Database Configuration

1. Open MySQL Workbench
2. Execute the schema SQL script
3. Create and verify the database:

```sql
CREATE DATABASE university;
```

4. Ensure all tables and mock data are loaded successfully

---

## 2️⃣ Application Setup

### Clone Repository

```bash
git clone <YOUR_REPOSITORY_URL>
```

### Place JDBC Driver

Ensure the following file exists in the project root directory:

```text
mysql-connector-j-9.7.0.jar
```

alongside:

```text
jdbcproject.java
```

### Configure Database Credentials

Open `jdbcproject.java` and update:

```java
String url = "jdbc:mysql://localhost:3306/university";
String user = "root";
String password = "YOUR_PASSWORD_HERE";
```

---

# ▶️ Execution

## Compile the Java Program

```bash
javac jdbcproject.java
```

## Run the Application (Windows)

```bash
java -cp ".;mysql-connector-j-9.7.0.jar" jdbcproject
```

## Run the Application (Linux/macOS)

```bash
java -cp ".:mysql-connector-j-9.7.0.jar" jdbcproject
```

---

# 📚 Core Concepts Implemented

- JDBC Connectivity
- Relational Database Design
- SQL Constraints
- Prepared Statements
- Dynamic Salary Processing
- Data Validation
- SQL JOIN Operations
- CLI Application Architecture

---

# 📈 Future Improvements

- GUI using JavaFX or Swing
- Authentication System
- Attendance Management
- Grade Management Module
- REST API Integration
- Docker Deployment

---

# 👨‍💻 Author

**Nishant Raj**  
Pre-Final Year CSE Undergraduate  
Full Stack Designer
