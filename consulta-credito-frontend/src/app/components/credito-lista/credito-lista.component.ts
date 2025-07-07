import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { Credito } from '../../models/credito.model';
import { Router } from '@angular/router';
import { CreditoService } from '../../services/credito.service';


@Component({
  selector: 'app-credito-lista',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatChipsModule, MatButtonModule, MatIconModule],
  templateUrl: './credito-lista.component.html',  
  styleUrls: ['./credito-lista.component.css']
})
export class CreditoListaComponent {
  creditos: Credito[] = [];
  numeroNfse: string = '';
  loading: boolean = false;
  error: string = '';

  constructor(
    private creditoService: CreditoService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }


  buscarCreditos(): void {
    if (!this.numeroNfse.trim()) {
      this.error = 'Por favor, informe o número da NFS-e';
      return;
    }

    this.loading = true;
    this.error = '';
    this.creditos = [];

    this.creditoService.getCreditosByNfse(this.numeroNfse).subscribe({
      next: (creditos) => {
        this.creditos = creditos;
        this.loading = false;
        if (creditos.length === 0) {
          this.error = 'Nenhum crédito encontrado para esta NFS-e';
        }
      },
      error: (error) => {
        this.error = error;
        this.loading = false;
      }
    });
  }


  verDetalhes(numeroCredito: string): void {
    this.router.navigate(['/credito-detalhes', numeroCredito]);
  }

  formatarMoeda(valor: number): string {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL'
    }).format(valor);
  }

  formatarData(data: string): string {
    return new Date(data).toLocaleDateString('pt-BR');
  }
} 