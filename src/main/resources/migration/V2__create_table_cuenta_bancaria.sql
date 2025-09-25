CREATE TABLE cuenta_bancaria (
    id VARCHAR(255) PRIMARY KEY,
    tipo VARCHAR(4) NOT NULL,
    balance DOUBLE NOT NULL,
    estado_cuenta VARCHAR(255) NOT NULL,
    fecha_creacion DATETIME(6) NOT NULL,
    sobregiro DOUBLE,
    tasa_de_interes DOUBLE,
    cliente_id BIGINT NOT NULL,
    CONSTRAINT fk_cuenta_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);