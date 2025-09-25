package com.example.CuentaBancaria.dtos;

import com.example.CuentaBancaria.enums.EstadoCuenta;
import lombok.Data;

import java.util.Date;

@Data
public class CuentaActualDTO extends CuentaBancariaDTO{

    private String id;

    public ClienteDTO getClienteDTO() {
        return clienteDTO;
    }

    public void setClienteDTO(ClienteDTO clienteDTO) {
        this.clienteDTO = clienteDTO;
    }

    private double balance;
    private Date fechaCreacion;
    private EstadoCuenta estadoCuenta;
    private ClienteDTO clienteDTO;
    private double sobregiro;

}
