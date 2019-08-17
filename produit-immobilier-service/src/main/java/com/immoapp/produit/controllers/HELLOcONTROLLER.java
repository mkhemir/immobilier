package com.immoapp.produit.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HELLOcONTROLLER {

    @GetMapping(value = "/hello")
    public String welcome(){
        return "hello, the service is up now";
    }
}
