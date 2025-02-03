package com.placeti.avaliacao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.placeti.avaliacao.model.Cidade;

//----------------------------------------------
/** Repositório para entidade Cidade */
//----------------------------------------------
public interface CidadeRepository extends JpaRepository<Cidade, Long>{
	
	List<Cidade> findByUf(String uf);
	
	List<Cidade> findByCapital(Boolean capital);
	
	List<Cidade> findBynome(String nome);
	
	
	
	
}
