package com.immoapp.db.repository;

import com.immoapp.db.models.ProduitImmobilier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduitImmobilierRepository extends CrudRepository<ProduitImmobilier, Long> {

    ProduitImmobilier findById(Long aLong);
}
