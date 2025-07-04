package com.example.domain.mappers;

import com.example.domain.dtos.CreditoDTO;
import com.example.domain.entities.Credito;
import org.springframework.stereotype.Component;

@Component
public class CreditoMapper {
    
    public CreditoDTO toDTO(Credito credito) {
        if (credito == null) {
            return null;
        }
        
        return new CreditoDTO(
            credito.getNumeroCredito(),
            credito.getNumeroNfse(),
            credito.getDataConstituicao(),
            credito.getValorIssqn(),
            credito.getTipoCredito(),
            credito.getSimplesNacional() ? "Sim" : "Não",
            credito.getAliquota(),
            credito.getValorFaturado(),
            credito.getValorDeducao(),
            credito.getBaseCalculo()
        );
    }
    
    public Credito toEntity(CreditoDTO creditoDTO) {
        if (creditoDTO == null) {
            return null;
        }

        Credito entity = Credito.builder()
                .numeroCredito(creditoDTO.getNumeroCredito())
                .numeroNfse(creditoDTO.getNumeroNfse())
                .dataConstituicao(creditoDTO.getDataConstituicao())
                .valorIssqn(creditoDTO.getValorIssqn())
                .tipoCredito(creditoDTO.getTipoCredito())
                .simplesNacional("Sim".equals(creditoDTO.getSimplesNacional()))
                .aliquota(creditoDTO.getAliquota())
                .valorFaturado(creditoDTO.getValorFaturado())
                .valorDeducao(creditoDTO.getValorDeducao())
                .baseCalculo(creditoDTO.getBaseCalculo())
                .build();

        return entity;
    }
}