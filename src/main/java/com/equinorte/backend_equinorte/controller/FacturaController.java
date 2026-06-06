package com.equinorte.backend_equinorte.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.equinorte.backend_equinorte.dto.FacturaDto;
import com.equinorte.backend_equinorte.dto.RecalculoFacturaDto;
import com.equinorte.backend_equinorte.service.FacturaService;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

     //Crear facturas 
    @PostMapping("/crear")
    public ResponseEntity<FacturaDto> crearFactura(@RequestBody FacturaDto facturaDto) {

        FacturaDto creada = facturaService.crearFactura(facturaDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(creada);
    }

     // Listar todas las facturas
    @GetMapping
    public ResponseEntity<List<FacturaDto>> listarFacturas() {

        List<FacturaDto> facturas = facturaService.listarFacturas();

        return ResponseEntity.ok(facturas);
    }

    //Buscar factura por ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<FacturaDto> obtenerPorId(@PathVariable Long id) {

        FacturaDto factura = facturaService.obtenerPorId(id);

        return ResponseEntity.ok(factura);
    }

    

    //Actualizar factura por ID
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<FacturaDto> actualizarFactura(
            @PathVariable Long id,
            @RequestBody FacturaDto facturaDto) {

        FacturaDto actualizada =
                facturaService.actualizarFactura(id, facturaDto);

        return ResponseEntity.ok(actualizada);
    }

    
    //Recalcular factura por ID 
    @PutMapping("/recalcular/{id}")
    public ResponseEntity<FacturaDto> recalcularFactura(
            @PathVariable Long id,
            @RequestBody RecalculoFacturaDto dto) {

        FacturaDto resultado =
                facturaService.recalcularFactura(id, dto);

        return ResponseEntity.ok(resultado);
    }

    //Eliminar factura por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarFactura(@PathVariable Long id) {

        facturaService.eliminarFactura(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(Map.of(
                        "status", HttpStatus.NO_CONTENT.value(),
                        "mensaje", "Factura eliminada correctamente"
                ));
    }
}