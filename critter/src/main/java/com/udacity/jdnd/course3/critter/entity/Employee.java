package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ElementCollection
    private Set<EmployeeSkill> employeeSkills;

    @ElementCollection
    private Set<DayOfWeek> employeeAvailabilitySet;

    public Employee(Long id, String name, Set<EmployeeSkill> employeeSkills, Set<DayOfWeek> employeeAvailabilitySet) {
        this.id = id;
        this.name = name;
        this.employeeSkills = employeeSkills;
        this.employeeAvailabilitySet = employeeAvailabilitySet;
    }

    public Employee(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EmployeeSkill> getEmployeeSkills() {
        return employeeSkills;
    }

    public void setEmployeeSkills(Set<EmployeeSkill> employeeSkills) {
        this.employeeSkills = employeeSkills;
    }

    public Set<DayOfWeek> getEmployeeAvailabilitySet() {
        return employeeAvailabilitySet;
    }

    public void setEmployeeAvailabilitySet(Set<DayOfWeek> employeeAvailabilitySet) {
        this.employeeAvailabilitySet = employeeAvailabilitySet;
    }
}
