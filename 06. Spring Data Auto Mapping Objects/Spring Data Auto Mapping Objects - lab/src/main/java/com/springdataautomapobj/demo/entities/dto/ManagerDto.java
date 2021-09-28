package com.springdataautomapobj.demo.entities.dto;

import java.util.List;
import java.util.Set;

public class ManagerDto extends BaseDto{
    List<EmployeeDto> subordinates;

    public List<EmployeeDto> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(List<EmployeeDto> subordinates) {
        this.subordinates = subordinates;
    }
}
