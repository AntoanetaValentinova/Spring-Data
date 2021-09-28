package com.springdataautomapobj.demo.services;

import com.springdataautomapobj.demo.entities.Employee;
import com.springdataautomapobj.demo.entities.dto.EmployeeDto;
import com.springdataautomapobj.demo.entities.dto.ManagerDto;
import com.springdataautomapobj.demo.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeServileImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    public EmployeeServileImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeDto findOne(Long id) {
        ModelMapper modelMapper=new ModelMapper();
        return modelMapper.map(this.employeeRepository.findById(1L).orElse(null),EmployeeDto.class);
    }

    @Override
    public ManagerDto findOneManager(Long id) {
        ModelMapper modelMapper=new ModelMapper();
        return modelMapper.map(this.employeeRepository.findById(1L).orElseThrow(),ManagerDto.class);
    }

    @Override
    public List<EmployeeDto> findAllEmployeesBornBefore1990() {
        ModelMapper modelMapper=new ModelMapper();
        modelMapper.addMappings(new PropertyMap<Employee, EmployeeDto>() {
            @Override
            protected void configure() {
                map().setManagerLastName(source.getManager().getLastName());
            }
        });
        return modelMapper.map(this.employeeRepository.findAllByBirthdayBeforeOrderBySalaryDesc(LocalDate.of(1990,1,1)),
                new TypeToken<List<EmployeeDto>>(){}.getType());
    }
}
