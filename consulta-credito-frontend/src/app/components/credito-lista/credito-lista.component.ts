import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Credito } from '../../models/credito.model';
import { Router } from '@angular/router';
import { CreditoService } from '../../services/credito.service';
import { FormsModule } from '@angular/forms';
import { MensagemApiComponent, AlertType } from '../mensagem-api.component';
import { ApiErrorResponse } from '../../models/api-error.model';

@Component({
  selector: 'app-credito-lista',
  standalone: true,
  imports: [CommonModule, FormsModule, MensagemApiComponent],
  templateUrl: './credito-lista.component.html',  
  styleUrls: ['./credito-lista.component.css']
})
export class CreditoListaComponent {
  creditos: Credito[] = [];
  numeroNfse: string = '';
  loading: boolean = false;
  errorMessage: string = '';
  errorStatus: number | null = null;

  showAlert: boolean = false;
  alertMessage: string = '';
  alertType: AlertType = 'info';

  constructor(
    private creditoService: CreditoService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  buscarCreditos(): void {
    if (this.loading) return;

    if (!this.numeroNfse.trim()) {
      this.errorMessage = 'Por favor, informe o número da NFS-e';
      this.errorStatus = 400;
      return;
    }

    this.loading = true;
    this.errorMessage = '';
    this.errorStatus = null;
    this.creditos = [];

    this.creditoService.getCreditosByNfse(this.numeroNfse).subscribe({
      next: (creditos: Credito[]) => {
        this.creditos = creditos;
        this.loading = false;
        this.numeroNfse = '';        
        
        if (creditos.length === 0) {
          this.showAlertMessage('warning', 'Nenhum crédito encontrado para esta NFS-e')
        } 
      },
      error: (apiError: ApiErrorResponse) => {
        this.numeroNfse = '';
        this.loading = false;
        this.handleApiError(apiError);
      }
    });
  }

  private handleApiError(apiError: ApiErrorResponse): void {
    if (apiError.isClientError) {
      this.showAlertMessage('warning', apiError.message);
    } else if (apiError.isServerError) {      
      this.showAlertMessage('danger', apiError.message);
    } else {
      this.showAlertMessage('danger', apiError.message);
    }
  }

  private showAlertMessage(type: AlertType, message: string): void {
    this.alertType = type;
    this.alertMessage = message;
    this.showAlert = true;
  }

  hideAlert(): void {
    this.showAlert = false;
    this.alertMessage = '';
  }

  onAlertDismissed(): void {
    this.hideAlert();
  }

  verDetalhes(numeroCredito: string): void {
    this.router.navigate(['/credito-detalhes', numeroCredito]);
  }
} 