import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

export type AlertType = 'success' | 'warning' | 'danger' | 'info';

@Component({
  selector: 'app-mensagem-api',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './mensagem-api.component.html',
  styleUrls: ['./mensagem-api.component.scss']
})
export class MensagemApiComponent {
    @Input() type: AlertType = 'info';
    @Input() message: string = '';
    @Input() dismissible: boolean = true;
    @Input() show: boolean = true;
    @Output() dismissed = new EventEmitter<void>();
  
    close(): void {
      this.show = false;
      this.dismissed.emit();
    }
  
    get alertClasses(): string {
      const baseClasses = 'alert';
      const typeClass = `alert-${this.type}`;
      const dismissibleClass = this.dismissible ? 'alert-dismissible' : '';
      
      return `${baseClasses} ${typeClass} ${dismissibleClass}`.trim();
    }
  
    get alertIcon(): string {
      switch (this.type) {
        case 'success':
          return 'fas fa-check-circle';
        case 'warning':
          return 'fas fa-exclamation-triangle';
        case 'danger':
          return 'fas fa-times-circle';
        case 'info':
        default:
          return 'fas fa-info-circle';
      }
    }
} 