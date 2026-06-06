package com.equinorte.backend_equinorte.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "facturas")
@Data
@AllArgsConstructor
public class Factura {
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private Long idFactura;

    @Column(name = "numero_factura", nullable = false, unique = true)
    private String numeroFactura;

    @Column(name = "subtotal", nullable = false)
    private BigDecimal subtotal;

    @Column(name = "iva", nullable = false)
    private BigDecimal iva;

    @Column(name = "total", nullable = false)
    private BigDecimal total;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleFactura> detalles = new ArrayList<>();

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }


}
