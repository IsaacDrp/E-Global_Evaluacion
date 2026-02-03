package com.gonet.api_persistence.repository;

import com.gonet.api_persistence.model.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    // Consulta para obtener registro base a la referencia
    @Query("SELECT t FROM TransactionEntity t WHERE t.referencia = :referencia")
    Optional<TransactionEntity> findByReferencia(@Param("referencia") String referencia);

    // Método para actualizar estatus de "aprobada" a "cancelada"
    @Modifying
    @Transactional
    @Query("UPDATE TransactionEntity t SET t.estatus = :nuevoEstatus WHERE t.id = :id AND t.referencia = :referencia")
    int updateEstatus(@Param("id") Long id,
                      @Param("referencia") String referencia,
                      @Param("nuevoEstatus") String nuevoEstatus);
}