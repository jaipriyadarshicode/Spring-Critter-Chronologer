package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;


    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        if(petDTO == null){
            throw new UnsupportedOperationException();
        }
        else{
            Pet pet = new Pet();
            //pet.setId(petDTO.getId());
            pet.setPettype(petDTO.getType());
            pet.setPetname(petDTO.getName());
            pet.setBirthdate(petDTO.getBirthDate());
            pet.setNotes(petDTO.getNotes());

            return convertEntityToPetDTO(petService.save(pet, petDTO.getOwnerId()));
        }
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        if(petId < 0){
            throw new UnsupportedOperationException();
        }
        else{
            return convertEntityToPetDTO(petService.getPet(petId));
        }
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> petList = petService.getPets();
        if (petList == null){
            throw new UnsupportedOperationException();
        }
        else{
            return petList.stream().map(this::convertEntityToPetDTO).collect(Collectors.toList());
        }

    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        if(ownerId < 0){
            throw new UnsupportedOperationException();
        }
        else{
            return petService.getPetsByOwner(ownerId).stream().map(this::convertEntityToPetDTO).collect(Collectors.toList());
        }
    }

    public PetDTO convertEntityToPetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setName(pet.getPetname());
        petDTO.setType(pet.getPettype());
        petDTO.setNotes(pet.getNotes());
        petDTO.setBirthDate(pet.getBirthdate());
        petDTO.setOwnerId(pet.getCustomer().getId());

        return petDTO;
    }
}
