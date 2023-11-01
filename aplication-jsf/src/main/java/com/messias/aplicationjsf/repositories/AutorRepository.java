package com.messias.aplicationjsf.repositories;

import com.messias.aplicationjsf.domain.Autor;
import org.springframework.data.jpa.repository.JpaRepository;



public interface AutorRepository extends JpaRepository<Autor, Integer> {
}
