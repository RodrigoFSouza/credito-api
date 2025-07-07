export interface Credito {
  id?: number;
  numeroCredito: string;
  numeroNfse: string;
  dataConstituicao: string;
  valorIssqn: number;
  tipoCredito: string;
  simplesNacional: string;
  aliquota: number;
  valorFaturado: number;
  valorDeducao: number;
  baseCalculo: number;
}

export interface CreditoResponse {
  timestamp: string;
  status: number;
  error: string;
  message: string;
  path: string;
} 