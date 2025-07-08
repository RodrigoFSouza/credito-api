package com.example.services.impl;

import com.example.domain.events.ConsultaCreditoEvent;
import com.example.services.AuditoriaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditoriaServiceImpl implements AuditoriaService {

    private final KafkaTemplate<String, ConsultaCreditoEvent> kafkaTemplate;
    
    private static final String TOPIC_AUDITORIA = "auditoria-consultas-credito";

    @Override
    public void publicarEventoConsulta(ConsultaCreditoEvent evento) {
        try {
            SendResult<String, ConsultaCreditoEvent> result = kafkaTemplate
                    .send(TOPIC_AUDITORIA, evento.getId(), evento)
                    .get();
            
            log.info("Evento de auditoria publicado com sucesso: {}", result.getRecordMetadata());
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            log.error("Erro ao tentar publicar evento de auditoria: {}", e.getMessage(), e);
        }
    }

    @Override
    public ConsultaCreditoEvent criarEventoConsulta(String referencia, String resultado, String mensagemErro, String tipoConsulta) {
        return ConsultaCreditoEvent.builder()
                .id(UUID.randomUUID().toString())
                .numeroNfse("NFSE".equalsIgnoreCase(tipoConsulta) ? referencia : null)
                .numeroCredito("CREDITO".equalsIgnoreCase(tipoConsulta) ? referencia : null)
                .tipoConsulta(tipoConsulta.toUpperCase())
                .timestamp(LocalDateTime.now())
                .usuario("SISTEMA") // TODO: quando tiver implementação de segurança adicionar usuário
                .ipOrigem("N/A") // TODO: quando tiver para pegar ip da request
                .resultado(resultado)
                .mensagemErro(mensagemErro)
                .build();
    }
} 