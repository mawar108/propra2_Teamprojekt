package de.hhu.propra.nimasichi.praktikumsplaner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TutorenController {


    @GetMapping("/tutorenansicht")
    public String handleTutorenAnsicht() {
        return "lol";
    }

}
