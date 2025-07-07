import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CreditoDetalhesComponent } from './components/credito-detalhes/credito-detalhes.component';
import { CreditoListaComponent } from './components/credito-lista/credito-lista.component';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    FormsModule,
    CreditoDetalhesComponent,
    CreditoListaComponent
  ]
})
export class AppComponent {

}
