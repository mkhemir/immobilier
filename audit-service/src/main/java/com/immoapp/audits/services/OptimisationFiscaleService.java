package com.immoapp.audits.services;

import com.immoapp.audits.dtos.InformationBanqueDTO;
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
public class OptimisationFiscaleService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;

    private static final Logger logger = LoggerFactory.getLogger(ProduitImmobilierService.class);

    public List<InformationBanqueDTO> getInfoBanques(){
        logger.info("receiving the call from zuul...");
        List<ServiceInstance> instances = discoveryClient.getInstances("gateservice");
        ServiceInstance serviceInstance = instances.get(0);
        String baseUrl = serviceInstance.getUri().toString();
        baseUrl = baseUrl + "/api/optimisation/banques";
        ResponseEntity<List<InformationBanqueDTO>> restExchange =null;
        try {
            restExchange =
                    restTemplate.exchange(baseUrl, HttpMethod.GET, getHeaders(), new ParameterizedTypeReference<List<InformationBanqueDTO>>(){});
        }catch (Exception e){
            logger.info("-----------------------------------------------------"+serviceInstance.getHost()+serviceInstance.isSecure()+baseUrl);
            logger.info(e.getMessage());
        }
        return restExchange.getBody();
    }

    public ProduitImmobilierDTO getEstimationLoyer(long id){
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
