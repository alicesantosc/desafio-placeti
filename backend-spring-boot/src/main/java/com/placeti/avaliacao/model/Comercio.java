package com.placeti.avaliacao.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//-------------------------------------------------
/** Entidade que guarda os dados de um comércio */
//-------------------------------------------------
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Comercio")
public class Comercio {

    


	public Comercio(String nome2, String nomeResponsavel, TipoComercio tipoComercio2, Cidade cidade2) {
		// TODO Auto-generated constructor stub
	}


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NOME", length = 100, nullable = false)
    private String nome;

    @Column(name = "NOME_RESPONSAVEL", length = 100, nullable = true)
    private String responsavel;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_COMERCIO", nullable = false)
    private TipoComercio tipoComercio = TipoComercio.PADARIA;
    
    @ManyToOne
    @JoinColumn(name = "cidade_id", nullable = false)
    private Cidade cidade;


    public enum TipoComercio {
        FARMÁCIA,
        PADARIA,
        POSTO_GASOLINA,
        LANCHONETE
    }


	public void setCidade(Cidade cidade2) {
		// TODO Auto-generated method stub
		
	}


	public Comercio(String nome2, String nomeResponsavel, TipoComercio tipoComercio2, Long cidadeId) {
		// TODO Auto-generated constructor stub
	}
}
