package com.companyz.ems.test;

public class TestRunner {
    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("  Company Z EMS - Test Suite");
        System.out.println("===========================================\n");

        int passed = 0;
        int total = 3;

        // Test 1: Employee Update
        System.out.println("--- Test 1: Employee Update ---");
        boolean test1 = EmployeeUpdateTest.run();
        System.out.println("Result: " + (test1 ? "PASS" : "FAIL"));
        if (test1) passed++;
        System.out.println();

        // Test 2: Employee Delete
        System.out.println("--- Test 2: Employee Search & Delete ---");
        boolean test2 = EmployeeDeleteTest.run();
        System.out.println("Result: " + (test2 ? "PASS" : "FAIL"));
        if (test2) passed++;
        System.out.println();

        // Test 3: Salary Update
        System.out.println("--- Test 3: Salary Update by Percentage ---");
        boolean test3 = SalaryUpdateTest.run();
        System.out.println("Result: " + (test3 ? "PASS" : "FAIL"));
        if (test3) passed++;
        System.out.println();

        System.out.println("===========================================");
        System.out.println("  Summary: " + passed + "/" + total + " tests passed");
        System.out.println("===========================================");
    }
}
