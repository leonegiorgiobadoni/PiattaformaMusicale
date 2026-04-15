package com.example.piattaformamusicale.controller;

import com.example.piattaformamusicale.model.Utente;
import com.example.piattaformamusicale.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private UtenteService utenteService;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("utente", new Utente());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute Utente utente, Model model) {
        try {
            utenteService.registraUtente(utente);
            return "redirect:/login?registered";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }
}