import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { Credito, CreditoResponse } from '../models/credito.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CreditoService {
  private baseUrl = environment.apiBaseUrl; 

  constructor(private http: HttpClient) { }

  getCreditosByNfse(numeroNfse: string): Observable<Credito[]> {
    return this.http.get<Credito[]>(`${this.baseUrl}/creditos/${numeroNfse}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  getCreditoById(numeroCredito: string): Observable<Credito> {
    return this.http.get<Credito>(`${this.baseUrl}/creditos/credito/${numeroCredito}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'Ocorreu um erro desconhecido';
    
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Erro: ${error.error.message}`;
    } else {
      errorMessage = `Código: ${error.status}, Mensagem: ${error.message}`;
    }
    
    console.error(errorMessage);
    return throwError(errorMessage);
  }
} 