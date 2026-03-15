# Company Z - Employee Management System

## SWE 3350 Group Project

A Java Swing desktop application for managing employee data, payroll, and reports using MySQL.

---

## Prerequisites

- **Java 8+** (JDK)
- **MySQL 8.0+** running locally on port 3306
- MySQL root credentials: `root` / `root` (can be changed in `src/com/companyz/ems/dao/DatabaseConnection.java`)

## Setup

### 1. Database Setup

Run the SQL scripts in order against your MySQL server:

```bash
mysql -u root -p < sql/schema.sql
mysql -u root -p < sql/seed_data.sql
```

This creates the `employeeData` database with all tables, indexes, relationships, and 10 sample employees.

### 2. Compile

```bash
./compile.sh
```

Or manually:

```bash
mkdir -p out
find src -name "*.java" > /tmp/sources.txt
javac -cp "src:lib/*" -d out @/tmp/sources.txt
```

### 3. Run the Application

```bash
java -cp "out:lib/*" com.companyz.ems.Main
```

### 4. Run Tests

```bash
java -cp "out:lib/*" com.companyz.ems.test.TestRunner
```

---

## Login Credentials

| Username | Password   | Role       |
|----------|------------|------------|
| admin    | admin123   | HR Admin   |
| jsmith   | password   | Employee   |

---

## Features

### HR Admin
- Search employees by name, DOB, SSN, or employee ID
- Add, edit, and delete employees
- Update salaries by percentage for employees in a salary range
- Reports: total pay by job title, total pay by division, new hires by date range

### General Employee
- Search and view employee data (read-only)
- View personal pay statement history

---

## Project Structure

```
src/com/companyz/ems/
├── Main.java                  # Application entry point
├── model/                     # POJOs (Employee, Address, Payroll, etc.)
├── dao/                       # Data Access Objects (JDBC)
├── service/                   # Business logic (Auth, Employee, Payroll, Salary)
├── ui/                        # Swing GUI panels
└── test/                      # Test suite (3 test cases)

sql/
├── schema.sql                 # Database schema (tables, FKs, indexes)
└── seed_data.sql              # Sample data (50 states, 10 employees, payroll)

lib/
└── mysql-connector-j-8.3.0.jar
```

---

## Database Schema

10 tables normalized to 3NF:

- `employees` — core employee data
- `addresses` — shared addresses (normalized, avoids duplicates for roommates)
- `cities`, `states` — lookup tables
- `division`, `employee_division` — employee-division assignments
- `job_titles`, `employee_job_titles` — employee-job title assignments
- `payroll` — pay records with full deduction breakdown
- `users` — login accounts with SHA-256 hashed passwords

### Key Relationships
- `employees.empID` → `employee_division.empID`, `payroll.empID`, `employee_job_titles.empID`
- `addresses.addressID` → `employees.addressID`
- `division.divID` → `employee_division.divID`
- `job_titles.job_titleID` → `employee_job_titles.job_titleID`

---

## Test Cases

1. **Employee Update** — Modifies an employee's name and email, verifies persistence, restores original values
2. **Employee Delete** — Creates a temp employee, deletes them, verifies removal, tests not-found case
3. **Salary Update** — Applies 10% raise to employees in a salary range, verifies new payroll record

---

## Changing DB Credentials

Edit `src/com/companyz/ems/dao/DatabaseConnection.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/employeeData";
private static final String USER = "root";
private static final String PASSWORD = "root";
```
