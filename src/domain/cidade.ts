
//---------------------------------------------------
/** Classe que guarda os dados de uma cidade */
//---------------------------------------------------

export class Cidade {
    id: number;
    nome: string;  // Adicione esta linha
    estado: string;
    uf: string;
    capital:boolean;

    constructor() {
        this.id = 0;
        this.nome = '';  // Inicialize a variável
        this.estado = '';
        this.uf = '';
        this.capital=false;
    }
}

