package com.messias.aplicationjsf.repositories;

import ch.qos.logback.core.model.INamedModel;
import com.messias.aplicationjsf.domain.Editora;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EditoraRepository extends JpaRepository<Editora, Integer> {
}
