package com.equinorte.backend_equinorte.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.*;



@Entity
@Table(name = "detalle_facturas")
@Data
@AllArgsConstructor
public class DetalleFactura {
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long idDetalle;

    @Column(name = "producto", nullable = false)
    private String producto;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", nullable = false)
    private BigDecimal precioUnitario;

    @Column(name = "subtotal", nullable = false)
    private BigDecimal subtotal;

    @ManyToOne
    @JoinColumn(name = "id_factura", nullable = false)
    private Factura factura;
}
