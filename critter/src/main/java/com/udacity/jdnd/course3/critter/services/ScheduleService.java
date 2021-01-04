package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    public Schedule save(Schedule schedule, List<Long> petIds, List<Long> employeeIds){
        List<Employee> employeeList = employeeRepository.findAllById(employeeIds);
        List<Pet> petList = petRepository.findAllById(petIds);
        schedule.setEmployees(employeeList);
        schedule.setPets(petList);
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules(){
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForPet(long pedId){
        Pet pet = petRepository.getOne(pedId);
        return scheduleRepository.findAll().stream().filter(rec -> rec.getPets().contains(pet)).collect(Collectors.toList());
    }

    public List<Schedule> getScheduleForEmployee(long employeeId){
        Employee employee = employeeRepository.getOne(employeeId);
        return scheduleRepository.findAll().stream().filter(rec -> rec.getEmployees().contains(employee)).collect(Collectors.toList());
    }

    public List<Schedule> getScheduleForCustomer(long customerId){
        Customer customer = customerRepository.getOne(customerId);
        return scheduleRepository.getAllByPetsIn(customer.getPets());
    }

}
