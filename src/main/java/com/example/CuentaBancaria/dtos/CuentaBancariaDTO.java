package com.example.CuentaBancaria.dtos;

import lombok.Data;

@Data
public class CuentaBancariaDTO {

    private String tipo;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
