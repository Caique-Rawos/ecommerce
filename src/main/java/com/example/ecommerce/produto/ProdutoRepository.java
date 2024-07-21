package com.example.ecommerce.produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {
    Optional<ProdutoEntity> findByCodigoBarras(String codigoBarras);

    @Query("SELECT p FROM ProdutoEntity p WHERE p.codigoBarras = :codigoBarras AND p.id != :id")
    Optional<ProdutoEntity> findByCodigoBarrasAndIdNot(@Param("codigoBarras") String codigoBarras, @Param("id") Long id);
}
