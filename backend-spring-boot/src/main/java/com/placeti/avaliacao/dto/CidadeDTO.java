package com.placeti.avaliacao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.placeti.avaliacao.model.Cidade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//-------------------------------------------------
/** DTO que guarda os dados de uma cidade */
//-------------------------------------------------
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CidadeDTO {

    private Long id;
    private String nome;
    private String uf;

    @JsonProperty("capital")
    private Boolean capital;

    //-----------------------------------------------
    /** Converte uma entidade Cidade para um DTO */
    //-----------------------------------------------
    public static CidadeDTO toDTO(Cidade cidade) {
        if (cidade == null) {
            return null;
        }
        return new CidadeDTO(cidade.getId(), cidade.getNome(), cidade.getUf(), cidade.getCapital());
    }

    //-----------------------------------------------
    /** Converte um DTO para uma entidade Cidade */
    //-----------------------------------------------
    public Cidade toEntity() {
        Cidade cidade = new Cidade();
        cidade.setId(this.id);
        cidade.setNome(this.nome);
        cidade.setUf(this.uf);
        
       
        cidade.setCapital(this.capital != null ? this.capital : false);

        return cidade;
    }
    
    
}
