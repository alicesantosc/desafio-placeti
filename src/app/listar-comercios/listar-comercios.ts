import { Component } from '@angular/core';
import { ImportsModule } from '../imports';
import { Comercio } from '@domain/comercio';  // Certifique-se de que o domínio "comercio" esteja correto

import { CadastrarComercio } from 'src/app/listar-comercios/cadastrar-comercio';
import { MessageService } from 'primeng/api';
import {ComercioService} from '@service/comercio-service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { trigger, transition, style, animate } from '@angular/animations';
import { RouterModule } from '@angular/router';


/** Tela para listar comércios */
@Component({
  selector: 'listar-comercio',
  templateUrl: 'listar-comercio.html',
  standalone: true,
  imports: [ImportsModule, CadastrarComercio,RouterModule],
  providers: [ComercioService, MessageService],
  animations: [
    trigger('fadeIn', [
      transition(':enter', [
        style({ opacity: 0 }),  // Define o estilo inicial
        animate('500ms', style({ opacity: 1 }))  // Anima para 1 (totalmente visível) ao longo de 500ms
      ])
    ])
  ]
})
export class ListarComercio {
  //-------------------------------------------------------
  // Lista de comércios, exibida na tabela
  //-------------------------------------------------------
  listaComercios!: Comercio[]; // Initialize as an empty array
  

  //-------------------------------------------------------------
  // Atributo que guarda o comércio selecionado na tabela
  //-------------------------------------------------------------
  comercioSelecionado: Comercio = new Comercio();

  //-------------------------------------------------------------
  // Flag usada para mostrar/esconder a janela de cadastro
  //-------------------------------------------------------------
  mostraJanelaCadastro: boolean = false;

  //--------------------------------------------------------------
  /** Construtor. Recebe os services usados pelo componente */
  //--------------------------------------------------------------
  constructor(private service: ComercioService, private messageService: MessageService) {}

  //-------------------------------------------------------------------------------------
  /** Inicialização do componente. Recupera a lista de comércios para exibir na tabela */
  //-------------------------------------------------------------------------------------
  ngOnInit() {
    this.pesquisarComercios();
  }

  //-------------------------------------------------------------------------------------
  /** Método chamado para recuperar comércios para a tabela */
  //-------------------------------------------------------------------------------------
  private pesquisarComercios(): void {
    this.service.pesquisarComercios().subscribe(
      (dados: Comercio[]) => {
        if (dados) {
          this.listaComercios = dados;
          console.log('Comércios carregados: ', this.listaComercios);
        } else {
          console.error('Dados inválidos recebidos do backend.');
          this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Dados inválidos recebidos do backend.' });
        }
      },
      (erro) => {
        console.error('Erro ao carregar comércios: ', erro);
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Falha ao carregar comércios.' });
      }
    );
  }
  

  //-------------------------------------------------------------------------------------
  /** Método chamado ao clicar no botão 'Novo Comércio' */
  //-------------------------------------------------------------------------------------
  public abreJanelaParaCadastrarNovoComercio() {
    // Define o comércio selecionado como um novo comércio
    this.comercioSelecionado = new Comercio();

    // Exibe a janela de cadastro
    this.mostraJanelaCadastro = true;
  }

  //-------------------------------------------------------------------------------------
  /** Método chamado ao clicar no botão 'Alterar' */
  //-------------------------------------------------------------------------------------
  public abreJanelaParaAlterarComercio(comercio: Comercio): void {
    // Copia os dados do comércio selecionado para um novo comércio
    this.comercioSelecionado = new Comercio();
    this.comercioSelecionado.id = comercio.id;
    this.comercioSelecionado.nome = comercio.nome;
    this.comercioSelecionado.nomeResponsavel = comercio.nomeResponsavel;
    this.comercioSelecionado.tipoComercio = comercio.tipoComercio;

    // Exibe a janela de cadastro
    this.mostraJanelaCadastro = true;
  }

  //-------------------------------------------------------------------------------------
  /** Método chamado ao clicar no botão 'Excluir' */
  //-------------------------------------------------------------------------------------
  public excluir(comercio: Comercio) {
    // Chama o backend para excluir o comércio selecionado
    this.service.excluir(comercio).subscribe((retorno) => {
      this.messageService.add({ severity: 'success', summary: 'Info', detail: `Comércio '${comercio.nome}' excluído com sucesso!` });

      // Atualiza a lista de comércios
      setTimeout(() => this.pesquisarComercios(), 100);
    });
  }

  //-------------------------------------------------------------------------------------
  /** Método chamado quando a janela de cadastro é fechada */
  //-------------------------------------------------------------------------------------
  public fechaJanelaCadastro(salvou: boolean): void {
    // Esconde a janela de cadastro
    this.mostraJanelaCadastro = false;

    // Se salvou, atualiza a lista de comércios
    if (salvou) {
      setTimeout(() => this.pesquisarComercios(), 100);
    }
  }
}
