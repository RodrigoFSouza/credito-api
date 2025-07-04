package com.example.controllers;

import com.example.domain.dtos.CreditoDTO;
import com.example.services.CreditoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/creditos")
@Tag(name = "Crédito API")
public class CreditoController {
    private final CreditoService creditoService;

    @Operation(summary = "API para Buscar créditos pelo número da NFS-e")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Retorna OK com a listagem de créditos"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação desta api"),
            @ApiResponse(responseCode = "403", description = "Erro de autorização desta api"),
            @ApiResponse(responseCode = "404", description = "Recurso não foi encontrado")
    })
    @GetMapping("/{numeroNfse}")
    public ResponseEntity<List<CreditoDTO>> buscarCreditosPorNfse(@PathVariable String numeroNfse) {
        List<CreditoDTO> creditos = creditoService.buscarCreditosPorNfse(numeroNfse);
        return ResponseEntity.ok(creditos);
    }

    @Operation(summary = "API para buscar crédito pelo número do crédito")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Retorna OK com a listagem de créditos"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação desta api"),
            @ApiResponse(responseCode = "403", description = "Erro de autorização desta api"),
            @ApiResponse(responseCode = "404", description = "Recurso não foi encontrado")
    })
    @GetMapping("/credito/{numeroCredito}")
    public ResponseEntity<CreditoDTO> buscarCreditoPorNumero(@PathVariable String numeroCredito) {
        CreditoDTO credito = creditoService.buscarCreditoPorNumero(numeroCredito);
        return ResponseEntity.ok(credito);
    }
}
