package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    CustomerService customerService;

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        if(customerDTO == null){
            throw new UnsupportedOperationException();
        }
        else{
            Customer customer = new Customer();
            customer.setName(customerDTO.getName());
            customer.setPhonenumber(customerDTO.getPhoneNumber());
            customer.setNotes(customerDTO.getNotes());
            List<Long> petIds = customerDTO.getPetIds();
            return convertCustomerEntityToDTO(customerService.save(customer, petIds));
        }
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customerList = customerService.getAllcustomers();
        return customerList.stream().map(this::convertCustomerEntityToDTO).collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        if(petId < 0){
            throw new UnsupportedOperationException();
        }
        else{
            return convertCustomerEntityToDTO(customerService.getOwnerByPet(petId));
        }
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        if(employeeDTO == null){
            throw new UnsupportedOperationException();
        }
        else{
           Employee employee = new Employee(employeeDTO.getId(), employeeDTO.getName(), employeeDTO.getSkills(), employeeDTO.getDaysAvailable());
           return convertEmployeeEntityToDTO(employeeService.save(employee));
        }
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        if (employeeId < 0){
            throw new UnsupportedOperationException();
        }
        else{
            return convertEmployeeEntityToDTO(employeeService.getEmployee(employeeId));
        }
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        if(daysAvailable == null || employeeId < 0){
            throw new UnsupportedOperationException();
        }
        else{
            employeeService.setAvailability(daysAvailable, employeeId);
        }
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        if(employeeDTO == null){
            throw new UnsupportedOperationException();
        }
        else{
            List<Employee> employeeList = employeeService.findEmployeesForService(employeeDTO.getDate().getDayOfWeek(), employeeDTO.getSkills());
            return employeeList.stream().map(this::convertEmployeeEntityToDTO).collect(Collectors.toList());
        }
    }

    //helper - conversion methods
    private CustomerDTO convertCustomerEntityToDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setPhoneNumber(customer.getPhonenumber());
        customerDTO.setNotes(customer.getNotes());

        //map Pet type list to Petid type list
        customerDTO.setPetIds(customer.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
        return customerDTO;
    }

    private EmployeeDTO convertEmployeeEntityToDTO(Employee employee){
        EmployeeDTO empDTO = new EmployeeDTO();
        empDTO.setId(employee.getId());
        empDTO.setName(employee.getName());
        empDTO.setSkills(employee.getEmployeeSkills());
        empDTO.setDaysAvailable(employee.getEmployeeAvailabilitySet());

        return empDTO;
    }

}
