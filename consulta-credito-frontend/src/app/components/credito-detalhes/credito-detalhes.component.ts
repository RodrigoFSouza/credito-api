import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Credito } from '../../models/credito.model';
import { ActivatedRoute, Router } from '@angular/router';
import { CreditoService } from '../../services/credito.service';
import { AlertType, MensagemApiComponent } from '../mensagem-api.component';
import { ApiErrorResponse } from '../../models/api-error.model';

@Component({
  selector: 'app-credito-detalhes',
  standalone: true,
  imports: [CommonModule, MensagemApiComponent],
  templateUrl: './credito-detalhes.component.html',
  styleUrls: ['./credito-detalhes.component.css']
})
export class CreditoDetalhesComponent {
  credito: Credito | null = null;
  loading: boolean = false;
  errorMessage: string = '';
  errorStatus: number | null = null;

  // Propriedades para o component de alerta
  showAlert: boolean = false;
  alertMessage: string = '';
  alertType: AlertType = 'info';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private creditoService: CreditoService
  ) { }

  ngOnInit(): void {
    this.carregarCredito(); 
  }

  carregarCredito(): void {
    const numeroCredito = this.route.snapshot.paramMap.get('numeroCredito');
    
    if (!numeroCredito) {
      this.errorMessage = 'Número do crédito não encontrado';
      this.errorStatus = 400;
      return;
    }

    this.loading = true;
    this.errorMessage = '';
    this.errorStatus = null;

    this.creditoService.getCreditoById(numeroCredito).subscribe({
      next: (credito) => {
        this.credito = credito;
        this.loading = false;
      },
      error: (apiError: ApiErrorResponse) => {
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

  voltarLista(): void {
    this.router.navigate(['/creditos']);
  }
} 