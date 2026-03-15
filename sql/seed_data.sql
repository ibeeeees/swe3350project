-- Company Z Employee Management System - Seed Data
USE employeeData;

-- Insert all 50 US states
INSERT INTO states (stateAbbr) VALUES
('AL'),('AK'),('AZ'),('AR'),('CA'),('CO'),('CT'),('DE'),('FL'),('GA'),
('HI'),('ID'),('IL'),('IN'),('IA'),('KS'),('KY'),('LA'),('ME'),('MD'),
('MA'),('MI'),('MN'),('MS'),('MO'),('MT'),('NE'),('NV'),('NH'),('NJ'),
('NM'),('NY'),('NC'),('ND'),('OH'),('OK'),('OR'),('PA'),('RI'),('SC'),
('SD'),('TN'),('TX'),('UT'),('VA'),('VT'),('WA'),('WV'),('WI'),('WY');

-- Insert cities
INSERT INTO cities (cityName) VALUES
('Atlanta'),('New York'),('Los Angeles'),('Chicago'),('Houston'),
('Phoenix'),('Philadelphia'),('San Antonio'),('San Diego'),('Dallas');

-- Insert addresses
INSERT INTO addresses (street, cityID, stateID, zip) VALUES
('123 Peachtree St', 1, 10, '30301'),
('456 Broadway Ave', 2, 32, '10001'),
('789 Sunset Blvd', 3, 5, '90001'),
('321 Michigan Ave', 4, 13, '60601'),
('654 Main St', 5, 43, '77001'),
('987 Desert Rd', 6, 3, '85001'),
('111 Liberty Ave', 7, 38, '19101'),
('222 Alamo Plaza', 8, 43, '78201'),
('333 Harbor Dr', 9, 5, '92101'),
('444 Elm St', 10, 43, '75201');

-- Insert employees
INSERT INTO employees (firstName, lastName, SSN, DOB, email, phone, emergencyContactName, emergencyContactPhone, addressID, hireDate, isActive) VALUES
('Sarah', 'Johnson', '123-45-6789', '1985-03-15', 'sjohnson@companyz.com', '404-555-0101', 'Mike Johnson', '404-555-0102', 1, '2020-01-15', TRUE),
('John', 'Smith', '234-56-7890', '1990-07-22', 'jsmith@companyz.com', '212-555-0201', 'Jane Smith', '212-555-0202', 2, '2020-03-01', TRUE),
('Maria', 'Garcia', '345-67-8901', '1988-11-08', 'mgarcia@companyz.com', '310-555-0301', 'Carlos Garcia', '310-555-0302', 3, '2021-06-15', TRUE),
('James', 'Williams', '456-78-9012', '1992-01-30', 'jwilliams@companyz.com', '312-555-0401', 'Lisa Williams', '312-555-0402', 4, '2021-09-01', TRUE),
('Emily', 'Brown', '567-89-0123', '1995-05-12', 'ebrown@companyz.com', '713-555-0501', 'Robert Brown', '713-555-0502', 5, '2022-02-14', TRUE),
('Michael', 'Davis', '678-90-1234', '1987-09-25', 'mdavis@companyz.com', '602-555-0601', 'Susan Davis', '602-555-0602', 6, '2022-07-01', TRUE),
('Jennifer', 'Martinez', '789-01-2345', '1993-12-03', 'jmartinez@companyz.com', '215-555-0701', 'Pedro Martinez', '215-555-0702', 7, '2023-01-10', TRUE),
('David', 'Anderson', '890-12-3456', '1991-04-18', 'danderson@companyz.com', '210-555-0801', 'Karen Anderson', '210-555-0802', 8, '2023-05-20', TRUE),
('Jessica', 'Taylor', '901-23-4567', '1994-08-07', 'jtaylor@companyz.com', '619-555-0901', 'Mark Taylor', '619-555-0902', 9, '2024-03-01', TRUE),
('Christopher', 'Thomas', '012-34-5678', '1989-06-14', 'cthomas@companyz.com', '214-555-1001', 'Nancy Thomas', '214-555-1002', 10, '2025-01-06', TRUE);

