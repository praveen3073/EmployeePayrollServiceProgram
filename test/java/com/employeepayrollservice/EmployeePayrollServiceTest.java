package com.employeepayrollservice;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class EmployeePayrollServiceTest {
    private static final String HOME =
            "C:/Users/Praveen Satya";
    private static final String PLAY_WITH_NIO = "TempPlayGround";

    //Method to delete files
    public boolean deleteFiles(File contentsToDelete) {
        File[] allContents = contentsToDelete.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteFiles(file);
            }
        }
        return contentsToDelete.delete();
    }

    @Test
    public void givenPathWhenCheckedThenConfirm() throws IOException {
        //Check File Exists
        Path homePath = Paths.get(HOME);
        Assert.assertTrue(Files.exists(homePath));

        // Delete file and check file not exist
        Path playPath = Paths.get(HOME + "/" + PLAY_WITH_NIO);
        if (Files.exists(playPath)) deleteFiles(playPath.toFile());
        Assert.assertTrue(Files.notExists(playPath));

        // Create Directory
        Files.createDirectory(playPath);
        Assert.assertTrue(Files.exists(playPath));

        // Create File
        IntStream.range(1, 10).forEach(cntr -> {
            Path tempFile = Paths.get(playPath + "/temp" + cntr);

            Assert.assertTrue(Files.notExists(tempFile));

            try {
                Files.createFile(tempFile);
            } catch (IOException e) {
                System.out.println("IO Exception Occured.");
            }

            Assert.assertTrue(Files.exists(tempFile));
        });

        // List files & directories
        Files.list(playPath).filter(Files::isRegularFile)
                .forEach(System.out::println);
        System.out.println();

        Files.newDirectoryStream(playPath).forEach(System.out::println);
        System.out.println();

        Files.newDirectoryStream(playPath, path -> path.toFile().isFile()
                && path.toString().startsWith("temp"))
                .forEach(System.out::println);
    }

    @Test
    public void givenADirWhenWatchedListsAllTheActivities() throws IOException {
        Path dir = Paths.get(HOME + "/" + PLAY_WITH_NIO);
        Files.list(dir).filter(Files::isRegularFile).forEach(System.out::println);
        Assert.assertTrue(new JavaWatchService(dir).processEvents());
    }

    @Test
    public void givenThreeEmployeesWhenWrittenToFileShouldMatchEmployeeEntries() {
        EmployeePayrollData[] arrayOfEmp = {
                new EmployeePayrollData(1,"Jeff Bezos",100000.0),
                new EmployeePayrollData(2, "Bill Gates",200000.0),
                new EmployeePayrollData(3, "Mark Zuckerberg",300000.0)
        };

        EmployeePayrollService employee = new EmployeePayrollService();
        employee.setEmployeeDataList(Arrays.asList(arrayOfEmp));
        employee.writeEmployeeData(EmployeePayrollService.IOCommand.FILE_IO);
        employee.printData();
        Assert.assertEquals(3, employee.countEntries(EmployeePayrollService.IOCommand.FILE_IO));
    }

    @Test
    public void givenFileOnReadingFromFileShouldMatchEmployeeCount() {
        EmployeePayrollData[] arrayOfEmp = {
                new EmployeePayrollData(1,"Jeff Bezos",100000.0),
                new EmployeePayrollData(2, "Bill Gates",200000.0),
                new EmployeePayrollData(3, "Mark Zuckerberg",300000.0)
        };

        EmployeePayrollService employee = new EmployeePayrollService();
        employee.setEmployeeDataList(Arrays.asList(arrayOfEmp));
        employee.writeEmployeeData(EmployeePayrollService.IOCommand.FILE_IO);
        employee.printData();
        List<EmployeePayrollData> employeeList = employee.readFileData();
        Assert.assertEquals(3, employee.countEntries(EmployeePayrollService.IOCommand.FILE_IO));
    }
}

