package com.placeti.avaliacao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.placeti.avaliacao.model.Comercio;

public interface ComercioRepository extends JpaRepository<Comercio, Long>{
	
	List<Comercio> findByCidadeId(Long cidadeId);

}
