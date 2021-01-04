package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        if(scheduleDTO == null){
            throw new UnsupportedOperationException();
        }
        else{
            Schedule schedule = new Schedule();
            schedule.setSchedule(scheduleDTO.getDate());
            schedule.setEmployeeSkillSet(scheduleDTO.getActivities());

            return convertScheduleEntitytoDTO(scheduleService.save(schedule, scheduleDTO.getPetIds(), scheduleDTO.getEmployeeIds()));
        }
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> scheduleList = scheduleService.getAllSchedules();
        if(scheduleList == null){
            throw new UnsupportedOperationException();
        }
        else{
            return scheduleList.stream().map(this::convertScheduleEntitytoDTO).collect(Collectors.toList());
        }
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        if(petId < 0){
            throw new UnsupportedOperationException();
        }
        else{
            return scheduleService.getScheduleForPet(petId).stream().map(this::convertScheduleEntitytoDTO).collect(Collectors.toList());
        }
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        if(employeeId < 0){
            throw new UnsupportedOperationException();
        }
        else{
            return scheduleService.getScheduleForEmployee(employeeId).stream().map(this::convertScheduleEntitytoDTO).collect(Collectors.toList());
        }
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        if(customerId < 0){
            throw new UnsupportedOperationException();
        }
        else{
            return scheduleService.getScheduleForCustomer(customerId).stream().map(this::convertScheduleEntitytoDTO).collect(Collectors.toList());
        }
    }

    public ScheduleDTO convertScheduleEntitytoDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setPetIds(schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
        scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
        scheduleDTO.setActivities(schedule.getEmployeeSkillSet());
        scheduleDTO.setDate(schedule.getSchedule());

        return scheduleDTO;
    }
}
