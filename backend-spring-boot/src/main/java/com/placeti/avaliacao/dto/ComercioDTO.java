package com.placeti.avaliacao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.placeti.avaliacao.model.Comercio;
import com.placeti.avaliacao.model.Comercio.TipoComercio;
import com.placeti.avaliacao.model.Cidade;
import com.placeti.avaliacao.repository.CidadeRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO que guarda os dados de um comércio */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComercioDTO {

    private Long id;
    private String nome;

    @JsonProperty("nome_responsavel")
    private String nomeResponsavel;

    @JsonProperty("tipo_comercio")
    private TipoComercio tipoComercio;

    @JsonProperty("cidade_id")
    private Long cidadeId;

    /** Converte um DTO para uma entidade Comercio */
    public Comercio toEntity(CidadeRepository cidadeRepository) {
        Comercio comercio = new Comercio();
        comercio.setId(this.id);
        comercio.setNome(this.nome);
        comercio.setResponsavel(this.nomeResponsavel);
        comercio.setTipoComercio(this.tipoComercio);

        // Verifica se o ID da cidade foi informado
        if (this.cidadeId != null) {
            Cidade cidade = cidadeRepository.findById(this.cidadeId)
                    .orElseThrow(() -> new IllegalArgumentException("Cidade não encontrada com o ID: " + this.cidadeId));
            comercio.setCidade(cidade);
        } else {
            throw new IllegalArgumentException("O ID da cidade não pode ser nulo!");
        }

        return comercio;
    }

    /** Converte uma entidade Comercio para um DTO */
    public static ComercioDTO fromEntity(Comercio comercio) {
        if (comercio == null) {
            return null;
        }

        return new ComercioDTO(
            comercio.getId(),
            comercio.getNome(),
            comercio.getResponsavel(),
            comercio.getTipoComercio(),
            comercio.getCidade() != null ? comercio.getCidade().getId() : null
        );
    }
}
