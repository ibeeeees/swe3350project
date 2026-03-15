package com.companyz.ems.service;

import com.companyz.ems.dao.*;
import com.companyz.ems.model.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    private EmployeeDAO employeeDAO;
    private AddressDAO addressDAO;
    private DivisionDAO divisionDAO;
    private JobTitleDAO jobTitleDAO;
    private UserDAO userDAO;

    public EmployeeService() {
        this.employeeDAO = new EmployeeDAO();
        this.addressDAO = new AddressDAO();
        this.divisionDAO = new DivisionDAO();
        this.jobTitleDAO = new JobTitleDAO();
        this.userDAO = new UserDAO();
    }

    public List<Employee> searchEmployees(String searchType, String searchValue) {
        List<Employee> results = new ArrayList<>();
        switch (searchType.toLowerCase()) {
            case "name":
                results = employeeDAO.searchByName(searchValue);
                break;
            case "dob":
                try {
                    Date dob = Date.valueOf(searchValue);
                    results = employeeDAO.searchByDOB(dob);
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid date format. Use yyyy-MM-dd");
                }
                break;
            case "ssn":
                Employee empSSN = employeeDAO.searchBySSN(searchValue);
                if (empSSN != null) results.add(empSSN);
                break;
            case "empid":
                try {
                    int empID = Integer.parseInt(searchValue);
                    Employee emp = employeeDAO.searchByEmpID(empID);
                    if (emp != null) results.add(emp);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid employee ID");
                }
                break;
        }
        return results;
    }

    public boolean addNewEmployee(Employee emp, Address addr, int divID, int jobTitleID,
                                   String username, String password) {
        int addressID = addressDAO.addAddress(addr);
        if (addressID == -1) return false;

        emp.setAddressID(addressID);
        emp.setActive(true);
        boolean empAdded = employeeDAO.addEmployee(emp);
        if (!empAdded) return false;

        divisionDAO.setEmployeeDivision(emp.getEmpID(), divID);
        jobTitleDAO.setEmployeeJobTitle(emp.getEmpID(), jobTitleID);

        if (username != null && password != null && !username.isEmpty()) {
            AuthService authService = new AuthService();
            User user = new User();
            user.setEmpID(emp.getEmpID());
            user.setUsername(username);
            user.setPasswordHash(authService.hashPassword(password));
            user.setRole("EMPLOYEE");
            userDAO.addUser(user);
        }

        return true;
    }

    public boolean updateEmployee(Employee emp) {
        return employeeDAO.updateEmployee(emp);
    }

    public boolean deleteEmployee(int empID) {
        return employeeDAO.deleteEmployee(empID);
    }

    public List<Employee> getNewHires(Date start, Date end) {
        return employeeDAO.getNewHires(start, end);
    }

    public Employee getByEmpID(int empID) {
        return employeeDAO.searchByEmpID(empID);
    }
}
