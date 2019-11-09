package com.immoapp.db.services;


import com.immoapp.db.dtos.ProduitImmobilierDTO;
import com.immoapp.db.models.ImageProduit;
import com.immoapp.db.models.ProduitImmobilier;
import com.immoapp.db.repository.ProduitImmobilierRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProduitImmobilierService {

    @Autowired
    ProduitImmobilierRepository produitImmobilierRepository;

    @Autowired
    ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(ProduitImmobilierService.class);

    public List<ProduitImmobilierDTO> findAllProducts() {
        return StreamSupport.stream(produitImmobilierRepository.findAll().spliterator(), false)
                .map(p -> modelMapper.map(p, ProduitImmobilierDTO.class))
                .collect(Collectors.toList());
    }

    public void saveProduct(ProduitImmobilierDTO produitImmobilierDTO){
        ProduitImmobilier produitImmobilier = modelMapper.map(produitImmobilierDTO , ProduitImmobilier.class);
        produitImmobilier.setImages(new ArrayList<ImageProduit>());
        Arrays.stream(produitImmobilierDTO.getFiles()).forEach(f -> {
            ImageProduit image = new ImageProduit();
            image.setName(f.getName());
            try {
                image.setFile(f.getBytes());
            }
            catch (IOException e){};
            produitImmobilier.getImages().add(image);
        });
         produitImmobilierRepository.save(produitImmobilier);
    }

    public ProduitImmobilierDTO getProduitImmobilier(long id){
        ProduitImmobilier produitImmobilier = produitImmobilierRepository.findById(id);
        logger.info("++++++++++++++"+produitImmobilier.getAdresse());
        return modelMapper.map(produitImmobilier , ProduitImmobilierDTO.class);
    }
}
