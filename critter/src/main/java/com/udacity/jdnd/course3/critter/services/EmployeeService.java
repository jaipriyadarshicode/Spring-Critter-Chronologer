package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee save(Employee employee){
        return employeeRepository.save(employee);
    }

    public Employee getEmployee(long employeeId){
        return employeeRepository.getOne(employeeId);
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId){
        Employee employee = employeeRepository.getOne(employeeId);
        employee.setEmployeeAvailabilitySet(daysAvailable);
        employeeRepository.save(employee);
    }

    public List<Employee> findEmployeesForService(DayOfWeek dayOfWeek, Set<EmployeeSkill> employeeSkills){
           return employeeRepository.findAll().stream()
                   .filter(rec -> rec.getEmployeeAvailabilitySet().contains(dayOfWeek) && rec.getEmployeeSkills().containsAll(employeeSkills))
                   .collect(Collectors.toList());

    }


}
