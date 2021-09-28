package com.springdataautomapobj.demo.repositories;

import com.springdataautomapobj.demo.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    List<Employee> findAllByBirthdayBeforeOrderBySalaryDesc(LocalDate birthday);
}
