package com.springdataautomapobj.demo.entities.dto;

import java.math.BigDecimal;

public class EmployeeDto extends BaseDto{

    private BigDecimal salary;
    private String managerLastName;

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getManagerLastName() {
        return managerLastName;
    }

    public void setManagerLastName(String managerLastName) {
        this.managerLastName = managerLastName;
    }
}
