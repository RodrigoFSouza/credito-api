package com.example.domain.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaCreditoEvent {
    
    private String id;
    private String numeroNfse;
    private String numeroCredito;
    private String tipoConsulta;
    private LocalDateTime timestamp;
    private String usuario; 
    private String ipOrigem; 
    private String resultado;
    private String mensagemErro;

} 