-- Insert divisions
INSERT INTO division (divName) VALUES
('Engineering'),('Marketing'),('Human Resources');

-- Insert job titles
INSERT INTO job_titles (jobTitle) VALUES
('Software Engineer'),('Marketing Manager'),('HR Specialist'),('Senior Developer'),('Marketing Coordinator');

-- Assign employees to divisions
INSERT INTO employee_division (empID, divID) VALUES
(1, 3),(2, 1),(3, 1),(4, 2),(5, 2),
(6, 1),(7, 3),(8, 1),(9, 2),(10, 1);

-- Assign employees to job titles
INSERT INTO employee_job_titles (empID, job_titleID) VALUES
(1, 3),(2, 1),(3, 4),(4, 2),(5, 5),
(6, 1),(7, 3),(8, 4),(9, 5),(10, 1);

-- Payroll records (3 months for each employee)
-- Employee 1: Sarah Johnson - HR Specialist - $5500/month gross
INSERT INTO payroll (empID, payDate, grossPay, federalTax, stateTax, socialSecurity, medicare, retirement401k, healthInsurance, netPay) VALUES
(1, '2025-01-31', 5500.00, 1210.00, 275.00, 341.00, 79.75, 330.00, 200.00, 3064.25),
(1, '2025-02-28', 5500.00, 1210.00, 275.00, 341.00, 79.75, 330.00, 200.00, 3064.25),
(1, '2025-03-31', 5500.00, 1210.00, 275.00, 341.00, 79.75, 330.00, 200.00, 3064.25);

-- Employee 2: John Smith - Software Engineer - $6500/month gross
INSERT INTO payroll (empID, payDate, grossPay, federalTax, stateTax, socialSecurity, medicare, retirement401k, healthInsurance, netPay) VALUES
(2, '2025-01-31', 6500.00, 1430.00, 325.00, 403.00, 94.25, 390.00, 200.00, 3657.75),
(2, '2025-02-28', 6500.00, 1430.00, 325.00, 403.00, 94.25, 390.00, 200.00, 3657.75),
(2, '2025-03-31', 6500.00, 1430.00, 325.00, 403.00, 94.25, 390.00, 200.00, 3657.75);

-- Employee 3: Maria Garcia - Senior Developer - $7500/month gross
INSERT INTO payroll (empID, payDate, grossPay, federalTax, stateTax, socialSecurity, medicare, retirement401k, healthInsurance, netPay) VALUES
(3, '2025-01-31', 7500.00, 1650.00, 375.00, 465.00, 108.75, 450.00, 200.00, 4251.25),
(3, '2025-02-28', 7500.00, 1650.00, 375.00, 465.00, 108.75, 450.00, 200.00, 4251.25),
(3, '2025-03-31', 7500.00, 1650.00, 375.00, 465.00, 108.75, 450.00, 200.00, 4251.25);

-- Employee 4: James Williams - Marketing Manager - $6000/month gross
INSERT INTO payroll (empID, payDate, grossPay, federalTax, stateTax, socialSecurity, medicare, retirement401k, healthInsurance, netPay) VALUES
(4, '2025-01-31', 6000.00, 1320.00, 300.00, 372.00, 87.00, 360.00, 200.00, 3361.00),
(4, '2025-02-28', 6000.00, 1320.00, 300.00, 372.00, 87.00, 360.00, 200.00, 3361.00),
(4, '2025-03-31', 6000.00, 1320.00, 300.00, 372.00, 87.00, 360.00, 200.00, 3361.00);

