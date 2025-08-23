package com.oracle.doceng.learning;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamJava8 {

    public static void main(String[] args) {

        List<String> fruits = Arrays.asList("java", "streams", "makes", "code", "more" ,"reusable");

        //1. Given a List of strings, write a program to concatenate all the strings using streams.

       String Confruits =fruits.stream().collect(Collectors.joining("-"));
       System.out.println(Confruits);


        //2 .Find the sum of all elements in a List using streams.
        List<Integer> integerList = Arrays.asList(67,89,99,45,77,22,76,56,45,34,38);

        @NotNull long sum=integerList.stream().collect(Collectors.summarizingInt(Integer::intValue)).getSum();
        System.out.println(sum);

        //3. Given a List of strings, write a program to count the number of strings that start with a word "spring"
        List<String> spring_projects = Arrays.asList("java", "java swing", "apache", "spring", "java spring" ,"spring boot","spring batch", "spring data jpa", "spring cloud", "spring vault", "spring web" ,"spring drools");
        spring_projects.stream().filter(project -> project.startsWith("spring")).forEach(project -> System.out.println(project));



        // 4. Convert a List of strings to uppercase using streams.

        List<String> uppercasefruits= fruits.stream().map(String::toUpperCase).collect(Collectors.toList());
        uppercasefruits.forEach(System.out::println);

        // 5. Given a List of integers, write a program to filter out the even numbers using streams.

        List<Integer> integerList1 = Arrays.asList(67,89,99,45,77,22,76,56,45,34,38);
        List<Integer> integerEvenList1=integerList1.stream().filter(integer -> integer % 2 == 0).collect(Collectors.toList());
        integerEvenList1.forEach(System.out::println);


        // 6. Write a program to find the average of a List of floating-point numbers using streams.
        List<Double> ListofFlotingNumbers = Arrays.asList(67.4,89.5,99.7,45.7,77.7,22.9,76.4,56.5,45.6,34.6,38.9);
       double average=ListofFlotingNumbers.stream().collect(Collectors.averagingDouble(Double::doubleValue)).doubleValue();
       System.out.println(average);

        // 8. Write a program to remove duplicate elements from a List using streams.

        List<String> healthyfruits = Arrays.asList("banana", "apple", "orange", "papaya", "apple" ,"grapes","banana", "apple", "orange", "papaya", "apple" ,"grapes");

        Set<String> healthyfruits1= healthyfruits.stream().collect(Collectors.toSet());

        healthyfruits1.forEach(System.out::println);

    }




}
