import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/app/environments/environment';
import { Comercio } from '@domain/comercio';

@Injectable({
  providedIn: 'root' // Torna o serviço disponível em toda a aplicação
})




export class ComercioService {
  private apiUrl = `${environment.apiUrl}/comercios`; // Define a URL base da API

  private httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }) // Adiciona headers para JSON
  };

  constructor(private http: HttpClient) {}

  //------------------------------------------------
  /** Recupera a lista de comércios */
  //------------------------------------------------
  pesquisarComercios(): Observable<Comercio[]> {
    return this.http.get<Comercio[]>(this.apiUrl);
  }

  //------------------------------------------------
  /** Pesquisa comércios por cidade */
  //------------------------------------------------
  pesquisarComerciosPorCidade(cidadeId: number): Observable<Comercio[]> {
    return this.http.get<Comercio[]>(`${this.apiUrl}/cidade/${cidadeId}`);
  }

  //------------------------------------------------
  /** Exclui o comércio informado */
  //------------------------------------------------
  excluir(comercio: Comercio): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${comercio.id}`);
  }

  //------------------------------------------------
  /** Salva o comércio informado */
  //------------------------------------------------
  salvar(comercio: Comercio): Observable<Comercio> {
    if (comercio.id) {
      // Se tiver ID, faz um update (PUT)
      console.log('Fazendo update');
      return this.http.put<Comercio>(`${this.apiUrl}/${comercio.id}`, comercio, this.httpOptions);
    } else {
      // Se não tiver ID, faz um insert (POST)
      console.log('Fazendo insert');
      return this.http.post<Comercio>(this.apiUrl, comercio, this.httpOptions);
    }
  }

  // Método para obter os tipos de comércio
  getTiposComercio(): Observable<string[]> {
    return this.http.get<string[]>(this.apiUrl);
  }

  
}
