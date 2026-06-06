
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
    'FAC-EQ-001',
    2430000,
    461700,
    2891700,
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
    'Alquiler excavadora hidráulica 320D',
    2,
    850000,
    1700000,
    1
),
(
    2,
    'Alquiler de andamios metálicos (kit semanal)',
    5,
    120000,
    600000,
    1
),
(
    3,
    'Alquiler vibrador de concreto portátil',
    2,
    95000,
    190000,
    1
);