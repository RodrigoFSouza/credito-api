import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { Credito, CreditoResponse } from '../models/credito.model';
import { environment } from '../../environments/environment';
import { ApiError } from '../models/api-error.model';
import { ApiErrorResponse } from '../models/api-error.model';

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

  private handleError(error: HttpErrorResponse): Observable<never> {
    let apiErrorResponse: ApiErrorResponse;
    
    if (error.error instanceof ErrorEvent) {
      apiErrorResponse = {
        message: `Erro de conexão: ${error.error.message}`,
        statusCode: 0,
        isClientError: false,
        isServerError: false
      };
    } else {
      const apiError: ApiError = error.error;
      
      apiErrorResponse = {
        message: apiError?.message || error.message || 'Erro desconhecido',
        statusCode: error.status,
        isClientError: error.status >= 400 && error.status < 500,
        isServerError: error.status >= 500
      };
    }
    
    console.log('Erro na API:' +  apiErrorResponse);
    return throwError(() => apiErrorResponse);
  }


} 