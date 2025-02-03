package com.placeti.avaliacao.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.placeti.avaliacao.dto.CidadeDTO;
import com.placeti.avaliacao.model.Cidade;
import com.placeti.avaliacao.service.ProjetoService;

//--------------------------------------------------
/** Endpoint para consultar e manter cidades */
//--------------------------------------------------
@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/cidades")
public class CidadeController {
	
	private final ProjetoService projetoService;
	
	public CidadeController(ProjetoService projetoService) {
		this.projetoService = projetoService;
		
	}
	
	
	//----------------------------------------------------------
			/** Endpoint de teste da requisição */
	//-----------------------------------------------------------
		
	
	@GetMapping("/hello")
	public ResponseEntity<String> helloWorld() {
	    return ResponseEntity.ok("Hello, PlaceTi, sou sua nova estagiária!");
	}

	//----------------------------------------------------------
		/** Endpoint que retorna uma cidade conforme seu ID */
	//-----------------------------------------------------------
	
	@GetMapping("/{id}")
	public ResponseEntity<CidadeDTO> buscarPeloId(@PathVariable Long id) {
		// TODO: Responde GET em http://localhost:8080/placeti/cidades/1
		return ResponseEntity.ok(projetoService.pesquisarCidade(id));
	}
	
	//----------------------------------------------------------
	/** Endpoint que retorna todas as cidades cadastradas */
	//----------------------------------------------------------
	
	@GetMapping
	public ResponseEntity<List<CidadeDTO>>  projetoCidades() {
		// TODO: Responde GET em http://localhost:8080/placeti/cidades
		List<CidadeDTO> cidades = projetoService.pesquisarCidades();
		return ResponseEntity.ok(cidades);
	}
	
	//----------------------------------------------------------
	/** Endpoint para incluir nova cidade */
	//----------------------------------------------------------
	
	@PostMapping
	public ResponseEntity<CidadeDTO> incluirCidade(@RequestBody CidadeDTO cidadeDto) {
	    System.out.println("Recebido: " + cidadeDto);

	    // Converter DTO para Entidade
	    Cidade cidade = cidadeDto.toEntity();

	    // Chamar o método do serviço para salvar a cidade
	    Cidade cidadeCriada = projetoService.incluirCidade(cidade);

	    // Converter a entidade salva de volta para DTO
	    CidadeDTO cidadeCriadaDTO = CidadeDTO.toDTO(cidadeCriada);

	    // Retornar a resposta com o DTO
	    return ResponseEntity.status(201).body(cidadeCriadaDTO);
				
		//	TODO: Responde POST em http://localhost:8080/placeti/cidades
		//	Envia JSON no body:
		//	{
		//	 	"nome": "Florianópolis",
		//	  	"uf": "SC",
		//	   	"capital": true
		//	}
	}	
	
	//----------------------------------------------------------
	/** Endpoint para alterar cidade */
	//----------------------------------------------------------
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> alterarCidade(@PathVariable Long id, @RequestBody CidadeDTO cidadeDto) {
		// TODO: Responde PUT em http://localhost:8080/placeti/cidades
		//   Envia JSON no body:
		//   {
		//     "id": 11,
		//     "nome": "Blumenau",
		//     "uf": "SC",
		//     "capital": false
		//   }
		
		cidadeDto.setId(id); // Define o ID da cidade a ser atualizada
		projetoService.alterarCidade(cidadeDto);
		return ResponseEntity.noContent().build();
		
	}
	
	//----------------------------------------------------------
	/** Endpoint para excluir uma cidade */
	//----------------------------------------------------------
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluirCidade(@PathVariable Long id){
		// Responde DELETE em http://localhost:8080/placeti/cidades/{idCidade}
		projetoService.excluirCidade(id);
		return ResponseEntity.noContent().build();
	}	
}
