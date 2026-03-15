package com.companyz.ems.test;

import com.companyz.ems.dao.EmployeeDAO;
import com.companyz.ems.model.Employee;

/**
 * Programming Task: Update Employee Data
 * Description: Search for an employee by empID, modify their first name and email,
 * save changes, then verify the update persisted by re-fetching the employee.
 * This test is general enough to apply to any employee data field.
 *
 * Test Case: Update employee data (make it general enough using new classes for all employee data)
 * Precondition: Employee with empID=1 exists in the database
 * Input: empID=1, new firstName="TestUpdate", new email="test@update.com"
 * Expected: After update, re-fetched employee has the new values
 * Pass criteria: firstName and email match updated values after re-fetch
 */
public class EmployeeUpdateTest {

    public static boolean run() {
        EmployeeDAO employeeDAO = new EmployeeDAO();

        try {
            // Step 1: Fetch employee with empID=1
            System.out.println("Step 1: Fetching employee with empID=1...");
            Employee emp = employeeDAO.searchByEmpID(1);
            if (emp == null) {
                System.out.println("FAILED: Employee with empID=1 not found.");
                return false;
            }
            System.out.println("Found: " + emp.getFirstName() + " " + emp.getLastName());

            // Step 2: Store original values
            String origFirstName = emp.getFirstName();
            String origEmail = emp.getEmail();
            System.out.println("Step 2: Original firstName=" + origFirstName + ", email=" + origEmail);

            // Step 3: Update values
            System.out.println("Step 3: Updating firstName to 'TestUpdate', email to 'test@update.com'...");
            emp.setFirstName("TestUpdate");
            emp.setEmail("test@update.com");

            // Step 4: Save to database
            System.out.println("Step 4: Saving changes to database...");
            boolean updated = employeeDAO.updateEmployee(emp);
            if (!updated) {
                System.out.println("FAILED: updateEmployee returned false.");
                return false;
            }

            // Step 5: Re-fetch and verify
            System.out.println("Step 5: Re-fetching employee to verify changes...");
            Employee updated_emp = employeeDAO.searchByEmpID(1);
            boolean firstNameMatch = "TestUpdate".equals(updated_emp.getFirstName());
            boolean emailMatch = "test@update.com".equals(updated_emp.getEmail());
            System.out.println("Verified firstName: " + updated_emp.getFirstName() + " -> " + (firstNameMatch ? "MATCH" : "MISMATCH"));
            System.out.println("Verified email: " + updated_emp.getEmail() + " -> " + (emailMatch ? "MATCH" : "MISMATCH"));

            // Step 6: Restore original values
            System.out.println("Step 6: Restoring original values...");
            emp.setFirstName(origFirstName);
            emp.setEmail(origEmail);
            employeeDAO.updateEmployee(emp);

            return firstNameMatch && emailMatch;

        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
