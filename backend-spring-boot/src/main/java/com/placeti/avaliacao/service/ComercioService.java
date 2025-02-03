package com.placeti.avaliacao.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.placeti.avaliacao.dto.ComercioDTO;
import com.placeti.avaliacao.model.Cidade;
import com.placeti.avaliacao.model.Comercio;
import com.placeti.avaliacao.model.Comercio.TipoComercio;
import com.placeti.avaliacao.repository.CidadeRepository;
import com.placeti.avaliacao.repository.ComercioRepository;

@Service
public class ComercioService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ComercioRepository comercioRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    // ---------------------------------------------------------
    /** Método que busca um comércio pelo seu ID */
    // ---------------------------------------------------------
    public ComercioDTO pesquisarComercio(Long id) {
        logger.info("Pesquisando comércio com ID: {}", id);
        return comercioRepository.findById(id).map(comercio -> {
            logger.info("Comércio encontrado: {}", comercio.getNome());
            return ComercioDTO.fromEntity(comercio); // Substituído por fromEntity
        }).orElseGet(() -> {
            logger.warn("Nenhum comércio encontrado com ID: {}", id);
            return null;
        });
    }

    // ---------------------------------------------------------
    /** Método que retorna todos os comércios cadastrados */
    // ---------------------------------------------------------
    public List<ComercioDTO> pesquisarComercios() {
        logger.info("Buscando todos os comércios cadastrados...");
        List<ComercioDTO> comercios = comercioRepository.findAll().stream()
                .map(ComercioDTO::fromEntity) // Substituído por fromEntity
                .collect(Collectors.toList());
        logger.info("Foram encontrados {} comércios cadastrados.", comercios.size());
        return comercios;
    }

 // ----------------------------------------------------------
    /** Método chamado para incluir um novo comércio */
    // ----------------------------------------------------------
    public ComercioDTO incluirComercio(ComercioDTO dto) {
        logger.info("Incluindo novo comércio: {}", dto.getNome());

        try {
            // Converte o DTO para a entidade Comercio, passando o CidadeRepository
            Comercio comercio = dto.toEntity(cidadeRepository);

            // Salva a entidade Comercio no banco de dados
            comercio = comercioRepository.save(comercio);

            // Atribui um valor padrão ao tipo de comércio, caso não tenha sido definido
            comercio.setTipoComercio(comercio.getTipoComercio() != null ? comercio.getTipoComercio() : TipoComercio.PADARIA);

            logger.info("Comércio incluído com sucesso! ID: {}", comercio.getId());

            // Retorna o DTO do comércio criado
            return ComercioDTO.fromEntity(comercio);

        } catch (Exception e) {
            // Loga a exceção e lança uma exceção customizada ou retorna um erro específico
            logger.error("Erro ao incluir o comércio: {}", e.getMessage());
            throw new RuntimeException("Erro ao incluir o comércio: " + e.getMessage());
        }
    }
    // ----------------------------------------------------------
    /** Método chamado para alterar os dados de um comércio */
    // ----------------------------------------------------------
    public void alterarComercio(ComercioDTO dto) {
        logger.info("Tentando alterar o comércio com ID: {}", dto.getId());
        comercioRepository.findById(dto.getId()).ifPresentOrElse(comercio -> {
            comercio.setNome(dto.getNome());
            comercio.setResponsavel(dto.getNomeResponsavel());
            comercio.setTipoComercio(dto.getTipoComercio());

            if (dto.getCidadeId() != null) {
                Cidade cidade = cidadeRepository.findById(dto.getCidadeId()).orElse(null);
                comercio.setCidade(cidade);
            }

            comercioRepository.save(comercio);
            logger.info("Comércio com ID: {} alterado com sucesso!", dto.getId());
        }, () -> logger.error("Erro ao alterar: Comércio com ID {} não encontrado.", dto.getId()));
    }

    // ----------------------------------------------------------
    /** Método chamado para excluir um comércio */
    // ----------------------------------------------------------
    public void excluirComercio(Long idComercio) {
        logger.info("Tentando excluir comércio com ID: {}", idComercio);
        if (comercioRepository.existsById(idComercio)) {
            comercioRepository.deleteById(idComercio);
            logger.info("Comércio com ID: {} excluído com sucesso!", idComercio);
        } else {
            logger.error("Erro ao excluir: Comércio com ID {} não encontrado.", idComercio);
        }
    }

    // ----------------------------------------------------------
    /** Método para listar os tipos de comércio */
    // ----------------------------------------------------------
    public List<String> listarTiposComercio() {
        // Supondo que 'TipoComercio' seja um Enum
        return List.of(TipoComercio.values())
                .stream()
                .map(Enum::name)
                .collect(Collectors.toList());
    }

	public Comercio incluirComercio(Comercio comercio) {
		// TODO Auto-generated method stub
		return null;
	}
}

