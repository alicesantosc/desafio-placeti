package com.placeti.avaliacao.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.placeti.avaliacao.dto.ComercioDTO;
import com.placeti.avaliacao.model.Cidade;
import com.placeti.avaliacao.model.Comercio;
import com.placeti.avaliacao.repository.CidadeRepository;
import com.placeti.avaliacao.service.ComercioService;
import com.placeti.avaliacao.exception.CidadeNotFoundException;

@RestController
@RequestMapping("/comercios")
@CrossOrigin(origins = "http://localhost:4200")
public class ComercioController {

    private final ComercioService comercioService;

    @Autowired
    public ComercioController(ComercioService comercioService) {
        this.comercioService = comercioService;
    }

    //----------------------------------------------------------
    /** Endpoint de teste da requisição */
    //----------------------------------------------------------
    @GetMapping("/hello")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello, PlaceTi, sou sua nova estagiária!");
    }

    //----------------------------------------------------------
    /** Endpoint que retorna um comércio conforme seu ID */
    //----------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<ComercioDTO> buscarPeloId(@PathVariable Long id) {
        ComercioDTO comercioDTO = comercioService.pesquisarComercio(id);
        if (comercioDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comercioDTO);
    }

    //----------------------------------------------------------
    /** Endpoint que retorna todos os comércios cadastrados */
    //----------------------------------------------------------
    @GetMapping
    public ResponseEntity<List<ComercioDTO>> listarComercios() {
        List<ComercioDTO> comercios = comercioService.pesquisarComercios();
        return ResponseEntity.ok(comercios);
    }

    //----------------------------------------------------------
    /** Endpoint para incluir novo comércio */
    //----------------------------------------------------------
    @PostMapping
    public ResponseEntity<?> incluirComercio(@RequestBody ComercioDTO comercioDto) {
        // Verifica se os campos obrigatórios estão presentes
        if (comercioDto.getCidadeId() == null || comercioDto.getTipoComercio() == null) {
            return ResponseEntity.badRequest().body("Campos obrigatórios não preenchidos.");
        }

        try {
            // Chama o serviço para incluir o comércio
            ComercioDTO comercioCriadoDTO = comercioService.incluirComercio(comercioDto);

            return ResponseEntity.status(HttpStatus.CREATED).body(comercioCriadoDTO);
        } catch (Exception e) {
            // Em caso de erro durante o processo, retorna uma resposta de erro com a mensagem
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao incluir o comércio: " + e.getMessage());
        }
    }



    //----------------------------------------------------------
    /** Endpoint para alterar comércio */
    //----------------------------------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<Void> alterarComercio(@PathVariable Long id, @RequestBody ComercioDTO comercioDto) {
        if (comercioDto.getId() == null || !id.equals(comercioDto.getId())) {
            return ResponseEntity.badRequest().build();
        }
        comercioDto.setId(id);
        comercioService.alterarComercio(comercioDto);
        return ResponseEntity.noContent().build();
    }

    //----------------------------------------------------------
    /** Endpoint para excluir um comércio */
    //----------------------------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirComercio(@PathVariable Long id) {
        comercioService.excluirComercio(id);
        return ResponseEntity.noContent().build();
    }

    //----------------------------------------------------------
    /** Endpoint que retorna os tipos de comércio */
    //----------------------------------------------------------
    @GetMapping("/tipos")
    public ResponseEntity<List<String>> listarTiposComercio() {
        List<String> tiposComercio = comercioService.listarTiposComercio();
        return ResponseEntity.ok(tiposComercio);
    }

}

