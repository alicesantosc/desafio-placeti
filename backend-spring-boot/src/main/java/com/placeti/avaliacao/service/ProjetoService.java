package com.placeti.avaliacao.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.placeti.avaliacao.dto.CidadeDTO;
import com.placeti.avaliacao.model.Cidade;
import com.placeti.avaliacao.repository.CidadeRepository;

//------------------------------------------------------------------
/** Service usado para acessar os repositórios da aplicação */
//------------------------------------------------------------------
@Service

public class ProjetoService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CidadeRepository cidadeRepository;

	// ---------------------------------------------------------
	/** Método que busca uma cidade pelo seu ID */
	// ---------------------------------------------------------
	public CidadeDTO pesquisarCidade(Long id) {
		logger.info("Pesquisando cidade com ID: {}", id);
		return cidadeRepository.findById(id).map(cidade -> {
			logger.info("Cidade encontrada: {}", cidade.getNome());
			return CidadeDTO.toDTO(cidade);
		}).orElseGet(() -> {
			logger.warn("Nenhuma cidade encontrada com ID: {}", id);
			return null;
		});
	}

	// ---------------------------------------------------------
	/** Método que retorna todas as cidades cadastradas */
	// ---------------------------------------------------------
	public List<CidadeDTO> pesquisarCidades() {
		logger.info("Buscando todas as cidades cadastradas...");
		List<CidadeDTO> cidades = cidadeRepository.findAll().stream().map(CidadeDTO::toDTO)
				.collect(Collectors.toList());
		logger.info("Foram encontradas {} cidades cadastradas.", cidades.size());
		return cidades;
	}

	// ----------------------------------------------------------
	/**
	 * Método chamado para incluir uma nova cidade
	 * 
	 * @return
	 */
	// ----------------------------------------------------------

	public Cidade incluirCidade(Cidade cidade) {
		// Salvar a cidade no banco de dados
		return cidadeRepository.save(cidade);
	}

	public CidadeDTO incluirCidade(CidadeDTO dto) {

		logger.info("Incluindo nova cidade: {}", dto.getNome());
		Cidade cidade = new Cidade(dto.getNome(), dto.getUf(), dto.getCapital());
		cidade = cidadeRepository.save(cidade);
		logger.info("Cidade incluída com sucesso! ID: {}", cidade.getId());
		return CidadeDTO.toDTO(cidade);

	}

	// ----------------------------------------------------------
	/** Método chamado para alterar os dados de uma cidade */
	// ----------------------------------------------------------
	public void alterarCidade(CidadeDTO dto) {

		logger.info("Tentando alterar a cidade com ID: {}", dto.getId());
		cidadeRepository.findById(dto.getId()).ifPresentOrElse(cidade -> {
			cidade.setNome(dto.getNome());
			cidade.setUf(dto.getUf());
			cidade.setCapital(dto.getCapital());
			cidadeRepository.save(cidade);
			logger.info("Cidade com ID: {} alterada com sucesso!", dto.getId());
		}, () -> logger.error("Erro ao alterar: Cidade com ID {} não encontrada.", dto.getId()));

	}

	// ----------------------------------------------------------
	/** Método chamado para excluir uma cidade */
	// ----------------------------------------------------------
	public void excluirCidade(Long idCidade) {
		logger.info("Tentando excluir cidade com ID: {}", idCidade);
		if (cidadeRepository.existsById(idCidade)) {
			cidadeRepository.deleteById(idCidade);
			logger.info("Cidade com ID: {} excluída com sucesso!", idCidade);
		} else {
			logger.error("Erro ao excluir: Cidade com ID {} não encontrada.", idCidade);
		}

	}

}
