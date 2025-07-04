package com.example.services;

import com.example.domain.dtos.CreditoDTO;

import java.util.List;

public interface CreditoService {

    List<CreditoDTO> buscarCreditosPorNfse(String numeroNfse);

    CreditoDTO buscarCreditoPorNumero(String numeroCredito);
}
