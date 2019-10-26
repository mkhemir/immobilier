package com.immoapp.produit.controllers;


import com.immoapp.produit.clients.DiscoveryClientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="discovery")
public class DiscoveryController {
    @Autowired
    private DiscoveryClientServices discoveryClientServices;

    @RequestMapping(value="/eureka/services",method = RequestMethod.GET)
    public List<String> getEurekaServices() {

        return discoveryClientServices.getEurekaServices();
    }
}
