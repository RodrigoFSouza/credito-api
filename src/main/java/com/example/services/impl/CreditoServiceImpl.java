package com.example.services.impl;

import com.example.domain.annotations.AuditarConsultaCredito;
import com.example.domain.dtos.CreditoDTO;
import com.example.domain.entities.Credito;
import com.example.domain.exceptions.CreditoNotFoundException;
import com.example.domain.mappers.CreditoMapper;
import com.example.repositories.CreditoRepository;
import com.example.services.CreditoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreditoServiceImpl implements CreditoService {

    private final CreditoRepository creditoRepository;
    private final CreditoMapper creditoMapper;

    @Transactional(readOnly = true)
    @AuditarConsultaCredito(tipoConsulta = "NFSE")
    public List<CreditoDTO> buscarCreditosPorNfse(String numeroNfse) {
        List<Credito> creditos = creditoRepository.findByNumeroNfseOrderByDataConstituicaoDesc(numeroNfse);

        if (creditos.isEmpty()) {
            throw new CreditoNotFoundException("Nenhum crédito encontrado para a NFS-e: " + numeroNfse);
        }

        return creditos.stream()
                .map(creditoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @AuditarConsultaCredito(tipoConsulta = "Credito")
    public CreditoDTO buscarCreditoPorNumero(String numeroCredito) {
        Credito credito = creditoRepository.findByNumeroCredito(numeroCredito)
                .orElseThrow(() -> new CreditoNotFoundException("Crédito não encontrado com número: " + numeroCredito));

        return creditoMapper.toDTO(credito);
    }
}
