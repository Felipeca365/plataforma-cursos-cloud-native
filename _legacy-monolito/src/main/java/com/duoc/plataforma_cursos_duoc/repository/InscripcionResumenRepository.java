package com.duoc.plataforma_cursos_duoc.repository;

import com.duoc.plataforma_cursos_duoc.model.InscripcionResumenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscripcionResumenRepository extends JpaRepository<InscripcionResumenEntity, Long> {
}