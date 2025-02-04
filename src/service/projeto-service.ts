import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { Cidade } from '@domain/cidade';
import { Observable } from 'rxjs';
import { environment } from 'src/app/environments/environment';

@Injectable({
  providedIn: 'root' // Torna o serviço disponível em toda a aplicação
})
export class ProjetoService {
  private apiUrl = `${environment.apiUrl}/cidades`; // Define a URL base da API

  private httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }) // Adiciona headers para JSON
  };

  constructor(private http: HttpClient) {}

  //------------------------------------------------
  /** Recupera a lista de cidades */
  //------------------------------------------------
  pesquisarCidades(): Observable<Cidade[]> {
    return this.http.get<Cidade[]>(this.apiUrl);
  }

  //------------------------------------------------
  /** Exclui a cidade informada */
  //------------------------------------------------
  excluir(cidade: Cidade): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${cidade.id}`);
  }

  //------------------------------------------------
  /** Salva a cidade informada */
  //------------------------------------------------
  salvar(cidade: Cidade): Observable<Cidade> {
    if (cidade.id) {
      // Se tiver ID, faz um update (PUT)
      console.log('Fazendo update');
      return this.http.put<Cidade>(`${this.apiUrl}/${cidade.id}`, cidade, this.httpOptions);
    } else {
      // Se não tiver ID, faz um insert (POST)
      console.log('Fazendo insert');
      return this.http.post<Cidade>(this.apiUrl, cidade, this.httpOptions);
    }
  }
}
