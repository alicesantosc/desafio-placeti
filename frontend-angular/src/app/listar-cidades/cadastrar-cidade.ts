import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ImportsModule } from '../imports';
import { Cidade } from '@domain/cidade';
import { ProjetoService } from '@service/projeto-service';
import { MessageService } from 'primeng/api';
import { trigger, state, style, animate, transition } from '@angular/animations';


@Component({
  selector: 'cadastrar-cidade',
  templateUrl: 'cadastrar-cidade.html',
  standalone: true,
  imports: [ImportsModule],
  providers: [ProjetoService],// Registra o serviço no escopo do componente
  animations: [
    trigger('animacaoExemplo', [
      state('inicial', style({ opacity: 0 })),
      state('final', style({ opacity: 1 })),
      transition('inicial => final', [animate('500ms')])
    ])
  ]
})
export class CadastrarCidade {
  
  //-------------------------------------------------------
  // Parâmetro de entrada para o componente
  //-------------------------------------------------------
  @Input() public cidade: Cidade = new Cidade();

  //-------------------------------------------------------
  // Evento lançado ao fechar a janela
  //-------------------------------------------------------
  @Output() private onClose: EventEmitter<boolean> = new EventEmitter<boolean>();

  //--------------------------------------------------------------
  /** Construtor com injeção de dependências */
  //--------------------------------------------------------------
  constructor(
    private projetoService: ProjetoService,
    private messageService: MessageService
  ) {}

  //-------------------------------------------------------------------------------------
  /** Método chamado ao clicar no botão 'salvar' */
  //-------------------------------------------------------------------------------------
  public salvar(): void {
    console.log('Antes de enviar:', this.cidade);
    console.log('Enviando cidade:', this.cidade); // 👈 Verifique o que está sendo enviado

    this.projetoService.salvar(this.cidade).subscribe({
        next: (result) => {
            console.log(result);
            this.messageService.add({ severity: 'success', summary: 'Info', detail: `Cidade '${this.cidade.nome}' cadastrada com sucesso!` });
        },
        error: (error) => {
            console.log(error);
            this.messageService.add({ severity: 'error', summary: 'Erro', detail: `Cidade '${this.cidade.nome}' não foi salva!` });
        },
        complete: () => {
            this.cancelar();
            this.reloadPage();
        }
    });
}


  //-------------------------------------------------------------------------------------
  /** Método chamado ao clicar no botão 'cancelar' */
  //-------------------------------------------------------------------------------------
  public cancelar(): void {
    this.onClose.emit(false);
  }

  /** Método para recarregar a página */
  public reloadPage(): void {
    setTimeout(() => window.location.reload(), 100);
  }
} 