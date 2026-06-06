
INSERT INTO users (
    email,
    fecha_actualizacion,
    fecha_creacion,
    nombre,
    tipo_usuario
) VALUES (
    'operador@equinorte.com',
    NULL,
    NOW(),
    'Usuario Operador',
    'OPERADOR'
);

INSERT INTO users (
    email,
    fecha_actualizacion,
    fecha_creacion,
    nombre,
    tipo_usuario
) VALUES (
    'supervisor@equinorte.com',
    NULL,
    NOW(),
    'Usuario Supervisor',
    'SUPERVISOR'
);

INSERT INTO facturas (
    id_factura,
    numero_factura,
    subtotal,
    iva,
    total,
    fecha_creacion,
    fecha_actualizacion
) VALUES (
    1,
    'FAC-INIT-001',
    810000,
    153900,
    963900,
    NOW(),
    NULL
);


INSERT INTO detalle_facturas (
    id_detalle,
    producto,
    cantidad,
    precio_unitario,
    subtotal,
    id_factura
) VALUES
(
    1,
    'Teclado mecánico',
    2,
    80000,
    160000,
    1
),
(
    2,
    'Mouse gamer',
    1,
    50000,
    50000,
    1
),
(
    3,
    'Monitor 24 pulgadas',
    1,
    600000,
    600000,
    1
);