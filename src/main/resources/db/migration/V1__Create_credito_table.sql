-- V1__Create_credito_table.sql
-- Criação da tabela credito
CREATE TABLE IF NOT EXISTS credito (
    id BIGSERIAL PRIMARY KEY,
    numero_credito VARCHAR(50) NOT NULL,
    numero_nfse VARCHAR(50) NOT NULL,
    data_constituicao DATE NOT NULL,
    valor_issqn DECIMAL(15,2) NOT NULL,
    tipo_credito VARCHAR(50) NOT NULL,
    simples_nacional BOOLEAN NOT NULL,
    aliquota DECIMAL(5,2) NOT NULL,
    valor_faturado DECIMAL(15,2) NOT NULL,
    valor_deducao DECIMAL(15,2) NOT NULL,
    base_calculo DECIMAL(15,2) NOT NULL
);

CREATE INDEX idx_credito_numero_nfse ON credito(numero_nfse);
CREATE INDEX idx_credito_data_constituicao ON credito(data_constituicao);
CREATE INDEX idx_credito_tipo_credito ON credito(tipo_credito);