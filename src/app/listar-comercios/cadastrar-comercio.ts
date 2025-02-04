import { Component, EventEmitter, Input, Output, OnInit } from '@angular/core';
import { ImportsModule } from '../imports';
import { Comercio } from '@domain/comercio';  // Certifique-se de que o domínio "comercio" esteja correto
import { ComercioService } from '@service/comercio-service'; // Corrigido para o Serviço de Comercio
import { MessageService } from 'primeng/api';
import { Cidade } from '@domain/cidade'; // Se precisar de cidades, importe também
import { ProjetoService } from '@service/projeto-service';
import { TipoComercio } from '@domain/comercio';

@Component({
  selector: 'cadastrar-comercio',
  templateUrl: 'cadastrar-comercio.html',
  standalone: true,
  imports: [ImportsModule],
  providers: [ComercioService] // Corrigido para o ComércioService
})
export class CadastrarComercio {

  //-------------------------------------------------------
  // Parâmetro de entrada para o componente
  //-------------------------------------------------------
  @Input() public comercio: Comercio = new Comercio();

  //-------------------------------------------------------
  // Evento lançado ao fechar a janela
  //-------------------------------------------------------
  @Output() private onClose: EventEmitter<boolean> = new EventEmitter<boolean>();

  //--------------------------------------------------------------
  /** Construtor com injeção de dependências */
  //--------------------------------------------------------------
  constructor(
    private comercioService: ComercioService,  // Serviço correto para comércio
    private projetoService: ProjetoService,
    private messageService: MessageService
  ) {}

  //--------------------------------------------------------------
  /** Propriedades para armazenar tipos de comércio e cidades */
  //--------------------------------------------------------------
  tiposComercio: any[] = []; // Alterado para array, já que é uma lista de objetos
  cidades: Cidade[] = []; // Inicialize com seus dados ou API

  //--------------------------------------------------------------
  /** Carregar os tipos de comércio e as cidades */
  //--------------------------------------------------------------
  ngOnInit(): void {
    this.carregarTiposComercio();
    this.carregarCidades();  // Carrega as cidades
  }

  //--------------------------------------------------------------
  /** Carregar tipos de comércio  */
  //--------------------------------------------------------------
  carregarTiposComercio(): void {
    this.comercioService.getTiposComercio().subscribe({
      next: (tipos) => {
        this.tiposComercio = tipos; // Atualiza a lista de tipos de comércio
      },
      error: (err) => {
        console.error('Erro ao carregar tipos de comércio:', err);
      },
    });
  }

  //--------------------------------------------------------------
  /** Carregar cidades da API */
  //--------------------------------------------------------------
  private carregarCidades(): void {
    // Aqui estamos chamando a API para carregar as cidades
    this.projetoService.pesquisarCidades().subscribe({
      next: (cidades: Cidade[]) => {
        this.cidades = cidades;  // Preenche o array de cidades com a resposta da API
      },
      error: (err) => {
        console.error('Erro ao carregar cidades:', err);
      },
    });
  }

  //--------------------------------------------------------------
  /** Método chamado ao clicar no botão 'salvar' */
  //--------------------------------------------------------------
  public salvar(): void {
    console.log('Antes de enviar:', this.comercio);
    console.log('Enviando comércio:', this.comercio); // 👈 Verifique o que está sendo enviado

    this.comercioService.salvar(this.comercio).subscribe({
        next: (result) => {
            console.log(result);
            this.messageService.add({ 
                severity: 'success', 
                summary: 'Info', 
                detail: `Comércio '${this.comercio.nome}' cadastrado com sucesso!` 
            });
        },
        error: (error) => {
            console.log(error);
            this.messageService.add({ 
                severity: 'error', 
                summary: 'Erro', 
                detail: `Comércio '${this.comercio.nome}' não foi salvo!` 
            });
        },
        complete: () => {
            this.cancelar();
            this.reloadPage();
        }
    });
}

  //--------------------------------------------------------------
  /** Método chamado ao clicar no botão 'cancelar' */
  //--------------------------------------------------------------
  public cancelar(): void {
    this.onClose.emit(false);
  }

  //--------------------------------------------------------------
  /** Método para recarregar a página */
  //--------------------------------------------------------------
  public reloadPage(): void {
    // Melhor usar uma estratégia de atualização do estado ou navegação
    setTimeout(() => {
      window.location.reload(); // Pode ser substituído por um método mais controlado
    }, 100);
  }
}
