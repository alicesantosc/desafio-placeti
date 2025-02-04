import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListarCidades } from './listar-cidades/listar-cidades';  // Importando o componente ListarCidades
import { trigger, state, style, animate, transition } from '@angular/animations';

const routes: Routes = [
  { path: 'listar-cidades', component: ListarCidades },  // Definindo a rota
  { path: '', redirectTo: '/listar-cidades', pathMatch: 'full' },  // Rota padrão redirecionando para listar-cidades
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],  // Inicializando o roteador com as rotas definidas
  exports: [RouterModule]  // Exportando o módulo para uso em outras partes da aplicação
})
export class AppRoutingModule {}
