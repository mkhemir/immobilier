package com.immoapp.audits.services;

import com.immoapp.audits.dtos.ProduitImmobilierDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
public class DbService {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;

    private static final Logger logger = LoggerFactory.getLogger(ProduitImmobilierService.class);

    public List<ProduitImmobilierDTO> getProduitImmobilier(){
        logger.info("receiving the call from zuul...");
        List<ServiceInstance> instances = discoveryClient.getInstances("gateservice");
        ServiceInstance serviceInstance = instances.get(0);
        String baseUrl = serviceInstance.getUri().toString();
        baseUrl = baseUrl + "/api/db/produits";
        ResponseEntity<List<ProduitImmobilierDTO>> restExchange =null;
        try {
            restExchange =
                    restTemplate.exchange(baseUrl, HttpMethod.GET, getHeaders(), new ParameterizedTypeReference<List<ProduitImmobilierDTO>>(){});
        }catch (Exception e){
            logger.info("-----------------------------------------------------"+serviceInstance.getHost()+serviceInstance.isSecure()+baseUrl);
            logger.info(e.getMessage());
        }
        return restExchange.getBody();
    }

    public ProduitImmobilierDTO getProduitImmobilierById(long id){
        logger.info("receiving the call from zuul...");
        List<ServiceInstance> instances = discoveryClient.getInstances("gateservice");
        ServiceInstance serviceInstance = instances.get(0);
        String baseUrl = serviceInstance.getUri().toString();
        baseUrl = baseUrl + "/api/db/produit/{id}";
        ResponseEntity<ProduitImmobilierDTO> restExchange =null;
        try {
            restExchange =restTemplate.exchange(
                    baseUrl,
                    HttpMethod.GET,
                    null, ProduitImmobilierDTO.class, id);
                  //  restTemplate.exchange(baseUrl, HttpMethod.GET, getHeaders(), new ParameterizedTypeReference<ProduitImmobilierDTO>(){});
        }catch (Exception e){

            logger.info(e.getMessage());
        }
        logger.info("-----------------------------------------------------"+restExchange.getBody());
        return restExchange.getBody();
    }

    private static HttpEntity<?> getHeaders() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }
}
