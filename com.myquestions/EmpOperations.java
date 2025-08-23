package com.oracle.doceng.learning;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EmpOperations {

    public static void main(String[] args) {

       // Filter employees in Engineering
        EmployeeData.returnEmployeeData().stream().filter(emp -> emp.getDepartment().equals("Engineering")).forEach(emp -> System.out.println(emp.toString()));
        List<Employee> listOfEngineeringEmployees=
                EmployeeData.returnEmployeeData().stream()
                        .filter(emp -> emp.getDepartment().equals("Engineering"))
                        .collect(Collectors.toList());
        listOfEngineeringEmployees.forEach(System.out::println);

        //Get names of employees earning more than 70k
        EmployeeData.returnEmployeeData().stream().filter(emp-> emp.getSalary()>70000)
                .map(Employee::getName).sorted().forEach(System.out::println);

        //Group employees by department
        Map<String, List<Employee>> byDept=EmployeeData.returnEmployeeData().stream().collect(Collectors.groupingBy(Employee::getDepartment));

        byDept.forEach((dept, list) -> {
            System.out.println(dept + ":");
            list.forEach(e -> System.out.println("  " + e));
        });

        //find departments

        Set<String> departments =EmployeeData.returnEmployeeData().stream()
                .map(Employee::getDepartment)
                .sorted()
                .collect(Collectors.toSet());
        departments.forEach(System.out::println);

        //Calculate average salary per department
        Map<String, Double> avgSalaryByDept = EmployeeData.returnEmployeeData().stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.averagingDouble(Employee::getSalary)
                ));

        System.out.println(avgSalaryByDept);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        //Sort employees by salary (descending)
        List<Employee> sortedBySalary = EmployeeData.returnEmployeeData().stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .collect(Collectors.toList());

        sortedBySalary.forEach(System.out::println);

        //display all emp record in sorted manner (ascending) that name starts with 'a' in empList
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        List<String> empName=EmployeeData.returnEmployeeData().stream().filter(e->e.getName().startsWith("a"))
                .map(Employee::getName).collect(Collectors.toList());

        empName.forEach(System.out::println);



        //calcluate avg salary
        @NotNull Double avgsal=EmployeeData.returnEmployeeData().stream()
                        .collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println(avgsal);

        //list all names of employess

        EmployeeData.returnEmployeeData().stream().map(Employee::getName).forEach(System.out::println);

        //avg slaary in a department
         Map<String, Double> detAvgsalary= EmployeeData.returnEmployeeData().stream()
                .collect( Collectors.groupingBy(Employee::getDepartment,Collectors.averagingDouble(Employee::getSalary)));

        detAvgsalary.forEach((s, aDouble) ->
                System.out.println(s + ":" + aDouble)
        );

        //top 3 salaries
        System.out.println("+++++++++++++++++++++++top 3 salaries+++++++++++++++++++++++++++++++++++");
        Stream<Employee> maxsalary=
                EmployeeData.returnEmployeeData().stream()
                        .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                        .limit(3);

        maxsalary.forEach(System.out::println);

                //Count Employees in Each Department
        @NotNull Map<String, Long> byDept1=EmployeeData.returnEmployeeData().stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,Collectors.counting()));

        byDept1.forEach((dept, count) -> System.out.println(dept + ":" + count));

    }
}
