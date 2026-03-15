package com.companyz.ems.test;

import com.companyz.ems.dao.AddressDAO;
import com.companyz.ems.dao.EmployeeDAO;
import com.companyz.ems.model.Address;
import com.companyz.ems.model.Employee;

/**
 * Programming Task: Search for Employee by empID for Deletion
 * Description: Search for an employee by empID. If found, confirm and delete.
 * If not found, display a "no match" message. Verify deletion by re-searching.
 *
 * Test Case: Search for employee (empID) for deletion. If found, confirm delete,
 * otherwise no match for employee.
 * Precondition: Database is accessible
 * Input: A temporary test employee is created, then deleted
 * Expected: After deletion, employee is no longer found. Non-existent empID returns null.
 * Pass criteria: Employee found before delete, not found after delete, empID=99999 not found.
 */
public class EmployeeDeleteTest {

    public static boolean run() {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        AddressDAO addressDAO = new AddressDAO();

        try {
            // Step 1: Create a temporary test employee
            System.out.println("Step 1: Creating temporary test employee...");
            Address addr = new Address();
            addr.setStreet("999 Test St");
            addr.setCityID(1);
            addr.setStateID(1);
            addr.setZip("99999");
            int addrID = addressDAO.addAddress(addr);

            Employee testEmp = new Employee();
            testEmp.setFirstName("TestDelete");
            testEmp.setLastName("Employee");
            testEmp.setSSN("999-99-9999");
            testEmp.setDOB(java.sql.Date.valueOf("1990-01-01"));
            testEmp.setEmail("testdelete@test.com");
            testEmp.setPhone("000-000-0000");
            testEmp.setEmergencyContactName("Test Contact");
            testEmp.setEmergencyContactPhone("000-000-0001");
            testEmp.setAddressID(addrID);
            testEmp.setHireDate(java.sql.Date.valueOf("2025-01-01"));
            testEmp.setActive(true);
            employeeDAO.addEmployee(testEmp);
            System.out.println("Created test employee with empID=" + testEmp.getEmpID());

            // Step 2: Search for the employee
            System.out.println("Step 2: Searching for employee by SSN '999-99-9999'...");
            Employee found = employeeDAO.searchBySSN("999-99-9999");
            if (found == null) {
                System.out.println("FAILED: Could not find the test employee.");
                return false;
            }
            int testEmpID = found.getEmpID();
            System.out.println("Employee found: " + found.getFirstName() + " " + found.getLastName() + " (ID: " + testEmpID + ")");

            // Step 3: Confirm and delete
            System.out.println("Step 3: Employee found, confirming delete...");
            boolean deleted = employeeDAO.deleteEmployee(testEmpID);
            if (!deleted) {
                System.out.println("FAILED: deleteEmployee returned false.");
                return false;
            }
            System.out.println("Employee deleted successfully.");

            // Step 4: Verify deletion
            System.out.println("Step 4: Verifying employee no longer exists...");
            Employee afterDelete = employeeDAO.searchByEmpID(testEmpID);
            if (afterDelete != null) {
                System.out.println("FAILED: Employee still exists after deletion.");
                return false;
            }
            System.out.println("Verified: Employee not found after deletion.");

            // Step 5: Test not-found case
            System.out.println("Step 5: Searching for non-existent empID=99999...");
            Employee notFound = employeeDAO.searchByEmpID(99999);
            if (notFound != null) {
                System.out.println("FAILED: Found employee with empID=99999 (should not exist).");
                return false;
            }
            System.out.println("No match for employee with empID=99999. (Expected behavior)");

            return true;

        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
