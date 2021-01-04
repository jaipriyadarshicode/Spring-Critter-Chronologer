package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate schedule;

    @ManyToMany
    private List<Employee> employees;

    @ManyToMany
    private List<Pet> pets;

   @ElementCollection
    private Set<EmployeeSkill> employeeSkillSet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getSchedule() {
        return schedule;
    }

    public void setSchedule(LocalDate schedule) {
        this.schedule = schedule;
    }

    public Set<EmployeeSkill> getEmployeeSkillSet() {
        return employeeSkillSet;
    }

    public void setEmployeeSkillSet(Set<EmployeeSkill> employeeSkillSet) {
        this.employeeSkillSet = employeeSkillSet;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
