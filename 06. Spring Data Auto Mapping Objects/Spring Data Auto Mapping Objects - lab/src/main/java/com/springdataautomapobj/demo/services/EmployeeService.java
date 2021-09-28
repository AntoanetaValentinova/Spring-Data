package com.springdataautomapobj.demo.services;

import com.springdataautomapobj.demo.entities.dto.EmployeeDto;
import com.springdataautomapobj.demo.entities.dto.ManagerDto;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeService {

    EmployeeDto findOne(Long id);

    ManagerDto findOneManager(Long id);

    List<EmployeeDto> findAllEmployeesBornBefore1990();
}
