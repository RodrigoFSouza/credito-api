package com.example.repositories;

import com.example.domain.entities.Credito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreditoRepository extends JpaRepository<Credito, Long> {

    @Query("SELECT c FROM Credito c WHERE c.numeroNfse = :numeroNfse ORDER BY c.dataConstituicao DESC")
    List<Credito> findByNumeroNfseOrderByDataConstituicaoDesc(@Param("numeroNfse") String numeroNfse);
    Optional<Credito> findByNumeroCredito(String numeroCredito);

}
