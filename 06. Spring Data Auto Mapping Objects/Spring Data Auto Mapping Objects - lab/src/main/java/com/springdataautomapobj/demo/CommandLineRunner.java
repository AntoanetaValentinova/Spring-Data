package com.springdataautomapobj.demo;

import com.springdataautomapobj.demo.entities.Employee;
import com.springdataautomapobj.demo.entities.dto.EmployeeDto;
import com.springdataautomapobj.demo.entities.dto.ManagerDto;
import com.springdataautomapobj.demo.services.EmployeeService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandLineRunner implements org.springframework.boot.CommandLineRunner {

    private final EmployeeService employeeService;

    public CommandLineRunner(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Exercise 1:");
        EmployeeDto employeeDto = this.employeeService.findOne(1L);
        System.out.println(employeeDto.getFirstName()+" "+employeeDto.getLastName()+": "+employeeDto.getSalary());
        System.out.println("Exercise 2:");
        ManagerDto oneManager = this.employeeService.findOneManager(1L);
        System.out.println(oneManager.getFirstName()+" "+oneManager.getLastName()+":");
        oneManager.getSubordinates().forEach(employee -> System.out.printf("\t -%s %s : %.2f%n",employee.getFirstName(),employee.getLastName(),employee.getSalary()));
        System.out.println("Exercise 3:");
        List<EmployeeDto> allEmployeesBornBefore1990 = this.employeeService.findAllEmployeesBornBefore1990();
        allEmployeesBornBefore1990.forEach(employee ->
                System.out.printf("%s %s  %.2f Manager last name: %s%n",employee.getFirstName(),employee.getLastName(),
                        employee.getSalary(),employee.getManagerLastName()));

    }
}
