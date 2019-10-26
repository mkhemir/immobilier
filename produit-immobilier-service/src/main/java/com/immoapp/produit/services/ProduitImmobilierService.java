package com.immoapp.produit.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Component
public class ProduitImmobilierService {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;

    private static final Logger logger = LoggerFactory.getLogger(ProduitImmobilierService.class);

    public String getProduitImmobilier(){
    logger.info("receiving the call from zuul...");
        List<ServiceInstance> instances = discoveryClient.getInstances("gateservice");
        ServiceInstance serviceInstance = instances.get(0);
        String baseUrl = serviceInstance.getUri().toString();
        baseUrl = baseUrl + "api/produit/hello";
        ResponseEntity<String> restExchange =null;
        try {
           restExchange =
                    restTemplate.exchange(baseUrl, HttpMethod.GET, getHeaders(), String.class);
        }catch (Exception e){
            logger.info("-----------------------------------------------------"+serviceInstance.getHost()+serviceInstance.isSecure()+baseUrl);
            logger.info(e.getMessage());
        }
        return restExchange.getBody();
    }

    private static HttpEntity<?> getHeaders() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }

}
