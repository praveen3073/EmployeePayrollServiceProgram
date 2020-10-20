package com.employeepayrollservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeePayrollService {
    public enum IOCommand
    {CONSOLE_IO,FILE_IO,DB_IO,REST_IO}

    //Declaring global var list of employee data
    public List<EmployeePayrollData> employeeDataList;

    public void setEmployeeDataList(List<EmployeePayrollData> employeeDataList) {
        this.employeeDataList = employeeDataList;
    }

    public EmployeePayrollService() {
        employeeDataList = new ArrayList<EmployeePayrollData>();
    }

    public void readEmployeeData() {
        Scanner consoleScanner=new Scanner(System.in);
        System.out.print("Enter Employee ID : ");
        int id = consoleScanner.nextInt();
        System.out.print("Enter Employee name : ");
        String name = consoleScanner.next();
        System.out.print("Enter Employee salary : ");
        double salary = consoleScanner.nextDouble();
        EmployeePayrollData employee=new EmployeePayrollData(id,name,salary);
        System.out.println(employee);
        employeeDataList.add(employee);
        consoleScanner.close();
    }

    public void printData() {
        new EmployeePayrollIO().printData();
    }

    public void writeEmployeeData(IOCommand ioType) {
        if(ioType.equals(ioType.CONSOLE_IO)) {
            System.out.println("Writing Employee Payroll Data to Console.");
            for (EmployeePayrollData employee:employeeDataList) {
                employee.printData();
            }
        }else if (ioType.equals(ioType.FILE_IO)){
            new EmployeePayrollIO().writeData(employeeDataList);
            System.out.println("Write in File");
        }
    }

    public int countEntries(IOCommand ioType) {
        if(ioType.equals(IOCommand.FILE_IO))
            return new EmployeePayrollIO().countEntries();
        return 0;
    }

    //Main Method
    public static void main(String[] args) {
        EmployeePayrollService employee = new EmployeePayrollService();
        employee.readEmployeeData();
        employee.writeEmployeeData(IOCommand.CONSOLE_IO);
        employee.writeEmployeeData(IOCommand.FILE_IO);
        System.out.println("Output from file: ");
        employee.printData();
    }
}
