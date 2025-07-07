import { Routes } from '@angular/router';
import { CreditoListaComponent } from './components/credito-lista/credito-lista.component';
import { CreditoDetalhesComponent } from './components/credito-detalhes/credito-detalhes.component';

export const routes: Routes = [
  { path: 'creditos', component: CreditoListaComponent },
  { path: 'credito-detalhes/:numeroCredito', component: CreditoDetalhesComponent },
  { path: '', redirectTo: 'creditos', pathMatch: 'full' },
  { path: '**', redirectTo: 'creditos' }
];
