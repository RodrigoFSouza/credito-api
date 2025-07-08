package com.example.services;

import com.example.domain.events.ConsultaCreditoEvent;

public interface AuditoriaService {
    
    void publicarEventoConsulta(ConsultaCreditoEvent evento);

    ConsultaCreditoEvent criarEventoConsulta(String numeroNfse, String resultado, String mensagemErro, String tipoConsulta);
} 