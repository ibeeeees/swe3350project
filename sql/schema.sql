-- Company Z Employee Management System - Database Schema
-- MySQL Database: employeeData

DROP DATABASE IF EXISTS employeeData;
CREATE DATABASE employeeData;
USE employeeData;

-- States table (all 50 US states)
CREATE TABLE states (
    stateID INT PRIMARY KEY AUTO_INCREMENT,
    stateAbbr CHAR(2) UNIQUE NOT NULL
);

-- Cities table
CREATE TABLE cities (
    cityID INT PRIMARY KEY AUTO_INCREMENT,
    cityName VARCHAR(25) NOT NULL
);

-- Addresses table (normalized - shared if roommates)
CREATE TABLE addresses (
    addressID INT PRIMARY KEY AUTO_INCREMENT,
    street VARCHAR(100) NOT NULL,
    cityID INT NOT NULL,
    stateID INT NOT NULL,
    zip VARCHAR(10) NOT NULL,
    FOREIGN KEY (cityID) REFERENCES cities(cityID),
    FOREIGN KEY (stateID) REFERENCES states(stateID)
);

-- Employees table
CREATE TABLE employees (
    empID INT PRIMARY KEY AUTO_INCREMENT,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    SSN CHAR(11) UNIQUE NOT NULL,
    DOB DATE NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(15),
    emergencyContactName VARCHAR(100),
    emergencyContactPhone VARCHAR(15),
    addressID INT,
    hireDate DATE NOT NULL,
    isActive BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (addressID) REFERENCES addresses(addressID)
);

-- Division table
CREATE TABLE division (
    divID INT PRIMARY KEY AUTO_INCREMENT,
    divName VARCHAR(50) NOT NULL
);

-- Employee-Division junction table
CREATE TABLE employee_division (
    empID INT NOT NULL,
    divID INT NOT NULL,
    PRIMARY KEY (empID, divID),
    FOREIGN KEY (empID) REFERENCES employees(empID) ON DELETE CASCADE,
    FOREIGN KEY (divID) REFERENCES division(divID)
);

-- Job Titles table
CREATE TABLE job_titles (
    job_titleID INT PRIMARY KEY AUTO_INCREMENT,
    jobTitle VARCHAR(50) NOT NULL
);

-- Employee-Job Titles junction table
CREATE TABLE employee_job_titles (
    empID INT NOT NULL,
    job_titleID INT NOT NULL,
    PRIMARY KEY (empID, job_titleID),
    FOREIGN KEY (empID) REFERENCES employees(empID) ON DELETE CASCADE,
    FOREIGN KEY (job_titleID) REFERENCES job_titles(job_titleID)
);

-- Payroll table
CREATE TABLE payroll (
    payrollID INT PRIMARY KEY AUTO_INCREMENT,
    empID INT NOT NULL,
    payDate DATE NOT NULL,
    grossPay DECIMAL(12,2) NOT NULL,
    federalTax DECIMAL(10,2) NOT NULL,
    stateTax DECIMAL(10,2) NOT NULL,
    socialSecurity DECIMAL(10,2) NOT NULL,
    medicare DECIMAL(10,2) NOT NULL,
    retirement401k DECIMAL(10,2) NOT NULL,
    healthInsurance DECIMAL(10,2) NOT NULL,
    netPay DECIMAL(12,2) NOT NULL,
    FOREIGN KEY (empID) REFERENCES employees(empID) ON DELETE CASCADE
);

-- Users table (for login/authentication)
CREATE TABLE users (
    userID INT PRIMARY KEY AUTO_INCREMENT,
    empID INT UNIQUE,
    username VARCHAR(50) UNIQUE NOT NULL,
    passwordHash VARCHAR(255) NOT NULL,
    role ENUM('HR_ADMIN', 'EMPLOYEE') NOT NULL DEFAULT 'EMPLOYEE',
    FOREIGN KEY (empID) REFERENCES employees(empID) ON DELETE CASCADE
);

-- Indexes for performance
CREATE INDEX idx_employees_lastName ON employees(lastName);
CREATE INDEX idx_employees_SSN ON employees(SSN);
CREATE INDEX idx_employees_DOB ON employees(DOB);
CREATE INDEX idx_payroll_payDate ON payroll(payDate);
CREATE INDEX idx_payroll_empID ON payroll(empID);
CREATE INDEX idx_employees_hireDate ON employees(hireDate);
