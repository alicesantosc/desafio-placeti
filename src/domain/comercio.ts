//---------------------------------------------------
/** Enum que define os tipos de Comércio */
//---------------------------------------------------
export enum TipoComercio {
    FARMACIA = 'FARMÁCIA',
    PADARIA = 'PADARIA',
    POSTO_GASOLINA = 'POSTO GÁSOLINA',
    LANCHONETE = 'LANCHONETE'
}

//---------------------------------------------------
/** Classe que guarda os dados de um comércio */
//---------------------------------------------------
export class Comercio {
    id: number;
    nome: string;
    nomeResponsavel: string;
    cidadeId: number;  // Renomeado para cidadeId
    tipoComercio: TipoComercio;

    constructor() {
        this.id = 0;
        this.nome = '';
        this.nomeResponsavel = '';
        this.tipoComercio = TipoComercio.FARMACIA;
        this.cidadeId = 0;  // Alterado para cidadeId
    }
}
