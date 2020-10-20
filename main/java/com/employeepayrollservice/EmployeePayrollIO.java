package com.employeepayrollservice;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollIO {
    public static String PAYROLL_FNAME = "payroll.txt";

    public void writeData(List<EmployeePayrollData> employeeDataList) {

        StringBuffer empBuffer = new StringBuffer();
        employeeDataList.forEach(employee -> {
            String employeeDataStr = employee.pushData().concat("\n");
            empBuffer.append(employeeDataStr);
        });

        try {
            Files.write(Paths.get(PAYROLL_FNAME), empBuffer.toString().getBytes());
        }catch(IOException exception) {
            exception.printStackTrace();
        }
    }

    /**Method to read data from file to console
     *
     */
    public void printData() {
        try {
            Files.lines(new File(PAYROLL_FNAME).toPath()).forEach(System.out::println);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public int countEntries() {

        int entries = 0;

        try {
            entries = (int) Files.lines(new File(PAYROLL_FNAME).toPath()).count();
        }catch(IOException exception) {
            exception.printStackTrace();
        }
        return entries;
    }

    public List<EmployeePayrollData> readData() {
        List<EmployeePayrollData> employeeDataList = new ArrayList<EmployeePayrollData>();

        try {
            Files.lines(new File(PAYROLL_FNAME).toPath())
                    .map(line->line.trim())
                    .forEach(line->{
                        String data = line.toString();
                        String[] dataArr = data.split(",");
                        for(int i=0;i<dataArr.length;i++){
                            int id = Integer.valueOf(dataArr[i].split(" = ")[1]);
                            i++;
                            String name = dataArr[i].replaceAll("name =", "");
                            i++;
                            double salary = Double.parseDouble(dataArr[i].replaceAll("salary =", ""));
                            EmployeePayrollData employee = new EmployeePayrollData(id,name,salary);
                            employeeDataList.add(employee);
                        }
                    });
        }catch(IOException exception) {
            exception.printStackTrace();
        }
        return employeeDataList;
    }
}
