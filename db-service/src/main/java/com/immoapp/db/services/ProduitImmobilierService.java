package com.immoapp.db.services;


import com.immoapp.db.dtos.ProduitImmobilierDTO;
import com.immoapp.db.repository.ProduitImmobilierRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProduitImmobilierService {

    @Autowired
    ProduitImmobilierRepository produitImmobilierRepository;

    @Autowired
    ModelMapper modelMapper;

    public List<ProduitImmobilierDTO> findAllProducts() {
        return produitImmobilierRepository.findAll().stream()
                .map(p -> modelMapper.map(p, ProduitImmobilierDTO.class))
                .collect(Collectors.toList());
    }
}
