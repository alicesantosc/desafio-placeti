import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'; // Importação do módulo de animações
import { AppComponent } from './app.component';

@NgModule({
  declarations: [
    AppComponent,
    // outros componentes
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule, // Adicione o módulo de animações aqui
    // outros módulos
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}