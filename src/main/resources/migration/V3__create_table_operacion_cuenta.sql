CREATE TABLE operacion_cuenta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    descripcion VARCHAR(255) NOT NULL,
    fecha_operacion DATETIME(6) NOT NULL,
    monto DOUBLE NOT NULL,
    tipo_operacion VARCHAR(255) NOT NULL,
    cuenta_bancaria_id VARCHAR(255) NOT NULL,
    CONSTRAINT fk_operacion_cuenta FOREIGN KEY (cuenta_bancaria_id) REFERENCES cuenta_bancaria(id)
);