-- Employee 5: Emily Brown - Marketing Coordinator - $4500/month gross
INSERT INTO payroll (empID, payDate, grossPay, federalTax, stateTax, socialSecurity, medicare, retirement401k, healthInsurance, netPay) VALUES
(5, '2025-01-31', 4500.00, 990.00, 225.00, 279.00, 65.25, 270.00, 200.00, 2470.75),
(5, '2025-02-28', 4500.00, 990.00, 225.00, 279.00, 65.25, 270.00, 200.00, 2470.75),
(5, '2025-03-31', 4500.00, 990.00, 225.00, 279.00, 65.25, 270.00, 200.00, 2470.75);

-- Employee 6: Michael Davis - Software Engineer - $6500/month gross
INSERT INTO payroll (empID, payDate, grossPay, federalTax, stateTax, socialSecurity, medicare, retirement401k, healthInsurance, netPay) VALUES
(6, '2025-01-31', 6500.00, 1430.00, 325.00, 403.00, 94.25, 390.00, 200.00, 3657.75),
(6, '2025-02-28', 6500.00, 1430.00, 325.00, 403.00, 94.25, 390.00, 200.00, 3657.75),
(6, '2025-03-31', 6500.00, 1430.00, 325.00, 403.00, 94.25, 390.00, 200.00, 3657.75);

-- Employee 7: Jennifer Martinez - HR Specialist - $5500/month gross
INSERT INTO payroll (empID, payDate, grossPay, federalTax, stateTax, socialSecurity, medicare, retirement401k, healthInsurance, netPay) VALUES
(7, '2025-01-31', 5500.00, 1210.00, 275.00, 341.00, 79.75, 330.00, 200.00, 3064.25),
(7, '2025-02-28', 5500.00, 1210.00, 275.00, 341.00, 79.75, 330.00, 200.00, 3064.25),
(7, '2025-03-31', 5500.00, 1210.00, 275.00, 341.00, 79.75, 330.00, 200.00, 3064.25);

-- Employee 8: David Anderson - Senior Developer - $7000/month gross
INSERT INTO payroll (empID, payDate, grossPay, federalTax, stateTax, socialSecurity, medicare, retirement401k, healthInsurance, netPay) VALUES
(8, '2025-01-31', 7000.00, 1540.00, 350.00, 434.00, 101.50, 420.00, 200.00, 3954.50),
(8, '2025-02-28', 7000.00, 1540.00, 350.00, 434.00, 101.50, 420.00, 200.00, 3954.50),
(8, '2025-03-31', 7000.00, 1540.00, 350.00, 434.00, 101.50, 420.00, 200.00, 3954.50);

-- Employee 9: Jessica Taylor - Marketing Coordinator - $4000/month gross
INSERT INTO payroll (empID, payDate, grossPay, federalTax, stateTax, socialSecurity, medicare, retirement401k, healthInsurance, netPay) VALUES
(9, '2025-01-31', 4000.00, 880.00, 200.00, 248.00, 58.00, 240.00, 200.00, 2174.00),
(9, '2025-02-28', 4000.00, 880.00, 200.00, 248.00, 58.00, 240.00, 200.00, 2174.00),
(9, '2025-03-31', 4000.00, 880.00, 200.00, 248.00, 58.00, 240.00, 200.00, 2174.00);

-- Employee 10: Christopher Thomas - Software Engineer - $6000/month gross
INSERT INTO payroll (empID, payDate, grossPay, federalTax, stateTax, socialSecurity, medicare, retirement401k, healthInsurance, netPay) VALUES
(10, '2025-01-31', 6000.00, 1320.00, 300.00, 372.00, 87.00, 360.00, 200.00, 3361.00),
(10, '2025-02-28', 6000.00, 1320.00, 300.00, 372.00, 87.00, 360.00, 200.00, 3361.00),
(10, '2025-03-31', 6000.00, 1320.00, 300.00, 372.00, 87.00, 360.00, 200.00, 3361.00);

-- Users (HR Admin and General Employee)
-- admin123 SHA-256: 240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9
-- password SHA-256: 5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8
INSERT INTO users (empID, username, passwordHash, role) VALUES
(1, 'admin', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'HR_ADMIN'),
(2, 'jsmith', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'EMPLOYEE');
