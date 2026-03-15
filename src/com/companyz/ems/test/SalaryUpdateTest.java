package com.companyz.ems.test;

import com.companyz.ems.dao.PayrollDAO;
import com.companyz.ems.model.Payroll;
import com.companyz.ems.service.SalaryService;
import java.util.List;

/**
 * Programming Task: Update Salary by Percentage for Employees in a Given Range
 * Description: Update salary for all employees by a given percentage when their
 * annualized salary (grossPay * 12) is less than a given amount (within a specified range).
 * A new payroll record is created with the increased gross pay and recalculated deductions.
 *
 * Test Case: Update salary for all employees by a given percentage when salary less than
 * a given amount.
 * Precondition: Employee empID=2 has payroll records
 * Input: percentage=10, minSalary=0, maxSalary=200000
 * Expected: A new payroll record is created with grossPay increased by 10%
 * Pass criteria: Updated count > 0, new grossPay is ~1.10 * original grossPay (within $1)
 */
public class SalaryUpdateTest {

    public static boolean run() {
        SalaryService salaryService = new SalaryService();
        PayrollDAO payrollDAO = new PayrollDAO();

        try {
            // Step 1: Get employee 2's latest payroll
            System.out.println("Step 1: Fetching latest payroll for empID=2...");
            List<Payroll> payBefore = payrollDAO.getByEmpID(2);
            if (payBefore.isEmpty()) {
                System.out.println("FAILED: No payroll records found for empID=2.");
                return false;
            }
            Payroll latest = payBefore.get(0);
            double originalGross = latest.getGrossPay();
            double annualSalary = originalGross * 12;
            System.out.println("Original grossPay: $" + originalGross + " (Annual: $" + annualSalary + ")");

            // Step 2: Apply 10% increase for salaries in range 0-200000
            System.out.println("Step 2: Applying 10% increase for salaries between $0 and $200,000...");
            int count = salaryService.updateSalariesByPercentage(10.0, 0, 200000);
            System.out.println("Employees updated: " + count);

            if (count <= 0) {
                System.out.println("FAILED: No employees were updated.");
                return false;
            }

            // Step 3: Verify new payroll record for empID=2
            System.out.println("Step 3: Verifying new payroll record for empID=2...");
            List<Payroll> payAfter = payrollDAO.getByEmpID(2);
            Payroll newLatest = payAfter.get(0);
            double newGross = newLatest.getGrossPay();
            double expectedGross = originalGross * 1.10;

            System.out.println("New grossPay: $" + newGross);
            System.out.println("Expected grossPay: $" + String.format("%.2f", expectedGross));

            boolean match = Math.abs(newGross - expectedGross) < 1.00;
            System.out.println("GrossPay match (within $1 tolerance): " + (match ? "YES" : "NO"));

            return match;

        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
