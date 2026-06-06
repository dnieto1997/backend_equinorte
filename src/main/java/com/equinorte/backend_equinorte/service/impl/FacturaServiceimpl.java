package com.equinorte.backend_equinorte.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.equinorte.backend_equinorte.dto.FacturaDto;
import com.equinorte.backend_equinorte.dto.RecalculoFacturaDto;
import com.equinorte.backend_equinorte.entity.DetalleFactura;
import com.equinorte.backend_equinorte.entity.Factura;
import com.equinorte.backend_equinorte.entity.TipoUsuario;
import com.equinorte.backend_equinorte.exception.ResourceNotFoundException;
import com.equinorte.backend_equinorte.mapper.FacturaMapper;
import com.equinorte.backend_equinorte.repository.FacturaRepository;
import com.equinorte.backend_equinorte.service.FacturaService;

@Service
public class FacturaServiceimpl implements FacturaService {

        @Autowired
        private FacturaRepository facturaRepository;

        @Autowired
        private FacturaMapper facturaMapper;

        @Override
        public FacturaDto crearFactura(FacturaDto facturaDto) {
                if (facturaRepository.findByNumeroFactura(facturaDto.getNumeroFactura()).isPresent()) {
                        throw new ResponseStatusException(
                                        HttpStatus.BAD_REQUEST,
                                        "Ya existe una factura con el número: " + facturaDto.getNumeroFactura());
                }

                Factura factura = new Factura();

                factura.setNumeroFactura(facturaDto.getNumeroFactura());

                List<DetalleFactura> detalles = facturaDto.getDetalles()
                                .stream()
                                .map(d -> {
                                        DetalleFactura det = new DetalleFactura(null, null, null, null, null, factura);

                                        det.setProducto(d.getProducto());
                                        det.setCantidad(d.getCantidad());
                                        det.setPrecioUnitario(d.getPrecioUnitario());

                                        BigDecimal subtotal = d.getPrecioUnitario()
                                                        .multiply(BigDecimal.valueOf(d.getCantidad()));

                                        det.setSubtotal(subtotal);

                                        det.setFactura(factura);

                                        return det;
                                }).toList();

                factura.setDetalles(detalles);

                BigDecimal subtotal = detalles.stream()
                                .map(DetalleFactura::getSubtotal)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                factura.setSubtotal(subtotal);

                BigDecimal iva = subtotal.multiply(new BigDecimal("0.19"));
                factura.setIva(iva);
                factura.setTotal(subtotal.add(iva));

                return facturaMapper.toDto(
                                facturaRepository.save(factura));
        }

        @Override
        public FacturaDto actualizarFactura(Long id, FacturaDto facturaDto) {

                Factura factura = facturaRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Factura no encontrada con id: " + id));

                factura.getDetalles().clear();

                List<DetalleFactura> nuevosDetalles = facturaDto.getDetalles()
                                .stream()
                                .map(d -> {

                                        DetalleFactura det = new DetalleFactura();

                                        det.setProducto(d.getProducto());
                                        det.setCantidad(d.getCantidad());
                                        det.setPrecioUnitario(d.getPrecioUnitario());

                                        BigDecimal subtotal = d.getPrecioUnitario()
                                                        .multiply(BigDecimal.valueOf(d.getCantidad()));

                                        det.setSubtotal(subtotal);

                                        det.setFactura(factura);

                                        return det;
                                })
                                .toList();

                factura.getDetalles().addAll(nuevosDetalles);

                BigDecimal subtotal = nuevosDetalles.stream()
                                .map(DetalleFactura::getSubtotal)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                factura.setSubtotal(subtotal);

                BigDecimal iva = subtotal.multiply(new BigDecimal("0.19"));
                factura.setIva(iva);
                factura.setTotal(subtotal.add(iva));

                return facturaMapper.toDto(
                                facturaRepository.save(factura));
        }

        @Override
        public void eliminarFactura(Long id) {

                if (!facturaRepository.existsById(id)) {
                        throw new ResourceNotFoundException("Factura no encontrada con id: " + id);
                }

                facturaRepository.deleteById(id);
        }

        @Override
        public FacturaDto obtenerPorId(Long id) {

                Factura factura = facturaRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Factura no encontrada con id: " + id));

                return facturaMapper.toDto(factura);
        }

        @Override
        public List<FacturaDto> listarFacturas() {

                return facturaRepository.findAll()
                                .stream()
                                .map(facturaMapper::toDto)
                                .toList();
        }

        @Override
        public FacturaDto recalcularFactura(Long idFactura, RecalculoFacturaDto dto) {

                Factura factura = facturaRepository.findById(idFactura)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Factura no encontrada con id: " + idFactura));

                BigDecimal subtotalActual = factura.getSubtotal();
                BigDecimal nuevoSubtotal = dto.getNuevoSubtotal();

                BigDecimal diferencia = nuevoSubtotal.subtract(subtotalActual);

                if (diferencia.compareTo(BigDecimal.ZERO) > 0) {

                        if (dto.getTipoUsuario() == TipoUsuario.OPERADOR &&
                                        diferencia.compareTo(new BigDecimal("20000")) > 0) {
                                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                                "Operador solo puede aumentar hasta 20.000");
                        }

                        if (dto.getTipoUsuario() == TipoUsuario.SUPERVISOR &&
                                        diferencia.compareTo(new BigDecimal("50000")) > 0) {
                                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                                "Supervisor solo puede aumentar hasta 50.000");
                        }
                }

                if (subtotalActual.compareTo(BigDecimal.ZERO) == 0) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                        "No se puede recalcular una factura con subtotal 0");
                }

                BigDecimal factor = nuevoSubtotal
                                .divide(subtotalActual, 10, RoundingMode.HALF_UP);

                List<DetalleFactura> detalles = factura.getDetalles();
                BigDecimal acumulado = BigDecimal.ZERO;
                int lastIndex = detalles.size() - 1;

                for (int i = 0; i < detalles.size(); i++) {

                        DetalleFactura detalle = detalles.get(i);

                        BigDecimal valor = detalle.getSubtotal()
                                        .multiply(factor)
                                        .setScale(2, RoundingMode.HALF_UP);

                        if (i == lastIndex) {
                                valor = nuevoSubtotal.subtract(acumulado);
                        }

                        detalle.setSubtotal(valor);
                        acumulado = acumulado.add(valor);
                }

                factura.setSubtotal(nuevoSubtotal);

                BigDecimal iva = nuevoSubtotal.multiply(new BigDecimal("0.19"));
                factura.setIva(iva);
                factura.setTotal(nuevoSubtotal.add(iva));

                return facturaMapper.toDto(
                                facturaRepository.save(factura));
        }
}