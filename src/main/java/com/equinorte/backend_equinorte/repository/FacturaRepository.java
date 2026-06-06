package com.equinorte.backend_equinorte.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.equinorte.backend_equinorte.entity.Factura;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long>{

    Optional<Factura> findByNumeroFactura(String numeroFactura);

    boolean existsByNumeroFactura(String numeroFactura);
}
