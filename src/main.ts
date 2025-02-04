import { bootstrapApplication } from '@angular/platform-browser';
import { provideRouter, Routes } from '@angular/router';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { ListarCidades } from './app/listar-cidades/listar-cidades';
import { HomeComponent } from 'src/app/home/home.component';

import { ListarComercio } from './app/listar-comercios/listar-comercios';

// Defina as rotas
const routes: Routes = [
    { path: '', component: HomeComponent, children: [  // Home agora gerencia as rotas internas
      { path: 'cidades', component: ListarCidades },
      { path: 'comercios', component: ListarComercio }
    ]}
  ];


bootstrapApplication(HomeComponent, {
    providers: [provideRouter(routes),  provideAnimationsAsync()]
  }).catch(err => console.error(err));