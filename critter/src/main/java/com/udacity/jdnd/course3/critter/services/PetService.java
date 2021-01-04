package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Transactional
@Service
public class PetService {


    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    public Pet save(Pet pet, long ownerid){
        Customer customer = customerRepository.getOne(ownerid);
        pet.setCustomer(customer);
        pet = petRepository.save(pet);
        customer.addPet(pet);
        Customer cust = customerRepository.save(customer);
        return pet;
    }

    public Pet getPet(long petId){
        return petRepository.getOne(petId);
    }

    public List<Pet> getPets(){
        return petRepository.findAll();
    }

    public List<Pet> getPetsByOwner(long ownerId){
        return petRepository.findAll().stream().filter(rec -> rec.getCustomer().getId() == ownerId).collect(Collectors.toList());
    }

}
