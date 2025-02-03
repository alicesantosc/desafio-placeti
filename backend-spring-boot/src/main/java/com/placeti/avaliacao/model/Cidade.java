package com.placeti.avaliacao.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

//-------------------------------------------------
/** Entidade que guarda os dados de uma cidade */
//-------------------------------------------------
@Data
@Entity
@Table(name = "Cidade")
public class Cidade {

    public Cidade(String nome2, String uf2, Boolean capital2) {
		// TODO Auto-generated constructor stub
	}
    
    public Cidade() {
        this.capital = false; // Garante que nunca será nulo
    }

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NOME", length = 100, nullable = false)
    private String nome;
    
    @Column(name = "UF", length = 100, nullable = false)
    private String uf;
    
    @Column(nullable = false)
    private Boolean capital;
    
    @OneToMany(mappedBy = "cidade", cascade = CascadeType.ALL)
    private List<Comercio> comercios;

    
   


}
