package com.udacity.jdnd.course3.critter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Dummy controller class to verify installation success. Do not use for
 * your project work.
 */
@RestController
public class CritterController {

    @GetMapping("/test")
    public String test(){
        return "Critter Starter installed successfully";
    }
}
