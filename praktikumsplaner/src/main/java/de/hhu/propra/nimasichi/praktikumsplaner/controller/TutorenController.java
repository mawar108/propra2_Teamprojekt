package de.hhu.propra.nimasichi.praktikumsplaner.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TutorenController {

    public TutorenController() { }

    @GetMapping("/tutorenansicht")
    public String handleTutorenAnsicht() {
        return "/tutorenansicht";
    }

}
