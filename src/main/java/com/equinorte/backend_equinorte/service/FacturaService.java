package com.equinorte.backend_equinorte.service;

import com.equinorte.backend_equinorte.dto.FacturaDto;
import com.equinorte.backend_equinorte.dto.RecalculoFacturaDto;

import java.util.List;

public interface FacturaService {

    
    FacturaDto crearFactura(FacturaDto facturaDto);

    FacturaDto actualizarFactura(Long id, FacturaDto facturaDto);

    void eliminarFactura(Long id);

    FacturaDto obtenerPorId(Long id);

    List<FacturaDto> listarFacturas();

    FacturaDto recalcularFactura(Long idFactura, RecalculoFacturaDto dto);
}