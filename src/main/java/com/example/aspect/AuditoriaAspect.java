package com.example.aspect;

import com.example.domain.annotations.AuditarConsultaCredito;
import com.example.services.AuditoriaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuditoriaAspect {

    private final AuditoriaService auditoriaService;

    @Around("@annotation(auditoria)")
    public Object auditarConsulta(ProceedingJoinPoint joinPoint, AuditarConsultaCredito auditoria) throws Throwable {
        String tipoConsulta = auditoria.tipoConsulta();
        String referencia = extrairReferencia(joinPoint);

        try {
            Object resultado = joinPoint.proceed();

            auditoriaService.publicarEventoConsulta(
                auditoriaService.criarEventoConsulta(referencia, "SUCESSO", null, tipoConsulta)
            );

            return resultado;

        } catch (Exception ex) {
            auditoriaService.publicarEventoConsulta(
                auditoriaService.criarEventoConsulta(referencia, "ERRO", ex.getMessage(), tipoConsulta)
            );
            throw ex;
        }
    }

    private String extrairReferencia(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        return args.length > 0 ? String.valueOf(args[0]) : "DESCONHECIDO";
    }
}