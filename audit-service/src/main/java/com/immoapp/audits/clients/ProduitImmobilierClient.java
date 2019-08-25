package com.immoapp.audits.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProduitImmobilierClient {
    @Autowired
    RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(ProduitImmobilierClient.class);

    public String getProduitImmobilier(){
    logger.info("receiving the call from zuul...");
        ResponseEntity<String> restExchange =
                restTemplate.exchange(
                        "http://gateservice/produitimmobilierservice/hello",
                        HttpMethod.GET,null,String.class
                        );
        return restExchange.getBody();
    }

}
