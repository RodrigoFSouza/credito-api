import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Credito } from '../../models/credito.model';
import { ActivatedRoute, Router } from '@angular/router';
import { CreditoService } from '../../services/credito.service';

@Component({
  selector: 'app-credito-detalhes',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './credito-detalhes.component.html',
  styleUrls: ['./credito-detalhes.component.css']
})
export class CreditoDetalhesComponent {
  credito: Credito | null = null;
  loading: boolean = false;
  error: string = '';

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
      this.error = 'Número do crédito não encontrado';
      return;
    }

    this.loading = true;
    this.error = '';

    this.creditoService.getCreditoById(numeroCredito).subscribe({
      next: (credito) => {
        this.credito = credito;
        this.loading = false;
      },
      error: (error) => {
        this.error = error;
        this.loading = false;
      }
    });
  }


  voltarLista(): void {
    this.router.navigate(['/creditos']);
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

  
  formatarPercentual(valor: number): string {
    return valor.toFixed(2) + '%';
  }
} 