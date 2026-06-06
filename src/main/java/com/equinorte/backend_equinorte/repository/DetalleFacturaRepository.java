package com.equinorte.backend_equinorte.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.equinorte.backend_equinorte.entity.DetalleFactura;



@Repository
public interface DetalleFacturaRepository extends JpaRepository<DetalleFactura, Long>{
List<DetalleFactura> findByFacturaIdFactura(Long idFactura);
}
